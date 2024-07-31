package in.transportstack.delhi.usermanagement.controller;

import in.transportstack.delhi.usermanagement.dto.DataSetRequestDto;
import in.transportstack.delhi.usermanagement.service.DataSetService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
