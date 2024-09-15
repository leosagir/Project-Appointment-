package project.appointment.services.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import project.appointment.specialist.dto.SpecialistResponseDto;
import project.appointment.specialization.dto.SpecializationResponseDto;

import java.util.Set;

@Data
public class SServiceRequestDto {

    @NotBlank
    @Size(min = 3, max = 100)
    private String title;

    @NotBlank
    @Size(min = 3, max = 1000)
    private String description;

    @NotNull
    @Min(15)
    @Max(60)
    private Integer duration;

    @NotBlank(message = "Price is required")
    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$", message = "Price must be a valid number with up to 2 decimal places")
    private String price;

    private Set<Long> specialistId;


    private Long specializationId;
}
