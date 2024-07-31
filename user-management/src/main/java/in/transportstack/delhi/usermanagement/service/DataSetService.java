package in.transportstack.delhi.usermanagement.service;

import in.transportstack.delhi.usermanagement.dto.DataSetRequestDto;
import in.transportstack.delhi.usermanagement.dto.DataSetResponseDto;

public interface DataSetService {
    DataSetResponseDto createDataSet(DataSetRequestDto dataSetRequestDto);
}
