package in.transportstack.delhi.core.repository;

import in.transportstack.delhi.core.entity.ServiceSetDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServiceSetDocumentRepository extends JpaRepository<ServiceSetDocument, UUID> {
}