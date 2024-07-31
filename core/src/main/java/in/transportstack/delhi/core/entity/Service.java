package in.transportstack.delhi.core.entity;

import in.transportstack.delhi.core.entity.master.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "service")
public class Service extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "date_published")
    private LocalDate datePublished;

    @Column(name = "version")
    private String version;

    @Column(name = "update_frequency")
    private String updateFrequency;

    @Lob
    @Column(name = "search_tags")
    private String searchTags;

    @Column(name = "default_fee")
    private Double defaultFee;

    @Column(name = "link_to_access")
    private String linkToAccess;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_of_service_id")
    private DatasetTypeMaster serviceType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ancillary_service_id")
    private AncillaryServiceMaster ancillaryService;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transport_mode_id")
    private TransportModeMaster transportMode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_provider_id")
    private ServiceProviderMaster serviceProvider;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "approval_mechanism_id")
    private ApprovalMechanismMaster approvalMechanism;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "charging_model_id")
    private ChargingModelMaster chargingModel;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ServiceDocument> serviceDocuments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ServiceDocument> licenseAgreement = new LinkedHashSet<>();

    @Column(name = "is_soft_delete")
    private Boolean isSoftDelete;

}