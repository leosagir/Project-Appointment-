package project.appointment.specialization;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import project.appointment.specialization.dto.SpecializationRequestDto;
import project.appointment.specialization.dto.SpecializationResponseDto;
import project.appointment.specialization.dto.SpecializationShortDto;
import project.appointment.specialization.dto.SpecializationUpdateDto;
import project.appointment.specialization.entity.Specialization;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SpecializationMapper {
    @Mapping(target = "specialists", ignore = true)
    @Mapping(target = "services", ignore = true)
    Specialization specializationRequestDtoToSpecialization(SpecializationRequestDto specializationRequestDto);

    @Mapping(target = "specialists", source = "specialists")
    @Mapping(target = "services", source = "services")
    SpecializationResponseDto specializationToSpecializationResponseDto(Specialization specialization);

    SpecializationShortDto specializationToSpecializationShortDto(Specialization specialization);

    void updateSpecializationFromDto(SpecializationUpdateDto specializationUpdateDto, @MappingTarget Specialization specialization);
}
