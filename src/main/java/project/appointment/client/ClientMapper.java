package project.appointment.client;

import org.mapstruct.*;
import project.appointment.client.dto.*;
import project.appointment.client.entity.Client;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "role", constant = "CLIENT")
    Client clientRequestDtoToClient(ClientRequestDto clientRequestDto);

    ClientResponseDto clientToClientResponseDto(Client client);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "role", ignore = true)
    void updateClientFromDto(ClientUpdateDto clientUpdateDto, @MappingTarget Client client);

    @InheritInverseConfiguration(name = "clientToClientResponseDto")
    Client clientResponseDtoToClient(ClientResponseDto clientResponseDto);

    @InheritInverseConfiguration(name = "clientRequestDtoToClient")
    ClientRequestDto clientToClientRequestDto(Client client);

    @InheritConfiguration(name = "updateClientFromDto")
    @Mapping(target = "id", ignore = true)
    Client clientUpdateDtoToClient(ClientUpdateDto clientUpdateDto);

    @InheritInverseConfiguration(name = "clientUpdateDtoToClient")
    ClientUpdateDto clientToClientUpdateDto(Client client);

    @Mapping(target = "clientId", source = "id")
    ClientShortRequestDto clientToClientShortRequestDto(Client client);

    @Mapping(target = "id", source = "clientId")
    Client clientShortRequestDtoToClient(ClientShortRequestDto clientShortRequestDto);

    @Mapping(target = "phone", source = "phone")
    ClientShortResponseDto clientToClientShortResponseDto(Client client);

    @InheritInverseConfiguration(name = "clientToClientShortResponseDto")
    Client clientShortResponseDtoToClient(ClientShortResponseDto clientShortResponseDto);

    @AfterMapping
    default void linkAppointments(@MappingTarget Client client) {
        if (client.getAppointments() != null) {
            client.getAppointments().forEach(appointment -> appointment.setClient(client));
        }
    }

    @AfterMapping
    default void linkReviews(@MappingTarget Client client) {
        if (client.getReviews() != null) {
            client.getReviews().forEach(review -> review.setClient(client));
        }
    }

    @AfterMapping
    default void linkNotifications(@MappingTarget Client client) {
        if (client.getNotifications() != null) {
            client.getNotifications().forEach(notification -> notification.setClient(client));
        }
    }
}
