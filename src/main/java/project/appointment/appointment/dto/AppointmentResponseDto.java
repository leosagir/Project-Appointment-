package project.appointment.appointment.dto;

import lombok.Data;
import project.appointment.appointment.entity.Appointment;

import java.time.LocalDateTime;

@Data
public class AppointmentResponseDto {
    private Long id;
    private String specialistName;
    private String clientName;
    private String serviceName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Appointment.AppointmentStatus appointmentStatus;
}
