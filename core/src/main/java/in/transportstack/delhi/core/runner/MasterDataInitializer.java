package in.transportstack.delhi.core.runner;

import in.transportstack.delhi.core.entity.master.*;
import in.transportstack.delhi.core.repository.master.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MasterDataInitializer implements CommandLineRunner {
    private final AncillaryServiceMasterRepository ancillaryServiceMasterRepository;
    private final ApprovalMechanismMasterRepository approvalMechanismMasterRepository;
    private final ChargingModelMasterRepository chargingModelMasterRepository;
    private final DataAccessTypeMasterRepository dataAccessTypeMasterRepository;
    private final DataProviderMasterRepository dataProviderMasterRepository;
    private final DatasetTypeMasterRepository datasetTypeMasterRepository;
    private final ServiceProviderMasterRepository serviceProviderMasterRepository;
    private final TransportModeMasterRepository transportModeMasterRepository;

    public MasterDataInitializer(
            AncillaryServiceMasterRepository ancillaryServiceMasterRepository,
            ApprovalMechanismMasterRepository approvalMechanismMasterRepository,
            ChargingModelMasterRepository chargingModelMasterRepository,
            DataAccessTypeMasterRepository dataAccessTypeMasterRepository,
            DataProviderMasterRepository dataProviderMasterRepository,
            DatasetTypeMasterRepository datasetTypeMasterRepository,
            ServiceProviderMasterRepository serviceProviderMasterRepository,
            TransportModeMasterRepository transportModeMasterRepository
    ) {
        this.ancillaryServiceMasterRepository = ancillaryServiceMasterRepository;
        this.approvalMechanismMasterRepository = approvalMechanismMasterRepository;
        this.chargingModelMasterRepository = chargingModelMasterRepository;
        this.dataAccessTypeMasterRepository = dataAccessTypeMasterRepository;
        this.dataProviderMasterRepository = dataProviderMasterRepository;
        this.datasetTypeMasterRepository = datasetTypeMasterRepository;
        this.serviceProviderMasterRepository = serviceProviderMasterRepository;
        this.transportModeMasterRepository = transportModeMasterRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        this.initAncillaryServiceMasterData();
        this.initApprovalMechanismMasterData();
        this.initChargingModelMasterData();
        this.initDataAccessTypeMasterData();
        this.initDataProviderMasterData();
        this.initDatasetTypeMasterData();
        this.initServiceProviderMasterData();
        this.initTransportModeMasterData();
    }

    private void initAncillaryServiceMasterData() {
        if (ancillaryServiceMasterRepository.count() == 0) {
            Arrays.asList("Parking", "EV Charging", "Lockers").forEach(name ->
                    ancillaryServiceMasterRepository.save(new AncillaryServiceMaster(name)));
        }
    }

    private void initApprovalMechanismMasterData() {
        if (approvalMechanismMasterRepository.count() == 0) {
            Arrays.asList("Auto", "Manual").forEach(name ->
                    approvalMechanismMasterRepository.save(new ApprovalMechanismMaster(name)));
        }
    }

    private void initChargingModelMasterData() {
        if (chargingModelMasterRepository.count() == 0) {
            Arrays.asList("Free", "Paid").forEach(name ->
                    chargingModelMasterRepository.save(new ChargingModelMaster(name)));
        }
    }

    private void initDataAccessTypeMasterData() {
        if (dataAccessTypeMasterRepository.count() == 0) {
            Arrays.asList("Download", "Subscribe").forEach(name ->
                    dataAccessTypeMasterRepository.save(new DataAccessTypeMaster(name)));
        }
    }

    private void initDataProviderMasterData() {
        if (dataProviderMasterRepository.count() == 0) {
            Arrays.asList("DTC", "DIMTS", "GMCBL", "DMRC", "NMRC", "RMGL", "NCRTC", "MCD", "Transport Dept. (GNCTD)")
                    .forEach(name -> dataProviderMasterRepository.save(new DataProviderMaster(name)));
        }
    }

    private void initDatasetTypeMasterData() {
        if (datasetTypeMasterRepository.count() == 0) {
            Arrays.asList("Core Transport", "Ancillary").forEach(name ->
                    datasetTypeMasterRepository.save(new DatasetTypeMaster(name)));
        }
    }

    private void initServiceProviderMasterData() {
        if (serviceProviderMasterRepository.count() == 0) {
            Arrays.asList("DTC", "DIMTS", "GMCBL", "DMRC", "NMRC", "RMGL", "NCRTC", "MCD", "Transport Dept. (GNCTD)", "Transport Stack")
                    .forEach(name -> serviceProviderMasterRepository.save(new ServiceProviderMaster(name)));
        }
    }

    private void initTransportModeMasterData() {
        if (transportModeMasterRepository.count() == 0) {
            Arrays.asList("Metro", "Bus", "Rapid Rail", "Road Transport").forEach(name ->
                    transportModeMasterRepository.save(new TransportModeMaster(name)));
        }
    }
}
