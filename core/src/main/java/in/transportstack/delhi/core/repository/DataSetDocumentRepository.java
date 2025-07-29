package in.transportstack.delhi.core.repository;

import in.transportstack.delhi.core.entity.DataSetDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DataSetDocumentRepository extends JpaRepository<DataSetDocument, UUID> {
}