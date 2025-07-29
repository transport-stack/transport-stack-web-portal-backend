package in.transportstack.delhi.usermanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForgotPasswordRequestDto {
    String username;
    String confirmationCode;
    String newPassword;
}
