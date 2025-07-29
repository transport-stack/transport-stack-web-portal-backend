package in.transportstack.delhi.usermanagement.service;

import in.transportstack.delhi.core.entity.type.UploadType;
import in.transportstack.delhi.usermanagement.dto.ServiceSetListDto;
import in.transportstack.delhi.usermanagement.dto.ServiceSetRequestDto;
import in.transportstack.delhi.usermanagement.dto.ServiceSetResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ServiceSetService {
    ServiceSetResponseDto createServiceSet(ServiceSetRequestDto serviceSetRequestDto);
    Page<ServiceSetListDto> getServiceSetList(int page, int size, String sortBy, String sortDir);
    UUID uploadFile(MultipartFile file, String title, UploadType fileUploadType);
    byte[] downloadFile(String key);
    String deleteFile(UUID id);
}
