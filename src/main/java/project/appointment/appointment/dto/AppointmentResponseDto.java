package project.appointment.appointment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.appointment.appointment.entity.Appointment;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentResponseDto {
    private Long id;
    private Long clientId;
    private String clientFirstName;
    private String clientLastName;
    private Long specialistId;
    private String specialistFirstName;
    private String specialistLastName;
    private LocalDateTime appointmentDate;
    private Appointment.AppointmentStatus appointmentStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
