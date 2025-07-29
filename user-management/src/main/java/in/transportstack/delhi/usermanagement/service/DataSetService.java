package in.transportstack.delhi.usermanagement.service;

import in.transportstack.delhi.core.entity.type.UploadType;
import in.transportstack.delhi.usermanagement.dto.DataSetListDto;
import in.transportstack.delhi.usermanagement.dto.DataSetRequestDto;
import in.transportstack.delhi.usermanagement.dto.DataSetResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface DataSetService {
    DataSetResponseDto createDataSet(DataSetRequestDto dataSetRequestDto);
    Page<DataSetListDto> getDataSetList(int page, int size, String sortBy, String sortDir);
    UUID uploadFile(MultipartFile file, String title, UploadType fileUploadType);
    byte[] downloadFile(String key);
    String deleteFile(UUID id);
}
