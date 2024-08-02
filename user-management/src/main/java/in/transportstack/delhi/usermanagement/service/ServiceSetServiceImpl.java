package in.transportstack.delhi.usermanagement.service;


import in.transportstack.delhi.core.entity.ServiceSet;
import in.transportstack.delhi.core.entity.ServiceSetDocument;
import in.transportstack.delhi.core.entity.master.ServiceSetProviderMaster;
import in.transportstack.delhi.core.entity.type.UploadType;
import in.transportstack.delhi.core.repository.ServiceSetDocumentRepository;
import in.transportstack.delhi.core.repository.ServiceSetRepository;
import in.transportstack.delhi.core.repository.master.*;
import in.transportstack.delhi.sharedconfig.dto.UploadFileResponseDto;
import in.transportstack.delhi.sharedconfig.service.FileService;
import in.transportstack.delhi.usermanagement.dto.ServiceSetListDto;
import in.transportstack.delhi.usermanagement.dto.ServiceSetRequestDto;
import in.transportstack.delhi.usermanagement.dto.ServiceSetResponseDto;
import in.transportstack.delhi.usermanagement.mapper.ServiceSetMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ServiceSetServiceImpl implements ServiceSetService {

    @Value("${application.bucket.name}")
    private String bucketName;


    private final ServiceSetMapper serviceSetMapper;
    private final ServiceSetRepository serviceSetRepository;
    private final ServiceSetDocumentRepository serviceSetDocumentRepository;
    private final ServiceSetTypeMasterRepository serviceSetTypeMasterRepository;
    private final FileService fileService;
    private final AncillaryServiceMasterRepository ancillaryServiceMasterRepository;
    private final ApprovalMechanismMasterRepository approvalMechanismMasterRepository;
    private final ChargingModelMasterRepository chargingModelMasterRepository;
    private final ServiceSetProviderMasterRepository serviceSetProviderMasterRepository;
    private final TransportModeMasterRepository transportModeMasterRepository;
    public ServiceSetServiceImpl(
            ServiceSetMapper serviceSetMapper,
            ServiceSetRepository serviceSetRepository,
            ServiceSetDocumentRepository serviceSetDocumentRepository,
            ServiceSetTypeMasterRepository serviceSetTypeMasterRepository,
            FileService fileService,
            AncillaryServiceMasterRepository ancillaryServiceMasterRepository,
            ApprovalMechanismMasterRepository approvalMechanismMasterRepository,
            ChargingModelMasterRepository chargingModelMasterRepository,
            ServiceSetProviderMasterRepository serviceSetProviderMasterRepository,
            TransportModeMasterRepository transportModeMasterRepository
    ) {
        this.serviceSetMapper = serviceSetMapper;
        this.serviceSetRepository = serviceSetRepository;
        this.serviceSetDocumentRepository = serviceSetDocumentRepository;
        this.serviceSetTypeMasterRepository=serviceSetTypeMasterRepository;
        this.fileService = fileService;
        this.ancillaryServiceMasterRepository=ancillaryServiceMasterRepository;
        this.approvalMechanismMasterRepository=approvalMechanismMasterRepository;
        this.serviceSetProviderMasterRepository=serviceSetProviderMasterRepository;
        this.chargingModelMasterRepository=chargingModelMasterRepository;
        this.transportModeMasterRepository=transportModeMasterRepository;
    }

    @Override
    public ServiceSetResponseDto createServiceSet(ServiceSetRequestDto serviceSetRequestDto) {
        if (serviceSetRepository.existsByNameIgnoreCase(serviceSetRequestDto.getName())) {
            return new ServiceSetResponseDto("ServiceSet already exists with given name");
        }

        // Mapper will map all the field accordingly
         ServiceSet serviceSet = serviceSetMapper.toEntity(serviceSetRequestDto);

        // Set Ancillary Mechanism
        serviceSet.setAncillaryService(ancillaryServiceMasterRepository.findById(serviceSetRequestDto.getAncillaryServiceId())
                .orElseThrow(() -> new EntityNotFoundException("AncillaryServiceMaster not found")));

        // Set Approval Mechanism
        serviceSet.setApprovalMechanism(
                approvalMechanismMasterRepository.findById(serviceSetRequestDto.getApprovalMechanismId())
                        .orElseThrow(() -> new EntityNotFoundException("ApprovalMechanismMaster not found"))
        );



        // Set Data Provider
        Set<ServiceSetProviderMaster> serviceProviders = serviceSetRequestDto.getServiceProviderIds().stream()
                .map(id -> serviceSetProviderMasterRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("ServiceSetProviderMaster not found with id: " + id)))
                .collect(Collectors.toSet());
        serviceSet.setServiceProviders(serviceProviders);

        // Set Serviceset Type
        serviceSet.setServiceSetType(
                serviceSetTypeMasterRepository.findById(serviceSetRequestDto.getServiceSetTypeId())
                        .orElseThrow(() -> new EntityNotFoundException("ServiceSetTypeMaster not found"))
        );


        // Set Charging Model
        serviceSet.setChargingModel(
                chargingModelMasterRepository.findById(serviceSetRequestDto.getChargingModelId())
                        .orElseThrow(() -> new EntityNotFoundException("ChargingModelMaster not found"))
        );

        // Set Transport Mode
        serviceSet.setTransportMode(
                transportModeMasterRepository.findById(serviceSetRequestDto.getTransportModeId())
                        .orElseThrow(() -> new EntityNotFoundException("TransportModeMaster not found"))
        );


        try {
            // Saving Service
            serviceSetRepository.save(serviceSet);
            return new ServiceSetResponseDto("Service created successfully");
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ServiceSetResponseDto(e.getMessage());
        }
    }

    @Override
    public Page<ServiceSetListDto> getServiceSetList(int page, int size, String sortBy, String sortDir) {
        Sort sort = Sort.by(sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ServiceSet> serviceSet = serviceSetRepository.findAllByIsSoftDeleteFalse(pageable);
        return serviceSet.map(serviceSetMapper::toListingDto);
    }

    @Override
    public UUID uploadFile(MultipartFile file, String title, UploadType fileUploadType) {
        try {
            UploadFileResponseDto uploadFileResponseDto = fileService.upload(file, bucketName);
            ServiceSetDocument serviceSetDocument = new ServiceSetDocument();

            serviceSetDocument.setTitle(title);
            serviceSetDocument.setUrl(uploadFileResponseDto.getFileUrl());
            serviceSetDocument.setUploadType(fileUploadType);
            serviceSetDocument.setFileKey(uploadFileResponseDto.getFileName());
            //dataSetDocument.setDataSet(new DataSet());
            ServiceSetDocument serviceSetDocumentObj=serviceSetDocumentRepository.save(serviceSetDocument);
             return serviceSetDocumentObj.getId();





        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public byte[] downloadFile(String key) {
        try {
            return fileService.download(key, bucketName);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public String deleteFile(UUID id) {
        Optional<ServiceSetDocument> serviceSetDocument =serviceSetDocumentRepository.findById(id);
        if (serviceSetDocument.isPresent()) {
            String fileKey = serviceSetDocument.get().getFileKey();
            fileService.delete(fileKey, bucketName);
            serviceSetDocumentRepository.delete(serviceSetDocument.get());
            return "Document deleted successfully " + id;
        }
        return "Document not found for given id: " + id;
    }
}
