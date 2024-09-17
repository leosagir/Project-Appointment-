package project.appointment.notification.service;

import jakarta.transaction.Transactional;
import project.appointment.appointment.entity.Appointment;
import project.appointment.notification.dto.NotificationRequestDto;
import project.appointment.notification.dto.NotificationResponseDto;

import java.util.List;


public interface NotificationService {
    @Transactional
    NotificationResponseDto createNotification(NotificationRequestDto notificationRequestDto);

    NotificationResponseDto getNotificationById(Long id);
    List<NotificationResponseDto> getAllNotifications();
    List<NotificationResponseDto> getNotificationsByClientId(Long clientId);
    void deleteNotification(Long id);
}
