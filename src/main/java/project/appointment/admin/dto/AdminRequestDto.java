package project.appointment.admin.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
@Data
public class AdminRequestDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 20, message = "Password must be at least 6 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$",
            message = "Password must contain at least one digit, one lowercase, one uppercase, one special character, and no whitespace")
    private String password;

    @NotBlank(message = "First name is required")
    @Size(min = 2,max = 20, message = "First name must be between 2 and 20 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2,max = 20, message = "Last name must be between 2 and 20 characters")
    private String lastName;

    @NotBlank(message = "Date of birth is required")
    @Past
    private LocalDate dateOfBirth;

    @NotBlank(message = "Address is required")
    @Size(max=255)
    private String address;

    @Pattern(regexp= "^\\+?[0-9]{7,14}$", message = "Phone number must be between 7 and 14 digits, optionally starting with +")
    private String phone;
}
