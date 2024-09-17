package project.appointment.services.dto;

import lombok.Data;
import project.appointment.specialist.dto.SpecialistShortDto;
import project.appointment.specialization.dto.SpecializationShortDto;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class ServiceResponseDto {
    private Long id;
    private String title;
    private String description;
    private Integer duration;
    private String price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private SpecializationShortDto specialization;
    private Set<SpecialistShortDto> specialists;
}
