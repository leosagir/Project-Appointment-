package project.appointment.specialization.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.util.Set;
@Data
public class SpecializationRequestDto {
    @NotBlank
    @Size(min = 2, max = 100)
    private String title;
}
