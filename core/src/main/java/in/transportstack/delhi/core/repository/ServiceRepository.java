package in.transportstack.delhi.core.repository;

import in.transportstack.delhi.core.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}