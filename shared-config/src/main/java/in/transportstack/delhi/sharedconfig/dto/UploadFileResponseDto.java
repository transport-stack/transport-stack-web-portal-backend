package in.transportstack.delhi.sharedconfig.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UploadFileResponseDto {
    private String fileName;
    private String fileUrl;
    private PutObjectResponse putObjectResponse;
}
