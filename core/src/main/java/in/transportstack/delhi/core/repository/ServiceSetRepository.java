package in.transportstack.delhi.core.repository;


import in.transportstack.delhi.core.entity.ServiceSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface ServiceSetRepository extends JpaRepository<ServiceSet, Long> {
    ServiceSet findByNameIgnoreCase(@NonNull String name);
    Boolean existsByNameIgnoreCase(@NonNull String name);
    Page<ServiceSet> findAllByIsSoftDeleteFalse(Pageable pageable);
}