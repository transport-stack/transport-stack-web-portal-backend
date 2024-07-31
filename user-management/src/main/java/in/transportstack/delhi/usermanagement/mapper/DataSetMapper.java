package in.transportstack.delhi.usermanagement.mapper;

import in.transportstack.delhi.core.entity.DataSet;
import in.transportstack.delhi.core.entity.DataSetDocument;
import in.transportstack.delhi.core.entity.master.DataProviderMaster;
import in.transportstack.delhi.usermanagement.dto.DataSetRequestDto;
import org.mapstruct.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DataSetMapper {
    @Mapping(source = "transportModeId", target = "transportMode.id")
    @Mapping(source = "datasetTypeId", target = "datasetType.id")
    @Mapping(source = "dataAccessTypeId", target = "dataAccessType.id")
    @Mapping(source = "chargingModelId", target = "chargingModel.id")
    @Mapping(source = "approvalMechanismId", target = "approvalMechanism.id")
    @Mapping(source = "ancillaryServiceId", target = "ancillaryService.id")
    DataSet toEntity(DataSetRequestDto dataSetRequestDto);

    @InheritInverseConfiguration(name = "toEntity")
    @Mapping(target = "dataProviderIds", expression = "java(dataProviderToDataProviderIds(dataSet.getDataProviders()))")
    @Mapping(target = "documentIds", expression = "java(documentToDocumentIds(dataSet.getDocuments()))")
    @Mapping(target = "dataFileIds", expression = "java(dataFileToDataFileIds(dataSet.getDataFiles()))")
    @Mapping(target = "licenseAgreementIds", expression = "java(licenseAgreementToLicenseAgreementIds(dataSet.getLicenseAgreements()))")
    DataSetRequestDto toDto(DataSet dataSet);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DataSet partialUpdate(DataSetRequestDto dataSetRequestDto, @MappingTarget DataSet dataSet);

    default Set<Long> dataProviderToDataProviderIds(Set<DataProviderMaster> dataProvider) {
        return dataProvider.stream().map(DataProviderMaster::getId).collect(Collectors.toSet());
    }

    default Set<UUID> documentToDocumentIds(Set<DataSetDocument> document) {
        return document.stream().map(DataSetDocument::getId).collect(Collectors.toSet());
    }

    default Set<UUID> dataFileToDataFileIds(Set<DataSetDocument> dataFile) {
        return dataFile.stream().map(DataSetDocument::getId).collect(Collectors.toSet());
    }

    default Set<UUID> licenseAgreementToLicenseAgreementIds(Set<DataSetDocument> licenseAgreement) {
        return licenseAgreement.stream().map(DataSetDocument::getId).collect(Collectors.toSet());
    }
}