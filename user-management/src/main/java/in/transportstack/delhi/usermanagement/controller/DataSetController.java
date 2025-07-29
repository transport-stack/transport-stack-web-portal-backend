package in.transportstack.delhi.usermanagement.controller;

import in.transportstack.delhi.core.entity.type.UploadType;
import in.transportstack.delhi.usermanagement.dto.DataSetListDto;
import in.transportstack.delhi.usermanagement.dto.DataSetRequestDto;
import in.transportstack.delhi.usermanagement.service.DataSetService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/dataset")
@CrossOrigin(origins = "*")
@Slf4j
public class DataSetController {

    private final DataSetService dataSetService;

    public DataSetController(DataSetService dataSetService) {
        this.dataSetService = dataSetService;
    }

    @PostMapping(value = "/create", consumes = {"application/json"})
    public ResponseEntity<?> createDataSet(@Valid @RequestBody DataSetRequestDto dataSetRequestDto) {
        return ResponseEntity.ok(dataSetService.createDataSet(dataSetRequestDto));
    }

    @GetMapping("/list")
    public Page<DataSetListDto> getDataSetList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return dataSetService.getDataSetList(page, size, sortBy, sortDir);
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                        @RequestParam("title") String title,
                                        @RequestParam("fileUploadType") UploadType fileUploadType) {
        UUID uuid = dataSetService.uploadFile(file, title, fileUploadType);

        if (uuid != null) {
            return ResponseEntity.ok(uuid.toString());
        }

        return ResponseEntity.badRequest().body("Unable to upload file");
    }

    @GetMapping(value = "/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("key") String key) {
        byte[] data = dataSetService.downloadFile(key);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + key + "\"")
                .body(resource);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("id") UUID id) {
        String response = dataSetService.deleteFile(id);
        return ResponseEntity.ok(response);
    }
}
