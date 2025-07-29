package in.transportstack.delhi.usermanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link in.transportstack.delhi.core.entity.DataSet}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceSetRequestDto implements Serializable {
    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    @NotBlank(message = "Name cannot be blank")
    private String name;
    private String description;
    private String linkToAccess;
    private LocalDate publishedDate;
    private Double defaultFee;
    private String searchTags;
    private String updateFrequency;
    private String version;
    private Long ancillaryServiceId;
    private Long approvalMechanismId;
    private Long chargingModelId;
    private Set<Long> serviceProviderIds;
    private Long serviceSetTypeId;
    private Long transportModeId;
    private Set<UUID> documentIds;
    private Set<UUID> licenseAgreementIds;
    private Boolean isSoftDelete;
}