package project.appointment.appointment;

import org.mapstruct.*;
import project.appointment.appointment.dto.*;
import project.appointment.appointment.entity.Appointment;
import project.appointment.client.entity.Client;
import project.appointment.services.entity.Service;
import project.appointment.specialist.entity.Specialist;
import project.appointment.services.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class AppointmentMapper {

    @Autowired
    protected ServiceRepository serviceRepository;

    @Mapping(target = "specialistId", source = "specialist.id")
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "serviceId", source = "service.id")
    public abstract AppointmentDto toDto(Appointment appointment);

    @Mapping(target = "specialist", source = "specialistId", qualifiedByName = "specialistIdToSpecialist")
    @Mapping(target = "service", source = "serviceId", qualifiedByName = "serviceIdToService")
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appointmentStatus", constant = "AVAILABLE")
    public abstract Appointment toEntity(AppointmentCreateDto dto);

    @Mapping(target = "specialist", source = "specialistId", qualifiedByName = "specialistIdToSpecialist")
    @Mapping(target = "client", source = "clientId", qualifiedByName = "clientIdToClient")
    @Mapping(target = "service", source = "serviceId", qualifiedByName = "serviceIdToService")
    public abstract void updateAppointmentFromDto(AppointmentUpdateDto dto, @MappingTarget Appointment appointment);

    @Mapping(target = "specialistName", source = "specialist.firstName")
    @Mapping(target = "clientName", source = "client.firstName")
    @Mapping(target = "serviceName", source = "service.title")
    public abstract AppointmentResponseDto toResponseDto(Appointment appointment);

    @Named("specialistIdToSpecialist")
    protected Specialist specialistIdToSpecialist(Long id) {
        if (id == null) {
            return null;
        }
        Specialist specialist = new Specialist();
        specialist.setId(id);
        return specialist;
    }

    @Named("clientIdToClient")
    protected Client clientIdToClient(Long id) {
        if (id == null) {
            return null;
        }
        Client client = new Client();
        client.setId(id);
        return client;
    }

    @Named("serviceIdToService")
    protected Service serviceIdToService(Long id) {
        if (id == null) {
            return null;
        }
        return serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + id));
    }
}



//import org.mapstruct.*;
//import project.appointment.appointment.dto.*;
//import project.appointment.appointment.entity.Appointment;
//import project.appointment.client.entity.Client;
//import project.appointment.services.entity.Service;
//import project.appointment.specialist.entity.Specialist;
//
//@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
//public interface AppointmentMapper {
//
//    @Mapping(target = "specialistId", source = "specialist.id")
//    @Mapping(target = "clientId", source = "client.id")
//    @Mapping(target = "serviceId", source = "service.id")
//    AppointmentDto toDto(Appointment appointment);
//
//    @Mapping(target = "specialist", source = "specialistId")
//    @Mapping(target = "service", source = "serviceId")
//    @Mapping(target = "client", ignore = true)
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "appointmentStatus", constant = "AVAILABLE")
//    Appointment toEntity(AppointmentCreateDto dto);
//
//    @Mapping(target = "specialist", source = "specialistId")
//    @Mapping(target = "client", source = "clientId")
//    @Mapping(target = "service", source = "serviceId")
//    void updateAppointmentFromDto(AppointmentUpdateDto dto, @MappingTarget Appointment appointment);
//
//    @Mapping(target = "specialistName", source = "specialist.firstName")
//    @Mapping(target = "clientName", source = "client.firstName")
//    @Mapping(target = "serviceName", source = "service.title")
//    AppointmentResponseDto toResponseDto(Appointment appointment);
//
//    @Named("specialistIdToSpecialist")
//    default Specialist specialistIdToSpecialist(Long id) {
//        if (id == null) {
//            return null;
//        }
//        Specialist specialist = new Specialist();
//        specialist.setId(id);
//        return specialist;
//    }
//
//    @Named("clientIdToClient")
//    default Client clientIdToClient(Long id) {
//        if (id == null) {
//            return null;
//        }
//        Client client = new Client();
//        client.setId(id);
//        return client;
//    }
//
//    @Named("serviceIdToService")
//    default Service serviceIdToService(Long id) {
//        if (id == null) {
//            return null;
//        }
//        Service service = new Service();
//        service.setId(id);
//        return service;
//    }
//}
