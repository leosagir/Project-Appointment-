package project.appointment.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.appointment.notification.dto.NotificationResponseDto;
import project.appointment.notification.service.NotificationsService;
import project.appointment.client.entity.Client;
import project.appointment.exception.ResourceNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/api/clients/notifications")
@PreAuthorize("hasRole('CLIENT')")
@RequiredArgsConstructor
public class ClientNotificationController {

    private final NotificationsService notificationsService;

    @GetMapping
    public ResponseEntity<List<NotificationResponseDto>> getClientNotifications(@AuthenticationPrincipal Client client) {
        List<NotificationResponseDto> notifications = notificationsService.getNotificationsByClientId(client.getId());
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponseDto> getClientNotificationById(@AuthenticationPrincipal Client client, @PathVariable Long id) {
        NotificationResponseDto notification = notificationsService.getNotificationById(id);
        if (!notification.getClientId().equals(client.getId())) {
            throw new ResourceNotFoundException("Notification not found for this client");
        }
        return ResponseEntity.ok(notification);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientNotification(@AuthenticationPrincipal Client client, @PathVariable Long id) {
        NotificationResponseDto notification = notificationsService.getNotificationById(id);
        if (!notification.getClientId().equals(client.getId())) {
            throw new ResourceNotFoundException("Notification not found for this client");
        }
        notificationsService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}
