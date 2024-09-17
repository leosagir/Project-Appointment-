package project.appointment.review;

import org.mapstruct.*;
import project.appointment.review.dto.*;
import project.appointment.review.entity.Review;
import project.appointment.appointment.entity.Appointment;
import project.appointment.client.entity.Client;
import project.appointment.specialist.entity.Specialist;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {

    @Mapping(target = "specialistId", source = "specialist.id")
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "appointmentId", source = "appointment.id")
    ReviewDto toDto(Review review);

    @Mapping(target = "specialist", source = "specialistId", qualifiedByName = "specialistIdToSpecialist")
    @Mapping(target = "client", source = "clientId", qualifiedByName = "clientIdToClient")
    @Mapping(target = "appointment", source = "appointmentId", qualifiedByName = "appointmentIdToAppointment")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Review toEntity(ReviewCreateDto dto);

    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "comment", source = "comment")
    void updateReviewFromDto(ReviewUpdateDto dto, @MappingTarget Review review);

    @Mapping(target = "specialistName", source = "specialist.firstName")
    @Mapping(target = "clientName", source = "client.firstName")
    @Mapping(target = "appointmentDate", source = "appointment.startTime")
    ReviewResponseDto toResponseDto(Review review);

    @Named("specialistIdToSpecialist")
    default Specialist specialistIdToSpecialist(Long id) {
        if (id == null) {
            return null;
        }
        Specialist specialist = new Specialist();
        specialist.setId(id);
        return specialist;
    }

    @Named("clientIdToClient")
    default Client clientIdToClient(Long id) {
        if (id == null) {
            return null;
        }
        Client client = new Client();
        client.setId(id);
        return client;
    }

    @Named("appointmentIdToAppointment")
    default Appointment appointmentIdToAppointment(Long id) {
        if (id == null) {
            return null;
        }
        Appointment appointment = new Appointment();
        appointment.setId(id);
        return appointment;
    }
}



//package project.appointment.review;
//
//import org.mapstruct.*;
//import project.appointment.review.dto.*;
//import project.appointment.review.entity.Review;
//import project.appointment.appointment.entity.Appointment;
//import project.appointment.client.entity.Client;
//import project.appointment.specialist.entity.Specialist;
//
//@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
//public interface ReviewMapper {
//
//    @Mapping(target = "specialistId", source = "specialist.id")
//    @Mapping(target = "clientId", source = "client.id")
//    @Mapping(target = "appointmentId", source = "appointment.id")
//    ReviewDto toDto(Review review);
//
//    @Mapping(target = "specialist", source = "specialistId")
//    @Mapping(target = "client", source = "clientId")
//    @Mapping(target = "appointment", source = "appointmentId")
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    Review toEntity(ReviewCreateDto dto);
//
//    @Mapping(target = "rating", source = "rating")
//    @Mapping(target = "comment", source = "comment")
//    void updateReviewFromDto(ReviewUpdateDto dto, @MappingTarget Review review);
//
//    @Mapping(target = "specialistName", source = "specialist.firstName")
//    @Mapping(target = "clientName", source = "client.firstName")
//    @Mapping(target = "appointmentDate", source = "appointment.startTime")
//    ReviewResponseDto toResponseDto(Review review);
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
//    @Named("appointmentIdToAppointment")
//    default Appointment appointmentIdToAppointment(Long id) {
//        if (id == null) {
//            return null;
//        }
//        Appointment appointment = new Appointment();
//        appointment.setId(id);
//        return appointment;
//    }
//
//}
