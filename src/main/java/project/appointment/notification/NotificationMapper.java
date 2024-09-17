package project.appointment.notification;

import org.mapstruct.*;
import project.appointment.notification.dto.NotificationRequestDto;
import project.appointment.notification.dto.NotificationResponseDto;
import project.appointment.notification.entity.Notification;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client.id", source = "clientId")
    @Mapping(target = "appointment.id", source = "appointmentId")
    @Mapping(target = "sentAt", ignore = true)
    @Mapping(target = "status", constant = "SENT")
    Notification notificationRequestDtoToNotification(NotificationRequestDto notificationRequestDto);

    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "clientFullName", expression = "java(notification.getClient().getFirstName() + \" \" + notification.getClient().getLastName())")
    @Mapping(target = "appointmentId", source = "appointment.id")
    @Mapping(target = "appointmentDate", source = "appointment.startTime")
    NotificationResponseDto notificationToNotificationResponseDto(Notification notification);

    @InheritInverseConfiguration(name = "notificationToNotificationResponseDto")
    Notification notificationResponseDtoToNotification(NotificationResponseDto notificationResponseDto);
}
