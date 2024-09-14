package project.appointment.specialist.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import project.appointment.services.dto.SServiceResponseDto;
import project.appointment.services.entity.SService;
import project.appointment.specialization.dto.SpecializationResponseDto;
import project.appointment.specialization.entity.Specialization;

import java.time.LocalDate;
import java.util.Set;

@Data
public class SpecialistUpdateDto {

    @Size(min = 2,max = 20, message = "First name must be between 2 and 20 characters")
    private String firstName;


    @Size(min = 2,max = 20, message = "Last name must be between 2 and 20 characters")
    private String lastName;


    private Set<SpecializationResponseDto> specializations;


    private Set<SServiceResponseDto> services;


    @Size(min=15,max=510)
    private String description;


    @Past
    private LocalDate dateOfBirth;


    @Size(max=255)
    private String address;

    @Pattern(regexp = "^\\+?[0-9]{10,14}$",
            message = "Phone number must be 10-14 digits long and may start with a '+' symbol")
    private String phone;
}

