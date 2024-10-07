package project.appointment.appointment.dto;

import lombok.Data;
import project.appointment.appointment.entity.Appointment;

import java.time.LocalDateTime;

@Data
public class AppointmentResponseDto {
    private Long id;
    private Long specialistId;
    private String specialistName;
    private Long clientId;
    private String clientName;
    private Long serviceId;
    private String serviceName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Appointment.AppointmentStatus appointmentStatus;
}
