package project.appointment.appointment.dto;

import lombok.Data;
import project.appointment.appointment.entity.Appointment;

import java.time.LocalDateTime;

@Data
public class AppointmentDto {
    private Long id;
    private Long specialistId;
    private Long clientId;
    private Long serviceId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Appointment.AppointmentStatus appointmentStatus;
}