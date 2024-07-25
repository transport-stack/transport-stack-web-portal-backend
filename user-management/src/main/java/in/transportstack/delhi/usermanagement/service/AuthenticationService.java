package in.transportstack.delhi.usermanagement.service;

import in.transportstack.delhi.usermanagement.dto.LoginUserResponseDto;
import in.transportstack.delhi.usermanagement.dto.LoginUserRequestDto;
import in.transportstack.delhi.usermanagement.dto.RegisterUserRequestDto;
import in.transportstack.delhi.usermanagement.dto.RegisterUserResponseDto;

public interface AuthenticationService {
    LoginUserResponseDto loginUser(LoginUserRequestDto loginUserRequestDto);
    RegisterUserResponseDto registerUser(RegisterUserRequestDto registerUserRequestDto) throws Exception;
}
