package in.transportstack.delhi.core.repository;

import in.transportstack.delhi.core.entity.DataSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSetRepository extends JpaRepository<DataSet, Long> {
}