package project.appointment.appointment.dto;

import lombok.Data;
import project.appointment.appointment.entity.Appointment;

import java.time.LocalDateTime;

@Data
public class AppointmentUpdateDto {
    private Long specialistId;
    private Long clientId;
    private Long serviceId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Appointment.AppointmentStatus appointmentStatus;
}