package project.appointment.specialist;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import project.appointment.specialist.dto.*;
import project.appointment.specialist.entity.Specialist;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SpecialistMapper {
    @Mapping(target = "specializations", ignore = true)
    @Mapping(target = "services", ignore = true)
    @Mapping(target = "appointments", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    Specialist specialistRequestDtoToSpecialist(SpecialistRequestDto specialistRequestDto);

    @Mapping(target = "specializations", source = "specializations")
    @Mapping(target = "services", source = "services")
    SpecialistResponseDto specialistToSpecialistResponseDto(Specialist specialist);

    SpecialistShortDto specialistToSpecialistShortDto(Specialist specialist);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "role", ignore = true)
    void updateSpecialistFromDto(SpecialistUpdateDto specialistUpdateDto, @MappingTarget Specialist specialist);

    @Mapping(target = "appointments", source = "appointments")
    @Mapping(target = "reviews", source = "reviews")
    SpecialistDetailedDto specialistToSpecialistDetailedDto(Specialist specialist);
}
