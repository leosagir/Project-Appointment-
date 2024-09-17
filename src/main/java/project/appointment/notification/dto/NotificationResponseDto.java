package project.appointment.notification.dto;

import lombok.Data;
import project.appointment.notification.entity.NotificationStatus;

import java.time.LocalDateTime;

@Data
public class NotificationResponseDto {
    private Long id;
    private Long clientId;
    private String clientFullName;
    private Long appointmentId;
    private LocalDateTime appointmentDate;
    private LocalDateTime sentAt;
    private NotificationStatus status;

}
