package in.transportstack.delhi.usermanagement.controller;

import in.transportstack.delhi.usermanagement.dto.*;
import in.transportstack.delhi.usermanagement.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
@Slf4j
public class UserController {

    private final AuthenticationService authenticationService;

    public UserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/login", consumes = {"application/json"})
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginUserRequestDto loginUserRequestDto) {
        LoginUserResponseDto loginUserResponseDto = authenticationService.loginUser(loginUserRequestDto);
        if (loginUserResponseDto != null) {
            return ResponseEntity.ok(loginUserResponseDto);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Invalid username or password"));
    }

    @PostMapping(value = "/register", consumes = {"application/json"})
    public ResponseEntity<RegisterUserResponseDto> registerUser(@Valid @RequestBody RegisterUserRequestDto registerUserRequestDto)
            throws Exception {
        RegisterUserResponseDto registerUserResponseDto = authenticationService.registerUser(registerUserRequestDto);
        return ResponseEntity.ok(registerUserResponseDto);
    }

    /** TODO: Implement functionality to fetch JWT from header or from logged in user(Principal)
     * and remove it from request body
     */
    @PostMapping(value = "/logout", consumes = {"application/json"})
    public ResponseEntity<LogoutUserResponseDto> logoutUser(@Valid @RequestBody LogoutUserRequestDto logoutUserRequestDto) {
        LogoutUserResponseDto logoutUserResponseDto = authenticationService.logoutUser(logoutUserRequestDto);
        if (logoutUserResponseDto != null) {
            return ResponseEntity.ok(logoutUserResponseDto);
        }
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new LogoutUserResponseDto("Unable to logout user"));
    }

    /** TODO: Implement functionality to fetch JWT from header or from logged in user(Principal)
     * and remove it from request body
     */
    @PostMapping(value = "/change-password", consumes = {"application/json"})
    public ResponseEntity<ChangePasswordResponseDto> changePassword(@Valid @RequestBody ChangePasswordRequestDto changePasswordRequestDto) {
        ChangePasswordResponseDto changePasswordResponseDto = authenticationService.changePassword(changePasswordRequestDto);
        if (changePasswordResponseDto != null) {
            return ResponseEntity.ok(changePasswordResponseDto);
        }
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ChangePasswordResponseDto("Unable to change password"));
    }

    @PostMapping(value = "/forgot-password", consumes = {"application/json"})
    public ResponseEntity<ForgotPasswordResponseDto> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDto forgotPasswordRequestDto) {
        ForgotPasswordResponseDto forgotPasswordResponseDto = authenticationService.forgotPassword(forgotPasswordRequestDto);
        if (forgotPasswordResponseDto != null) {
            return ResponseEntity.ok(forgotPasswordResponseDto);
        }
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ForgotPasswordResponseDto("Unable to proceed with forgot password"));
    }

    @PostMapping(value = "/confirm-forgot-password", consumes = {"application/json"})
    public ResponseEntity<ForgotPasswordResponseDto> confirmForgotPassword(@Valid @RequestBody ForgotPasswordRequestDto forgotPasswordRequestDto) {
        ForgotPasswordResponseDto forgotPasswordResponseDto = authenticationService.confirmForgotPassword(forgotPasswordRequestDto);
        if (forgotPasswordResponseDto != null) {
            return ResponseEntity.ok(forgotPasswordResponseDto);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ForgotPasswordResponseDto("Unable to proceed with forgot password"));
    }

    @GetMapping("/secured")
    @PreAuthorize("hasRole('USER')")
    public String securedEndpoint(@AuthenticationPrincipal Jwt jwt) {
        return "This is a secured endpoint!" + jwt.getClaims().toString() + jwt.getHeaders().toString();
    }
}
