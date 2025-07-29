package in.transportstack.delhi.core.entity;

import in.transportstack.delhi.core.entity.master.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "data_set")
public class DataSet extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "link_to_access")
    private String linkToAccess;

    @Column(name = "published_date")
    private LocalDate publishedDate;

    @Column(name = "default_fee")
    private Double defaultFee;

    @Column(name = "file_format")
    private String fileFormat;

    @Column(name = "is_api_available", nullable = false)
    private Boolean isApiAvailable = false;

    @Column(name = "processing_of_values")
    private String processingOfValues;

    @Lob
    @Column(name = "search_tags")
    private String searchTags;

    @Column(name = "update_frequency")
    private String updateFrequency;

    @Column(name = "version")
    private String version;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ancillary_service_id")
    private AncillaryServiceMaster ancillaryService;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "approval_mechanism_id")
    private ApprovalMechanismMaster approvalMechanism;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "charging_model_id")
    private ChargingModelMaster chargingModel;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "data_access_type_id")
    private DataAccessTypeMaster dataAccessType;

    @ToString.Exclude
    @OneToMany(mappedBy = "dataSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DataProviderMaster> dataProviders = new LinkedHashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dataset_type_id")
    private DatasetTypeMaster datasetType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transport_mode_id")
    private TransportModeMaster transportMode;

    @Column(name = "data_fields_covered")
    private String dataFieldsCovered;

    @ToString.Exclude
    @OneToMany(mappedBy = "dataSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DataSetDocument> documents = new LinkedHashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "dataSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DataSetDocument> dataFiles = new LinkedHashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "dataSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DataSetDocument> licenseAgreements = new LinkedHashSet<>();

    @Column(name = "is_soft_delete")
    private Boolean isSoftDelete;

}