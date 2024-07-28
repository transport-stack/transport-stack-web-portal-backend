package in.transportstack.delhi.usermanagement.service;

import in.transportstack.delhi.usermanagement.dto.*;

public interface AuthenticationService {
    LoginUserResponseDto loginUser(LoginUserRequestDto loginUserRequestDto);
    RegisterUserResponseDto registerUser(RegisterUserRequestDto registerUserRequestDto) throws Exception;
    LogoutUserResponseDto logoutUser(LogoutUserRequestDto logoutUserRequestDto);
    ChangePasswordResponseDto changePassword(ChangePasswordRequestDto changePasswordRequestDto);
    ForgotPasswordResponseDto forgotPassword(ForgotPasswordRequestDto forgotPasswordRequestDto);
    ForgotPasswordResponseDto confirmForgotPassword(ForgotPasswordRequestDto forgotPasswordRequestDto);
}
