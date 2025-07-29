package in.transportstack.delhi.usermanagement.service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.nimbusds.jwt.JWTParser;
import in.transportstack.delhi.core.entity.User;
import in.transportstack.delhi.core.repository.UserRepository;
import in.transportstack.delhi.sharedconfig.util.EncryptionUtil;
import in.transportstack.delhi.usermanagement.dto.*;
import in.transportstack.delhi.usermanagement.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${aws.userPoolId}")
    private String userPoolId;

    @Value("${spring.security.oauth2.client.registration.cognito.client-id}")
    private String clientId;

    private final AWSCognitoIdentityProvider cognitoIdentityProvider;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AuthenticationServiceImpl(
            AWSCognitoIdentityProvider cognitoIdentityProvider,
            UserRepository userRepository,
            UserMapper userMapper) {
        this.cognitoIdentityProvider = cognitoIdentityProvider;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public LoginUserResponseDto loginUser(LoginUserRequestDto loginUserRequestDto) {
        LoginUserResponseDto loggedInUser = new LoginUserResponseDto();

        // Set up the authentication request
        InitiateAuthRequest authRequest = new InitiateAuthRequest()
                .withAuthFlow("USER_PASSWORD_AUTH")
                .withClientId(clientId)
                .withAuthParameters(
                        Map.of(
                                "USERNAME", loginUserRequestDto.getUsername(),   // Use email as the username
                                "PASSWORD", loginUserRequestDto.getPassword()
                        )
                );

        try {
            InitiateAuthResult authResult = cognitoIdentityProvider.initiateAuth(authRequest);
            System.out.println(authResult);
            AuthenticationResultType authResponse = authResult.getAuthenticationResult();

            // At this point, the user is successfully authenticated, and you can access JWT tokens:
            String accessToken = authResponse.getAccessToken();
            String idToken = authResponse.getIdToken();
            String refreshToken = authResponse.getRefreshToken();

            // get user information
            GetUserRequest getUserRequest = new GetUserRequest().withAccessToken(accessToken);
            GetUserResult userResult = cognitoIdentityProvider.getUser(getUserRequest);



            userResult.getUserAttributes().forEach(attr -> {
                System.out.println("custom attribute" + attr.getName() + ": " + attr.getValue());
                if (attr.getName().equals("custom:appDbReference")) {
                    Optional<User> user = userRepository.findById(UUID.fromString(attr.getValue()));
                    if (user.isPresent()) {
                        loggedInUser.setFirstName(user.get().getFirstName());
                        loggedInUser.setLastName(user.get().getLastName());
                    }
                }
                System.out.println(attr.getName() + ": " + attr.getValue());
            });

            // extracting information from Token
            List<String> roles = JWTParser.parse(accessToken)
                    .getJWTClaimsSet()
                    .getStringListClaim("cognito:groups");

            // Describe the user pool client
            DescribeUserPoolClientRequest describeUserPoolClientRequest = new DescribeUserPoolClientRequest()
                    .withUserPoolId(userPoolId)
                    .withClientId(clientId);

            DescribeUserPoolClientResult describeUserPoolClientResult =
                    cognitoIdentityProvider.describeUserPoolClient(describeUserPoolClientRequest);

            System.out.println(describeUserPoolClientResult.getUserPoolClient().toString());
            // You can decode and verify the JWT tokens for user information

            loggedInUser.setUsername(loggedInUser.getUsername());
            loggedInUser.setAccessToken(accessToken); // Store the token for future requests
            loggedInUser.setIdToken(idToken);
            loggedInUser.setRefreshToken(refreshToken);
            loggedInUser.setRoles(roles);
            loggedInUser.setAccessTokenExpiresIn(
                    describeUserPoolClientResult.getUserPoolClient().getAccessTokenValidity() * 60);
            loggedInUser.setRefreshTokenExpiresIn(
                    describeUserPoolClientResult.getUserPoolClient().getRefreshTokenValidity() * 60);

            return loggedInUser;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public RegisterUserResponseDto registerUser(RegisterUserRequestDto registerUserRequestDto) throws Exception {

        if (userRepository.existsByEmail(registerUserRequestDto.getEmail())) {
            return new RegisterUserResponseDto("Email already exists");
        }

        if (userRepository.existsByMobileNumber(registerUserRequestDto.getMobileNumber())) {
            return new RegisterUserResponseDto("Mobile number already exists");
        }

        User user = userMapper.toEntity(registerUserRequestDto);
        user.setPassword(EncryptionUtil.encrypt(registerUserRequestDto.getPassword()));
        user.setIsAccountActive(true);
        user.setIsEmailVerified(true);
        user.setIsMobileVerified(true);

        // Save user to local DB
        User savedUser = userRepository.save(user);

        // Create user with email_verified attribute
        AdminCreateUserRequest adminCreateUserRequest = new AdminCreateUserRequest()
                .withUserPoolId(userPoolId)
                .withUsername(registerUserRequestDto.getEmail())
                .withUserAttributes(
                        new AttributeType().withName("email").withValue(registerUserRequestDto.getEmail()),
                        new AttributeType().withName("email_verified").withValue("true"),
                        new AttributeType().withName("custom:appDbReference").withValue(savedUser.getId().toString())
                )
                .withMessageAction("SUPPRESS"); // Suppress the email sending

        // Register the user with Amazon Cognito
        try {
            AdminCreateUserResult createUserResult = cognitoIdentityProvider.adminCreateUser(adminCreateUserRequest);
            System.out.println("User created: " + createUserResult.getUser().getUsername());

            setUserPasswordPermanent(userPoolId, registerUserRequestDto.getEmail(), registerUserRequestDto.getPassword());

            System.out.println("User password set to permanent: " + registerUserRequestDto.getEmail());
            System.out.println("User confirmed: " + registerUserRequestDto.getEmail());

            // Assign the user to a group
            AdminAddUserToGroupRequest adminAddUserToGroupRequest = new AdminAddUserToGroupRequest()
                    .withUserPoolId(userPoolId)
                    .withUsername(registerUserRequestDto.getEmail())
                    .withGroupName("ROLE_USER");

            cognitoIdentityProvider.adminAddUserToGroup(adminAddUserToGroupRequest);
            System.out.println("User added to group: ROLE_USER");

            return new RegisterUserResponseDto("User registered successfully");

        } catch (AWSCognitoIdentityProviderException e) {
            System.err.println("Failed to sign up user: " + e.getErrorMessage());

            return new RegisterUserResponseDto("User registration failed: " + e.getErrorMessage());

        } catch (Exception e) {
            log.error(e.getMessage());
            return new RegisterUserResponseDto("User registration failed: " + e.getMessage());
        }
    }

    @Override
    public LogoutUserResponseDto logoutUser(LogoutUserRequestDto logoutUserRequestDto) {
        if (ObjectUtils.isEmpty(logoutUserRequestDto.getAccessToken())) {
            return new LogoutUserResponseDto("AccessToken is null");
        }

        try {
            GlobalSignOutRequest signOutRequest = new GlobalSignOutRequest()
                    .withAccessToken(logoutUserRequestDto.getAccessToken());
            cognitoIdentityProvider.globalSignOut(signOutRequest);
            return new LogoutUserResponseDto("Logout successful");

        } catch(Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public ChangePasswordResponseDto changePassword(ChangePasswordRequestDto changePasswordRequestDto) {
        try {
            ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest()
                    .withAccessToken(changePasswordRequestDto.getAccessToken())
                    .withPreviousPassword(changePasswordRequestDto.getOldPassword())
                    .withProposedPassword(changePasswordRequestDto.getNewPassword());
            cognitoIdentityProvider.changePassword(changePasswordRequest);

            return new ChangePasswordResponseDto("Password updated successfully");

        } catch(Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public ForgotPasswordResponseDto forgotPassword(ForgotPasswordRequestDto forgotPasswordRequestDto) {
        if (ObjectUtils.isEmpty(forgotPasswordRequestDto.getUsername())) {
            return new ForgotPasswordResponseDto("Username is null");
        }

        try {
            if (!userRepository.existsByEmail(forgotPasswordRequestDto.getUsername())) {
                return new ForgotPasswordResponseDto("User Does Not Exist.");
            }

            ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest()
                    .withUsername(forgotPasswordRequestDto.getUsername())
                    .withClientId(clientId);
            ForgotPasswordResult result = cognitoIdentityProvider.forgotPassword(forgotPasswordRequest);
            log.info("Forgot password result: {}", result.toString());
            return new ForgotPasswordResponseDto("Password reset initiated. Please check your email for the verification code.");

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public ForgotPasswordResponseDto confirmForgotPassword(ForgotPasswordRequestDto forgotPasswordRequestDto) {
        try {
            ConfirmForgotPasswordRequest confirmRequest = new ConfirmForgotPasswordRequest()
                    .withUsername(forgotPasswordRequestDto.getUsername())
                    .withConfirmationCode(forgotPasswordRequestDto.getConfirmationCode())
                    .withPassword(forgotPasswordRequestDto.getNewPassword())
                    .withClientId(clientId);
            ConfirmForgotPasswordResult result = cognitoIdentityProvider.confirmForgotPassword(confirmRequest);
            return new ForgotPasswordResponseDto("Password reset successful");

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private void setUserPasswordPermanent(String userPoolId, String email, String password) {
        // Set user password and mark it as permanent
        AdminSetUserPasswordRequest adminSetUserPasswordRequest = new AdminSetUserPasswordRequest()
                .withUserPoolId(userPoolId)
                .withUsername(email)
                .withPassword(password)
                .withPermanent(true);
        cognitoIdentityProvider.adminSetUserPassword(adminSetUserPasswordRequest);
    }
}
