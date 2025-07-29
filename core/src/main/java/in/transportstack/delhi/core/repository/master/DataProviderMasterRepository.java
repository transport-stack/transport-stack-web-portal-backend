package in.transportstack.delhi.core.repository.master;

import in.transportstack.delhi.core.entity.master.DataProviderMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataProviderMasterRepository extends JpaRepository<DataProviderMaster, Long> {
}