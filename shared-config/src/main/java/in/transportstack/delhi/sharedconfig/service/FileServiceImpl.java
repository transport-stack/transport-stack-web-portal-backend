package in.transportstack.delhi.sharedconfig.service;

import in.transportstack.delhi.sharedconfig.dto.DeleteFileResponseDto;
import in.transportstack.delhi.sharedconfig.dto.UploadFileResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.IOException;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    private final S3Client s3Client;

    public FileServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public UploadFileResponseDto upload(MultipartFile file, String bucketName) throws IOException {
        log.info("Uploading file {} to bucket {}", file.getOriginalFilename(), bucketName);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        GetUrlRequest getUrlRequest = GetUrlRequest.builder().bucket(bucketName).key(fileName).build();
        String fileUrl = s3Client.utilities().getUrl(getUrlRequest).toExternalForm();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(fileName).build();
        PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest,
                RequestBody.fromBytes(file.getBytes()));

        return new UploadFileResponseDto(fileName, fileUrl, putObjectResponse);
    }

    @Override
    public byte[] download(String key, String bucketName) {
        log.info("downloading object from bucket {} with key {}", bucketName, key);
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        ResponseBytes<GetObjectResponse> responseBytes = s3Client.getObjectAsBytes(getObjectRequest);
        return responseBytes.asByteArray();
    }

    @Override
    public DeleteFileResponseDto delete(String key, String bucketName) {
        log.info("deleting file from bucket {} with key {}", bucketName, key);
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        DeleteObjectResponse response = s3Client.deleteObject(deleteObjectRequest);
        return new DeleteFileResponseDto(response);
    }
}
