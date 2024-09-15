package project.appointment.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.appointment.notification.entity.Notification;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository <Notification, Long> {
    List<Notification> findByClientId(Long clientId);
}
