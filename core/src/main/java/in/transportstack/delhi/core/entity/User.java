package in.transportstack.delhi.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotBlank(message = "First name cannot be blank")
    @NotEmpty(message = "First name cannot be empty")
    @NotNull(message = "First name cannot be null")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Size(message = "provide valid mobile number", min = 10, max = 10)
    @NotBlank(message = "Mobile number cannot be blank")
    @NotEmpty(message = "Mobile number cannot be empty")
    @NotNull(message = "Mobile number cannot be null")
    @Column(name = "mobile_number", nullable = false, unique = true)
    private String mobileNumber;

    @NotBlank(message = "Organization name cannot be blank")
    @NotEmpty(message = "Organization name cannot be empty")
    @NotNull(message = "Organization name cannot be null")
    @Column(name = "organization_name", nullable = false)
    private String organizationName;


    @NotBlank(message = "Organization type cannot be blank")
    @NotEmpty(message = "Organization type cannot be empty")
    @NotNull(message = "Organization type cannot be null")
    @Column(name = "organization_type", nullable = false)
    private String organizationType;

    @Column(name = "address")
    private String address;

    @Column(name = "country")
    private String country;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "state")
    private String state;

    @NotNull(message = "email cannot be null")
    @NotEmpty(message = "email cannot be empty")
    @NotBlank(message = "email cannot be blank")
    @Email(message = "provide valid email")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull(message = "password cannot be null")
    @NotEmpty(message = "password cannot be empty")
    @NotBlank(message = "password cannot be blank")
    @Column(name = "password", nullable = false)
    private String password;

    @Lob
    @Column(name = "profile_description")
    private String profileDescription;

    @NotNull(message = "Account active status cannot be null")
    @Column(name = "is_account_active", nullable = false)
    private Boolean isAccountActive = false;

    @NotNull(message = "Mobile verification cannot be null")
    @Column(name = "is_mobile_verified", nullable = false)
    private Boolean isMobileVerified = false;

    @NotNull(message = "Email verification cannot be null")
    @Column(name = "is_email_verified", nullable = false)
    private Boolean isEmailVerified = false;
}
