package project.appointment.notification.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import project.appointment.appointment.entity.Appointment;
import project.appointment.appointment.repository.AppointmentRepository;
import project.appointment.client.entity.Client;
import project.appointment.client.repository.ClientRepository;
import project.appointment.exception.ResourceNotFoundException;
import project.appointment.notification.dto.NotificationRequestDto;
import project.appointment.notification.dto.NotificationResponseDto;
import project.appointment.notification.entity.Notification;
import project.appointment.notification.NotificationMapper;
import project.appointment.notification.repository.NotificationRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final NotificationRepository notificationRepository;
    private final ClientRepository clientRepository;
    private final AppointmentRepository appointmentRepository;
    private final NotificationMapper notificationMapper;
    private final Validator validator;

    @Transactional
    @Override
    public NotificationResponseDto createNotification(NotificationRequestDto notificationRequestDto) {
        logger.info("Creating new notification for client ID: {}", notificationRequestDto.getClientId());
        validateNotificationRequest(notificationRequestDto);

        Client client = clientRepository.findById(notificationRequestDto.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        Appointment appointment = appointmentRepository.findById(notificationRequestDto.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        Notification notification = notificationMapper.notificationRequestDtoToNotification(notificationRequestDto);
        notification.setClient(client);
        notification.setAppointment(appointment);

        try {
            Notification savedNotification = notificationRepository.save(notification);
            logger.info("Notification created successfully with ID: {}", savedNotification.getId());
            return notificationMapper.notificationToNotificationResponseDto(savedNotification);
        } catch (Exception e) {
            logger.error("Failed to create notification", e);
            throw new RuntimeException("Failed to create notification", e);
        }
    }

    @Override
    @Transactional
    public NotificationResponseDto getNotificationById(Long id) {
        logger.info("Fetching notification with ID: {}", id);
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Notification with ID: {} not found", id);
                    return new ResourceNotFoundException("Notification not found");
                });
        return notificationMapper.notificationToNotificationResponseDto(notification);
    }

    @Override
    @Transactional
    public List<NotificationResponseDto> getAllNotifications() {
        logger.info("Fetching all notifications");
        List<Notification> notifications = notificationRepository.findAll();
        return notifications.stream()
                .map(notificationMapper::notificationToNotificationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<NotificationResponseDto> getNotificationsByClientId(Long clientId) {
        logger.info("Fetching notifications for client ID: {}", clientId);
        List<Notification> notifications = notificationRepository.findByClientId(clientId);
        return notifications.stream()
                .map(notificationMapper::notificationToNotificationResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteNotification(Long id) {
        logger.info("Deleting notification with ID: {}", id);
        if (!notificationRepository.existsById(id)) {
            logger.error("Notification with ID: {} not found", id);
            throw new ResourceNotFoundException("Notification not found");
        }
        notificationRepository.deleteById(id);
        logger.info("Notification deleted successfully with ID: {}", id);
    }

    private void validateNotificationRequest(NotificationRequestDto notificationRequestDto) {
        Set<ConstraintViolation<NotificationRequestDto>> violations = validator.validate(notificationRequestDto);
        if (!violations.isEmpty()) {
            logger.error("Validation failed for NotificationRequestDto: {}", violations);
            throw new ConstraintViolationException(violations);
        }
    }
}
