package project.appointment.specialization.dto;

import lombok.Data;
import project.appointment.specialist.dto.SpecialistResponseDto;

import java.util.Set;

@Data
public class SpecializationResponseDto {
    private Long id;
    private String title;
    private Set<Long> specialists;
}
