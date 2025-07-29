package in.transportstack.delhi.usermanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LogoutUserRequestDto {
    private String accessToken;
}
