package project.appointment.services;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import project.appointment.services.dto.ServiceRequestDto;
import project.appointment.services.dto.ServiceResponseDto;
import project.appointment.services.dto.ServiceShortDto;
import project.appointment.services.dto.ServiceUpdateDto;
import project.appointment.services.entity.Service;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServiceMapper {
    @Mapping(target = "specialization", ignore = true)
    @Mapping(target = "specialists", ignore = true)
    Service serviceRequestDtoToService(ServiceRequestDto serviceRequestDto);

    @Mapping(target = "specialization", source = "specialization")
    @Mapping(target = "specialists", source = "specialists")
    ServiceResponseDto serviceToServiceResponseDto(Service service);

    ServiceShortDto serviceToServiceShortDto(Service service);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "specialization", ignore = true)
    @Mapping(target = "specialists", ignore = true)
    void updateServiceFromDto(ServiceUpdateDto serviceUpdateDto, @MappingTarget Service service);
}
