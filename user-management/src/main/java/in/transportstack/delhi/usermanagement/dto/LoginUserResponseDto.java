package in.transportstack.delhi.usermanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginUserResponseDto implements Serializable {
    String firstName;
    String lastName;
    String username;
    String accessToken;
    Integer accessTokenExpiresIn;
    String refreshToken;
    Integer refreshTokenExpiresIn;
    String idToken;
    List<String> roles = new ArrayList<>();
}
