package in.transportstack.delhi.usermanagement.controller;

import in.transportstack.delhi.core.entity.type.UploadType;
import in.transportstack.delhi.usermanagement.dto.ServiceSetListDto;
import in.transportstack.delhi.usermanagement.dto.ServiceSetRequestDto;
import in.transportstack.delhi.usermanagement.service.ServiceSetService;
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
@RequestMapping("/api/service")
@CrossOrigin(origins = "*")
@Slf4j
public class ServiceSetController {

    private final ServiceSetService serviceSetService;

    public ServiceSetController(ServiceSetService serviceSetService) {
        this.serviceSetService = serviceSetService;
    }

    @PostMapping(value = "/create", consumes = {"application/json"})
    public ResponseEntity<?> createServiceSet(@Valid @RequestBody ServiceSetRequestDto serviceSetRequestDto) {
        return ResponseEntity.ok(serviceSetService.createServiceSet(serviceSetRequestDto));
    }

    @GetMapping("/list")
    public Page<ServiceSetListDto> getDataSetList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return serviceSetService.getServiceSetList(page, size, sortBy, sortDir);
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                        @RequestParam("title") String title,
                                        @RequestParam("fileUploadType") UploadType fileUploadType) {
        UUID uuid = serviceSetService.uploadFile(file, title, fileUploadType);

        if (uuid != null) {
            return ResponseEntity.ok(uuid.toString());
        }

        return ResponseEntity.badRequest().body("Unable to upload file");
    }

    @GetMapping(value = "/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("key") String key) {
        byte[] data = serviceSetService.downloadFile(key);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + key + "\"")
                .body(resource);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("id") UUID id) {
        String response = serviceSetService.deleteFile(id);
        return ResponseEntity.ok(response);
    }
}
