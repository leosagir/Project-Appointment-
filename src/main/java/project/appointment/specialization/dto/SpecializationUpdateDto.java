package project.appointment.specialization.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SpecializationUpdateDto {
    @NotBlank
    @Size(min = 2, max = 100)
    private String title;
}
