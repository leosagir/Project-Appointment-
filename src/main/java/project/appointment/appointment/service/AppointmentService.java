package project.appointment.appointment.service;

import org.springframework.transaction.annotation.Transactional;
import project.appointment.appointment.dto.*;

import java.util.List;

public interface AppointmentService {

    @Transactional(readOnly = true)
    AppointmentResponseDto getAppointmentById(Long id);

    @Transactional(readOnly = true)
    List<AppointmentResponseDto> getAllAppointments();

    @Transactional
    void deleteAppointment(Long id);

    @Transactional
    AppointmentResponseDto createFreeAppointment(AppointmentCreateDto appointmentCreateDto);

    @Transactional
    AppointmentResponseDto bookAppointment(Long id, AppointmentBookDto appointmentBookDto);

    @Transactional
    AppointmentResponseDto cancelBooking(Long id);

    @Transactional(readOnly = true)
    List<AppointmentResponseDto> getFreeAppointments(Long specialistId);

    @Transactional(readOnly = true)
    List<AppointmentResponseDto> getBookedAppointments(Long specialistId);

    @Transactional(readOnly = true)
    List<AppointmentResponseDto> getSpecialistAppointments(Long specialistId);

    @Transactional(readOnly = true)
    List<AppointmentResponseDto> getClientAppointments(Long clientId);

    @Transactional
    AppointmentResponseDto cancelClientAppointment(Long id);

    @Transactional(readOnly = true)
    List<AppointmentResponseDto> getClientPastAppointmentsWithoutReview(Long clientId);

}