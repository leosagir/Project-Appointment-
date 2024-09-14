package project.appointment.specialist.dto;

import lombok.Data;
import project.appointment.services.dto.SServiceResponseDto;
import project.appointment.specialization.dto.SpecializationResponseDto;

import java.time.LocalDate;
import java.util.Set;

@Data
public class SpecialistResponseDto {

        private Long id;
        private String firstName;
        private String lastName;
        private Set<SpecializationResponseDto> specializations;
        private Set<SServiceResponseDto> services;
        private String description;
        private LocalDate dateOfBirth;
        private String address;
        private String phone;

}
