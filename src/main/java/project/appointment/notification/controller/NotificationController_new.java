package project.appointment.notification.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.appointment.notification.dto.NotificationRequestDto;
import project.appointment.notification.dto.NotificationResponseDto;
import project.appointment.notification.service.NotificationService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationController_new {

    private final NotificationService notificationService;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<NotificationResponseDto> createNotification(@Valid @RequestBody NotificationRequestDto notificationRequestDto) {
        log.info("REST request to create Notification : {}", notificationRequestDto);
        NotificationResponseDto result = notificationService.createNotification(notificationRequestDto);
        return ResponseEntity.created(URI.create("/api/notifications/" + result.getId())).body(result);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or @securityService.isCurrentUserNotification(#id)")
    public ResponseEntity<NotificationResponseDto> getNotification(@PathVariable Long id) {
        log.info("REST request to get Notification : {}", id);
        NotificationResponseDto notificationDto = notificationService.getNotificationById(id);
        return ResponseEntity.ok(notificationDto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<NotificationResponseDto>> getAllNotifications() {
        log.info("REST request to get all Notifications");
        List<NotificationResponseDto> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/client/{clientId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or @securityService.isCurrentUser(#clientId)")
    public ResponseEntity<List<NotificationResponseDto>> getNotificationsByClientId(@PathVariable Long clientId) {
        log.info("REST request to get Notifications for Client : {}", clientId);
        List<NotificationResponseDto> notifications = notificationService.getNotificationsByClientId(clientId);
        return ResponseEntity.ok(notifications);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        log.info("REST request to delete Notification : {}", id);
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}
