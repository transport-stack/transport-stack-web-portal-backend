package in.transportstack.delhi.usermanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.io.Serializable;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginUserRequestDto implements Serializable {
    @NotBlank(message = "Username is required")
    String username;

    @NotBlank(message = "Password is required")
    String password;
}
