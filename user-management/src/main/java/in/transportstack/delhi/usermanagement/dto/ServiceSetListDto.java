package in.transportstack.delhi.usermanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * DTO for {@link in.transportstack.delhi.core.entity.DataSet}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceSetListDto implements Serializable {
    private Long id;
    private String name;
    private String description;
    private LocalDate publishedDate;
    private String searchTags;
    private Long ancillaryServiceSetId;
    private String ancillaryServiceSetName;
    private Set<ServiceSetProviderMasterDto> serviceSetProviders = new LinkedHashSet<>();
    private Long serviceSetTypeId;
    private String serviceSetTypeName;
    private Long transportModeId;
    private String transportModeName;

    /**
     * DTO for {@link in.transportstack.delhi.core.entity.master.ServiceSetProviderMaster}
     */
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ServiceSetProviderMasterDto implements Serializable {
        private Long id;
        private String name;
    }
}