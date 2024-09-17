package project.appointment.client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientShortRequestDto {
    @NotBlank
    private Long clientId;

    @NotBlank(message = "First name is required")
    @Size(min = 2,max = 20, message = "First name must be between 2 and 20 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2,max = 20, message = "Last name must be between 2 and 20 characters")
    private String lastName;

}
