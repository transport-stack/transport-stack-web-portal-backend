package in.transportstack.delhi.usermanagement.service;

import in.transportstack.delhi.core.entity.DataSet;
import in.transportstack.delhi.core.repository.DataSetRepository;
import in.transportstack.delhi.usermanagement.dto.DataSetRequestDto;
import in.transportstack.delhi.usermanagement.dto.DataSetResponseDto;
import in.transportstack.delhi.usermanagement.mapper.DataSetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataSetServiceImpl implements DataSetService {

    private final DataSetMapper dataSetMapper;
    private final DataSetRepository dataSetRepository;

    public DataSetServiceImpl(
            DataSetMapper dataSetMapper,
            DataSetRepository dataSetRepository
    ) {
        this.dataSetMapper = dataSetMapper;
        this.dataSetRepository = dataSetRepository;
    }

    @Override
    public DataSetResponseDto createDataSet(DataSetRequestDto dataSetRequestDto) {
        if (dataSetRepository.existsByNameIgnoreCase(dataSetRequestDto.getName())) {
            return new DataSetResponseDto("DataSet already exists with given name");
        }

        // Mapper will map all the field accordingly
        DataSet dataSet = dataSetMapper.toEntity(dataSetRequestDto);

        try {
            // Saving DataSet
            dataSetRepository.save(dataSet);
            return new DataSetResponseDto("DataSet created successfully");
        } catch (Exception e) {
            log.error(e.getMessage());
            return new DataSetResponseDto(e.getMessage());
        }
    }
}
