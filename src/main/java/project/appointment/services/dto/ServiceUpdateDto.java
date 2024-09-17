package project.appointment.services.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ServiceUpdateDto {
    @Size(min = 3, max = 100)
    private String title;

    @Size(min = 3, max = 1000)
    private String description;

    @Min(15)
    @Max(60)
    private Integer duration;

    private String price;

    private Long specializationId;
}
