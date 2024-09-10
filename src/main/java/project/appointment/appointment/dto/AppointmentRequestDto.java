package project.appointment.appointment.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequestDto {

        @NotNull(message = "Client ID is required")
        private Long clientId;

        @NotNull(message = "Specialist ID is required")
        private Long specialistId;

        @NotNull(message = "Appointment date is required")
        @Future(message = "Appointment date must be in the future")
        private LocalDateTime appointmentDate;

}
