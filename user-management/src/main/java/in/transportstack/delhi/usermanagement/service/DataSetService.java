package in.transportstack.delhi.usermanagement.service;

import in.transportstack.delhi.core.entity.type.UploadType;
import in.transportstack.delhi.usermanagement.dto.DataSetRequestDto;
import in.transportstack.delhi.usermanagement.dto.DataSetResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface DataSetService {
    DataSetResponseDto createDataSet(DataSetRequestDto dataSetRequestDto);
    UUID uploadFile(MultipartFile file, String title, UploadType fileUploadType);
    byte[] downloadFile(String key);
    String deleteFile(UUID id);
}
