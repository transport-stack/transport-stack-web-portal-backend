package in.transportstack.delhi.core.repository;

import in.transportstack.delhi.core.entity.DataSet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface DataSetRepository extends JpaRepository<DataSet, Long> {
    DataSet findByNameIgnoreCase(@NonNull String name);
    Boolean existsByNameIgnoreCase(@NonNull String name);
    Page<DataSet> findAllByIsSoftDeleteFalse(Pageable pageable);
}