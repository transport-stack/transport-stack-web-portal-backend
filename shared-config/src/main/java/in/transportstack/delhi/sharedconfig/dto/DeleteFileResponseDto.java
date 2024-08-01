package in.transportstack.delhi.sharedconfig.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DeleteFileResponseDto {
    private DeleteObjectResponse deleteObjectResponse;
}
