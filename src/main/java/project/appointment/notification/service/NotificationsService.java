package project.appointment.notification.service;

import org.springframework.stereotype.Service;
import project.appointment.appointment.entity.Appointment;
import project.appointment.notification.dto.NotificationRequestDto;
import project.appointment.notification.dto.NotificationResponseDto;

import java.util.List;


public interface NotificationsService {
    NotificationResponseDto createBookingNotification(Appointment appointment);
    NotificationResponseDto createCancellationNotification(Appointment appointment);
    NotificationResponseDto createReschedulingNotification(Appointment appointment);
    NotificationResponseDto getNotificationById(Long id);
    List<NotificationResponseDto> getAllNotifications();
    List<NotificationResponseDto> getNotificationsByClientId(Long clientId);
    void deleteNotification(Long id);
}
