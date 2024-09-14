package project.appointment.appointment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import project.appointment.appointment.entity.Appointment;
import project.appointment.appointment.repository.AppointmentRepository;
import project.appointment.client.repository.ClientRepository;
import project.appointment.specialist.repository.SpecialistRepository;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;
    private final SpecialistRepository specialistRepository;

    @Override
    public void cancelAppointment(Long appointmentId) {

    }

    @Override
    public List<Appointment> getClientAppointments(Long clientId) {
        return List.of();
    }

    @Override
    public List<Appointment> getSpecialistAppointments(Long specialistId) {
        return List.of();
    }
}

