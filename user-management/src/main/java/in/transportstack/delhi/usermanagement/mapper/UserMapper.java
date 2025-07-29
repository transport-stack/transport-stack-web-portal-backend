package in.transportstack.delhi.usermanagement.mapper;

import in.transportstack.delhi.core.entity.User;
import in.transportstack.delhi.usermanagement.dto.RegisterUserRequestDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntity(RegisterUserRequestDto registerUserRequestDto);

    RegisterUserRequestDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(RegisterUserRequestDto registerUserRequestDto, @MappingTarget User user);
}