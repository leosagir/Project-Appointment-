package project.appointment.services.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Set;

@Data
public class ServiceRequestDto {

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

    @NotBlank
    private String price;

    @NotNull
    private Long specializationId;

}
