package in.transportstack.delhi.core.repository.master;

import in.transportstack.delhi.core.entity.master.DatasetTypeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatasetTypeMasterRepository extends JpaRepository<DatasetTypeMaster, Long> {
}