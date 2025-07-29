package in.transportstack.delhi.usermanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.Value;

import java.io.Serializable;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterUserRequestDto implements Serializable {

    @NotNull(message = "First name cannot be null")
    @NotEmpty(message = "First name cannot be empty")
    @NotBlank(message = "First name cannot be blank")
    String firstName;

    String lastName;

    @NotNull(message = "Mobile number cannot be null")
    @Size(message = "provide valid mobile number", min = 10, max = 10)
    @NotEmpty(message = "Mobile number cannot be empty")
    @NotBlank(message = "Mobile number cannot be blank")
    String mobileNumber;

    @NotNull(message = "Organization name cannot be null")
    @NotEmpty(message = "Organization name cannot be empty")
    @NotBlank(message = "Organization name cannot be blank")
    String organizationName;

    @NotNull(message = "Organization type cannot be null")
    @NotEmpty(message = "Organization type cannot be empty")
    @NotBlank(message = "Organization type cannot be blank")
    String organizationType;

    String address;

    String country;

    String pincode;

    String state;

    @NotNull(message = "email cannot be null")
    @Email(message = "provide valid email")
    @NotEmpty(message = "email cannot be empty")
    @NotBlank(message = "email cannot be blank")
    String email;

    @NotNull(message = "password cannot be null")
    @Pattern(message = "password doesn't match the criteria", regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{12,}$")
    @NotEmpty(message = "password cannot be empty")
    @NotBlank(message = "password cannot be blank")
    String password;

    String profileDescription;
}
