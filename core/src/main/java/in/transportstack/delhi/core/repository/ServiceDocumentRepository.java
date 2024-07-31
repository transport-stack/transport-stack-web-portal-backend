package in.transportstack.delhi.core.repository;

import in.transportstack.delhi.core.entity.ServiceDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServiceDocumentRepository extends JpaRepository<ServiceDocument, UUID> {
}