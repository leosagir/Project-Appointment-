package project.appointment.services.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import project.appointment.specialist.dto.SpecialistResponseDto;
import project.appointment.specialist.entity.Specialist;
import project.appointment.specialization.dto.SpecializationResponseDto;
import project.appointment.specialization.entity.Specialization;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class SServiceResponseDto {

    private Long id;
    private String title;
    private String description;
    private Integer duration;
    private String price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<SpecialistResponseDto> specialists;
    private SpecializationResponseDto specialization;
}
