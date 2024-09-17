package project.appointment.specialization.dto;

import lombok.Data;
import project.appointment.services.dto.ServiceShortDto;
import project.appointment.specialist.dto.SpecialistResponseDto;
import project.appointment.specialist.dto.SpecialistShortDto;

import java.util.Set;

@Data
public class SpecializationResponseDto {
    private Long id;
    private String title;
    private Set<SpecialistShortDto> specialists;
    private Set<ServiceShortDto> services;
}
