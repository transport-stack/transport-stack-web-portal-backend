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
public class DataSetListDto implements Serializable {
    private Long id;
    private String name;
    private String description;
    private LocalDate publishedDate;
    private String searchTags;
    private Long ancillaryServiceId;
    private String ancillaryServiceName;
    private Set<DataProviderMasterDto> dataProviders = new LinkedHashSet<>();
    private Long datasetTypeId;
    private String datasetTypeName;
    private Long transportModeId;
    private String transportModeName;

    /**
     * DTO for {@link in.transportstack.delhi.core.entity.master.DataProviderMaster}
     */
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataProviderMasterDto implements Serializable {
        private Long id;
        private String name;
    }
}