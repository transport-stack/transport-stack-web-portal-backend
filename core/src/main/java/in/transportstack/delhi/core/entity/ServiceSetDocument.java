package in.transportstack.delhi.core.entity;

import in.transportstack.delhi.core.entity.type.UploadType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "service_set_document")
public class ServiceSetDocument extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotBlank(message = "Title cannot be blank")
    @NotEmpty(message = "Title cannot be empty")
    @NotNull(message = "Title cannot be null")
    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "upload_type", nullable = false)
    private UploadType uploadType;


    @NotBlank(message = "Url cannot be blank")
    @NotEmpty(message = "Url cannot be empty")
    @NotNull(message = "Url cannot be null")
    @Column(name = "url", nullable = false, unique = true)
    private String url;

    @NotBlank(message = "File key cannot be blank")
    @NotEmpty(message = "File key cannot be empty")
    @NotNull(message = "File key cannot be null")
    @Column(name = "file_key", nullable = false)
    private String fileKey;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_set_id")
    private ServiceSet serviceSet;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        DataSetDocument that = (DataSetDocument) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}