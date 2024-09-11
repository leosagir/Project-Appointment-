package project.appointment.appointment.service;

import project.appointment.appointment.entity.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
    Appointment addAppointment(Long clientId, Long specialistId, LocalDateTime startTime, Appointment.AppointmentType appointmentType);
    void cancelAppointment(Long appointmentId);
    List<Appointment> getClientAppointments(Long clientId);
    List<Appointment> getSpecialistAppointments(Long specialistId);
}
