package project.appointment.specialization.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.util.Set;
@Data
public class SpecializationRequestDto {

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    private String title;

    private Set<Long> specialistIds;
}
