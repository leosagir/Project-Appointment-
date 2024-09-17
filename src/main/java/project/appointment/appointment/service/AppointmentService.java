package project.appointment.appointment.service;


import org.springframework.transaction.annotation.Transactional;
import project.appointment.appointment.dto.AppointmentBookDto;
import project.appointment.appointment.dto.AppointmentCreateDto;
import project.appointment.appointment.dto.AppointmentDto;
import project.appointment.appointment.dto.AppointmentUpdateDto;

import java.util.List;

public interface AppointmentService {


    @Transactional
    AppointmentDto createAppointment(AppointmentCreateDto appointmentCreateDto);

    @Transactional(readOnly = true)
    AppointmentDto getAppointmentById(Long id);

    @Transactional(readOnly = true)
    List<AppointmentDto> getAllAppointments();

    @Transactional
    AppointmentDto updateAppointment(Long id, AppointmentUpdateDto appointmentUpdateDto);

    @Transactional
    void deleteAppointment(Long id);

    @Transactional
    AppointmentDto createFreeAppointment(AppointmentCreateDto appointmentCreateDto);

    @Transactional
    AppointmentDto bookAppointment(Long id, AppointmentBookDto appointmentBookDto);

    @Transactional
    AppointmentDto cancelBooking(Long id);

    @Transactional(readOnly = true)
    List<AppointmentDto> getFreeAppointments(Long specialistId);

    @Transactional(readOnly = true)
    List<AppointmentDto> getBookedAppointments(Long specialistId);
}
