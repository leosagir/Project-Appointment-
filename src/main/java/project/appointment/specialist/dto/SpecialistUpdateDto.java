package project.appointment.specialist.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class SpecialistUpdateDto {
    @Size(min = 2, max = 20)
    private String firstName;

    @Size(min = 2, max = 20)
    private String lastName;

    @Past
    private LocalDate dateOfBirth;

    private Set<Long> specializationIds;

    private Set<Long> serviceIds;

    @Size(min = 3, max = 510)
    private String description;

    @Size(max = 255)
    private String address;

    @Pattern(regexp = "^\\+?[0-9]{10,14}$")
    private String phone;
}


