package project.appointment.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.appointment.notification.dto.NotificationResponseDto;
import project.appointment.notification.service.NotificationsService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/notifications")
@PreAuthorize("hasRole('ADMINISTRATOR')")
@RequiredArgsConstructor
public class AdminNotificationController {

    private final NotificationsService notificationsService;

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponseDto> getNotificationById(@PathVariable Long id) {
        NotificationResponseDto notification = notificationsService.getNotificationById(id);
        return ResponseEntity.ok(notification);
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponseDto>> getAllNotifications() {
        List<NotificationResponseDto> notifications = notificationsService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationsService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}
