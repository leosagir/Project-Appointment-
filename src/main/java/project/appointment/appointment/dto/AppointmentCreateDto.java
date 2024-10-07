package project.appointment.appointment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentCreateDto {
    private Long specialistId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
