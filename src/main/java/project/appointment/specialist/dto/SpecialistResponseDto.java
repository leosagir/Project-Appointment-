package project.appointment.specialist.dto;

import lombok.Data;
import project.appointment.ENUM.Status;
import project.appointment.services.dto.ServiceShortDto;
import project.appointment.specialization.dto.SpecializationShortDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class SpecialistResponseDto {
        private Long id;
        private String email;
        private String firstName;
        private String lastName;
        private LocalDate dateOfBirth;
        private Set<SpecializationShortDto> specializations;
        private Set<ServiceShortDto> services;
        private String description;
        private String address;
        private String phone;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Status status;
}
