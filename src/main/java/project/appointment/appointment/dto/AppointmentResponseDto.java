package project.appointment.appointment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "dd MMMM yyyy HH:mm", timezone = "UTC")
    private LocalDateTime appointmentDate;
    private Appointment.AppointmentStatus appointmentStatus;
    @JsonFormat(pattern = "dd MMMM yyyy HH:mm", timezone = "UTC")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd MMMM yyyy HH:mm", timezone = "UTC")
    private LocalDateTime updatedAt;

}
