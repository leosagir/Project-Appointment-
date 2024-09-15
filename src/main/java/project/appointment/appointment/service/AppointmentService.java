package project.appointment.appointment.service;

import project.appointment.appointment.dto.AppointmentRequestDto;
import project.appointment.appointment.dto.AppointmentResponseDto;


import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {

    AppointmentResponseDto createFreeAppointment(Long specialistId, AppointmentRequestDto appointmentRequestDto);
    AppointmentResponseDto updateFreeAppointment(Long appointmentId, AppointmentRequestDto appointmentRequestDto);
    void deleteFreeAppointment(Long specialistId, Long appointmentId);
    AppointmentResponseDto bookAppointment(Long clientId, Long appointmentId);
    AppointmentResponseDto cancelBookedAppointment(Long clientId, Long appointmentId);
    AppointmentResponseDto updateBookedAppointment(Long clientId, Long appointmentId, AppointmentRequestDto appointmentRequestDto);
    List<AppointmentResponseDto> getFreeAppointmentsForSpecialist(Long specialistId);
    List<AppointmentResponseDto> getBookedAppointmentsForSpecialist(Long specialistId);
    List<AppointmentResponseDto> getAppointmentsForClient(Long clientId);
}
