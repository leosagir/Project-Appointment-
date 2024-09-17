package project.appointment.notification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationRequestDto {
    @NotNull
    private Long clientId;

    @NotNull
    private Long appointmentId;

}