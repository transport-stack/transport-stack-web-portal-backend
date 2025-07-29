package in.transportstack.delhi.sharedconfig.service;

import in.transportstack.delhi.sharedconfig.dto.DeleteFileResponseDto;
import in.transportstack.delhi.sharedconfig.dto.UploadFileResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    UploadFileResponseDto upload(MultipartFile file, String bucketName) throws IOException;
    byte[] download(String key, String bucketName);
    DeleteFileResponseDto delete(String fileKey, String bucketName);
}
