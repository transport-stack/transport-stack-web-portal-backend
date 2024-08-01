package in.transportstack.delhi.usermanagement.service;

import in.transportstack.delhi.core.entity.DataSet;
import in.transportstack.delhi.core.entity.DataSetDocument;
import in.transportstack.delhi.core.entity.type.UploadType;
import in.transportstack.delhi.core.repository.DataSetDocumentRepository;
import in.transportstack.delhi.core.repository.DataSetRepository;
import in.transportstack.delhi.sharedconfig.dto.UploadFileResponseDto;
import in.transportstack.delhi.sharedconfig.service.FileService;
import in.transportstack.delhi.usermanagement.dto.DataSetListDto;
import in.transportstack.delhi.usermanagement.dto.DataSetRequestDto;
import in.transportstack.delhi.usermanagement.dto.DataSetResponseDto;
import in.transportstack.delhi.usermanagement.mapper.DataSetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class DataSetServiceImpl implements DataSetService {

    @Value("${application.bucket.name}")
    private String bucketName;

    private final DataSetMapper dataSetMapper;
    private final DataSetRepository dataSetRepository;
    private final DataSetDocumentRepository dataSetDocumentRepository;
    private final FileService fileService;

    public DataSetServiceImpl(
            DataSetMapper dataSetMapper,
            DataSetRepository dataSetRepository,
            DataSetDocumentRepository dataSetDocumentRepository,
            FileService fileService
    ) {
        this.dataSetMapper = dataSetMapper;
        this.dataSetRepository = dataSetRepository;
        this.dataSetDocumentRepository = dataSetDocumentRepository;
        this.fileService = fileService;
    }

    @Override
    public DataSetResponseDto createDataSet(DataSetRequestDto dataSetRequestDto) {
        if (dataSetRepository.existsByNameIgnoreCase(dataSetRequestDto.getName())) {
            return new DataSetResponseDto("DataSet already exists with given name");
        }

        // Mapper will map all the field accordingly
        DataSet dataSet = dataSetMapper.toEntity(dataSetRequestDto);

        try {
            // Saving DataSet
            dataSetRepository.save(dataSet);
            return new DataSetResponseDto("DataSet created successfully");
        } catch (Exception e) {
            log.error(e.getMessage());
            return new DataSetResponseDto(e.getMessage());
        }
    }

    @Override
    public Page<DataSetListDto> getDataSetList(int page, int size, String sortBy, String sortDir) {
        Sort sort = Sort.by(sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<DataSet> dataSets = dataSetRepository.findAllByIsSoftDeleteFalse(pageable);
        return dataSets.map(dataSetMapper::toListingDto);
    }

    @Override
    public UUID uploadFile(MultipartFile file, String title, UploadType fileUploadType) {
        try {
            UploadFileResponseDto uploadFileResponseDto = fileService.upload(file, bucketName);
            DataSetDocument dataSetDocument = new DataSetDocument();

            dataSetDocument.setTitle(title);
            dataSetDocument.setUrl(uploadFileResponseDto.getFileUrl());
            dataSetDocument.setUploadType(fileUploadType);
            dataSetDocument.setKey(uploadFileResponseDto.getFileName());

            return dataSetDocumentRepository.save(dataSetDocument).getId();

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
        Optional<DataSetDocument> dataSetDocument = dataSetDocumentRepository.findById(id);
        if (dataSetDocument.isPresent()) {
            String fileKey = dataSetDocument.get().getKey();
            fileService.delete(fileKey, bucketName);
            dataSetDocumentRepository.delete(dataSetDocument.get());
            return "Document deleted successfully " + id;
        }
        return "Document not found for given id: " + id;
    }
}
