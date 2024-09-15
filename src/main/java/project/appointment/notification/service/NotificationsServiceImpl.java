package project.appointment.notification.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import project.appointment.appointment.entity.Appointment;
import project.appointment.appointment.repository.AppointmentRepository;
import project.appointment.client.repository.ClientRepository;
import project.appointment.exception.ResourceNotFoundException;
import project.appointment.notification.dto.NotificationRequestDto;
import project.appointment.notification.dto.NotificationResponseDto;
import project.appointment.notification.entity.Notification;
import project.appointment.notification.entity.NotificationStatus;
import project.appointment.notification.repository.NotificationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class NotificationsServiceImpl implements NotificationsService {

    private final Logger logger = LoggerFactory.getLogger(NotificationsServiceImpl.class);

    private final NotificationRepository notificationRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public NotificationResponseDto createBookingNotification(Appointment appointment) {
        return createNotification(appointment, "Your appointment has been booked successfully.");
    }

    @Override
    @Transactional
    public NotificationResponseDto createCancellationNotification(Appointment appointment) {
        return createNotification(appointment, "Your appointment has been cancelled.");
    }

    @Override
    @Transactional
    public NotificationResponseDto createReschedulingNotification(Appointment appointment) {
        return createNotification(appointment, "Your appointment has been rescheduled.");
    }

    private NotificationResponseDto createNotification(Appointment appointment, String message) {
        logger.info("Creating new notification for client ID: {}", appointment.getClient().getId());
        Notification notification = new Notification();
        notification.setClient(appointment.getClient());
        notification.setAppointment(appointment);
        notification.setMessage(message);

        try {
            // Здесь может быть логика отправки уведомления
            notification.setStatus(NotificationStatus.SENT);
        } catch (Exception e) {
            logger.error("Failed to send notification", e);
            notification.setStatus(NotificationStatus.FAILED);
        }

        Notification savedNotification = notificationRepository.save(notification);
        logger.info("Notification created with ID: {} and status: {}",
                savedNotification.getId(), savedNotification.getStatus());
        return modelMapper.map(savedNotification, NotificationResponseDto.class);
    }

    @Override
    @Transactional
    public NotificationResponseDto getNotificationById(Long id) {
        logger.info("Fetching notification with ID: {}", id);
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Notification with ID: {} not found", id);
                    return new ResourceNotFoundException("Notification with ID: " + id + " not found");
                });
        return modelMapper.map(notification, NotificationResponseDto.class);
    }

    @Override
    @Transactional
    public List<NotificationResponseDto> getAllNotifications() {
        logger.info("Fetching all notifications");
        List<Notification> notifications = notificationRepository.findAll();
        return notifications.stream()
                .map(notification -> modelMapper.map(notification, NotificationResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<NotificationResponseDto> getNotificationsByClientId(Long clientId) {
        logger.info("Fetching notifications for client ID: {}", clientId);
        List<Notification> notifications = notificationRepository.findByClientId(clientId);
        return notifications.stream()
                .map(notification -> modelMapper.map(notification, NotificationResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteNotification(Long id) {
        logger.info("Deleting notification with ID: {}", id);
        if (!notificationRepository.existsById(id)) {
            logger.error("Notification with ID: {} not found", id);
            throw new ResourceNotFoundException("Notification with ID: " + id + " not found");
        }
        notificationRepository.deleteById(id);
        logger.info("Notification deleted successfully with ID: {}", id);
    }
}
