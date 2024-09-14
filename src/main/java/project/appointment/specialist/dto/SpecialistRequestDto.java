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
public class SpecialistRequestDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 20, message = "Password must be at least 6 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?])(?=\\S+$).{8,}$",
            message = "Password must contain at least one digit, one lowercase, one uppercase, one special character, and no whitespace")
    private String password;

    @NotBlank(message = "First name is required")
    @Size(min = 2,max = 20, message = "First name must be between 2 and 20 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2,max = 20, message = "Last name must be between 2 and 20 characters")
    private String lastName;

    @NotEmpty(message = "At least one specialization is required")
    private Set<SpecializationResponseDto> specializations;

    @NotEmpty(message = "At least one services is required")
    private Set<SServiceResponseDto> services;

    @NotBlank(message = "Description is required")
    @Size(min=15,max=510)
    private String description;

    @NotNull(message = "Date of birth is required")
    @Past
    private LocalDate dateOfBirth;

    @NotBlank(message = "Address is required")
    @Size(max=255)
    private String address;

    @Pattern(regexp = "^\\+?[0-9]{10,14}$",
            message = "Phone number must be 10-14 digits long and may start with a '+' symbol")
    private String phone;
}
