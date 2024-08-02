package in.transportstack.delhi.usermanagement.mapper;


import in.transportstack.delhi.core.entity.ServiceSet;
import in.transportstack.delhi.core.entity.ServiceSetDocument;
import in.transportstack.delhi.core.entity.master.ServiceSetProviderMaster;
import in.transportstack.delhi.usermanagement.dto.ServiceSetListDto;
import in.transportstack.delhi.usermanagement.dto.ServiceSetRequestDto;
import org.mapstruct.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ServiceSetMapper {
    @Mapping(source = "transportModeId", target = "transportMode.id")
    @Mapping(source = "serviceSetTypeId", target = "serviceSetType.id")
    @Mapping(source = "chargingModelId", target = "chargingModel.id")
    @Mapping(source = "approvalMechanismId", target = "approvalMechanism.id")
    @Mapping(source = "ancillaryServiceId", target = "ancillaryService.id")
    ServiceSet toEntity(ServiceSetRequestDto serviceSetRequestDto);

    @InheritInverseConfiguration(name = "toEntity")
    @Mapping(target = "serviceProviderIds", expression = "java(serviceProviderToServiceProviderIds(serviceSet.getServiceProviders()))")
    @Mapping(target = "documentIds", expression = "java(documentToDocumentIds(serviceSet.getServiceSetDocuments()))")
    @Mapping(target = "licenseAgreementIds", expression = "java(licenseAgreementToLicenseAgreementIds(serviceSet.getLicenseAgreements()))")
    ServiceSetRequestDto toDto(ServiceSet serviceSet);

    @InheritInverseConfiguration(name = "toEntity")
    ServiceSetListDto toListingDto(ServiceSet serviceSet);

    @AfterMapping
    default void linkServiceProviders(@MappingTarget ServiceSet serviceSet) {
        serviceSet.getServiceProviders().forEach(serviceProvider -> serviceProvider.setServiceSet(serviceSet));
    }

    default Set<Long> serviceProviderToServiceProviderIds(Set<ServiceSetProviderMaster> serviceProvider) {
        return serviceProvider.stream().map(ServiceSetProviderMaster::getId).collect(Collectors.toSet());
    }

    default Set<UUID> documentToDocumentIds(Set<ServiceSetDocument> document) {
        return document.stream().map(ServiceSetDocument::getId).collect(Collectors.toSet());
    }


    default Set<UUID> licenseAgreementToLicenseAgreementIds(Set<ServiceSetDocument> licenseAgreement) {
        return licenseAgreement.stream().map(ServiceSetDocument::getId).collect(Collectors.toSet());
    }
}
