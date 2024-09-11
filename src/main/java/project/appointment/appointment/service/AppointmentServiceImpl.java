package project.appointment.appointment.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import project.appointment.appointment.entity.Appointment;
import project.appointment.appointment.repository.AppointmentRepository;
import project.appointment.client.entity.Client;
import project.appointment.client.repository.ClientRepository;
import project.appointment.specialist.entity.Specialist;
import project.appointment.specialist.repository.SpecialistRepository;
import project.appointment.timeSlot.entity.TimeSlot;
import project.appointment.timeSlot.repository.TimeSlotRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

    private final AppointmentRepository appointmentRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final ClientRepository clientRepository;
    private final SpecialistRepository specialistRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, TimeSlotRepository timeSlotRepository, ClientRepository clientRepository, SpecialistRepository specialistRepository) {
        this.appointmentRepository = appointmentRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.clientRepository = clientRepository;
        this.specialistRepository = specialistRepository;
    }

    @Override
    public Appointment addAppointment(Long clientId, Long specialistId, LocalDateTime startTime, Appointment.AppointmentType appointmentType) {
        logger.info("Attempting to add appointment for client {} with specialist {} at {}", clientId, specialistId, startTime);

        if (startTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Appointment start time cannot be in the past");
        }

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        Specialist specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() -> new EntityNotFoundException("Specialist not found"));

        LocalDateTime endTime = startTime.plusMinutes(appointmentType.getDurationMinutes());

        List<TimeSlot> requiredSlots = timeSlotRepository.findBySpecialistAndStartTimeBetween(specialist, startTime, endTime);

        if (requiredSlots.size() != appointmentType.getDurationMinutes() / 30 || requiredSlots.stream().anyMatch(slot -> slot.getTimeSlotStatus() == TimeSlot.TimeSlotStatus.BOOKED)) {
            throw new IllegalStateException("Required time slots are not available");
        }

        Appointment appointment = new Appointment();
        appointment.setClient(client);
        appointment.setSpecialist(specialist);
        appointment.setStartTime(startTime);
        appointment.setEndTime(endTime);
        appointment.setAppointmentStatus(Appointment.AppointmentStatus.SCHEDULED);
        appointment.setAppointmentType(appointmentType);

        for (TimeSlot slot : requiredSlots) {
            slot.setTimeSlotStatus(TimeSlot.TimeSlotStatus.BOOKED);
            slot.setAppointment(appointment);
            appointment.getTimeSlots().add(slot);
        }

        Appointment savedAppointment = appointmentRepository.save(appointment);
        logger.info("Appointment added successfully: {}", savedAppointment);
        return savedAppointment;
    }

    @Override
    public void cancelAppointment(Long appointmentId) {
        logger.info("Attempting to cancel appointment {}", appointmentId);

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        if (appointment.getAppointmentStatus() == Appointment.AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("Appointment is already cancelled");
        }

        appointment.setAppointmentStatus(Appointment.AppointmentStatus.CANCELLED);
        for (TimeSlot slot : appointment.getTimeSlots()) {
            slot.setTimeSlotStatus(TimeSlot.TimeSlotStatus.AVAILABLE);
            slot.setAppointment(null);
        }
        appointmentRepository.save(appointment);
        logger.info("Appointment {} cancelled successfully", appointmentId);
    }

    @Override
    public List<Appointment> getClientAppointments(Long clientId) {
        logger.info("Fetching appointments for client {}", clientId);

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        return appointmentRepository.findByClientAndAppointmentStatusNot(client, Appointment.AppointmentStatus.CANCELLED);
    }

    @Override
    public List<Appointment> getSpecialistAppointments(Long specialistId) {
        logger.info("Fetching appointments for specialist {}", specialistId);

        Specialist specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() -> new EntityNotFoundException("Specialist not found"));
        return appointmentRepository.findBySpecialistAndAppointmentStatusNot(specialist, Appointment.AppointmentStatus.CANCELLED);
    }
}


//import jakarta.persistence.EntityNotFoundException;
//import jakarta.transaction.Transactional;
//import org.springframework.stereotype.Service;
//import project.appointment.appointment.entity.Appointment;
//import project.appointment.appointment.repository.AppointmentRepository;
//import project.appointment.client.entity.Client;
//import project.appointment.client.repository.ClientRepository;
//import project.appointment.specialist.entity.Specialist;
//import project.appointment.specialist.repository.SpecialistRepository;
//import project.appointment.timeSlot.entity.TimeSlot;
//import project.appointment.timeSlot.repository.TimeSlotRepository;
//
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Service
//@Transactional
//public class AppointmentServiceImpl implements AppointmentService {
//
//    private final AppointmentRepository appointmentRepository;
//    private final TimeSlotRepository timeSlotRepository;
//    private final ClientRepository clientRepository;
//    private final SpecialistRepository specialistRepository;
//
//    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, TimeSlotRepository timeSlotRepository, ClientRepository clientRepository, SpecialistRepository specialistRepository) {
//        this.appointmentRepository = appointmentRepository;
//        this.timeSlotRepository = timeSlotRepository;
//        this.clientRepository = clientRepository;
//        this.specialistRepository = specialistRepository;
//    }
//
//    @Override
//    public Appointment addAppointment(Long clientId, Long specialistId, LocalDateTime startTime, Appointment.AppointmentType appointmentType) {
//        Client client = clientRepository.findById(clientId)
//                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
//        Specialist specialist = specialistRepository.findById(specialistId)
//                .orElseThrow(() -> new EntityNotFoundException("Specialist not found"));
//
//        LocalDateTime endTime = startTime.plusMinutes(appointmentType.getDurationMinutes());
//
//        List<TimeSlot> requiredSlots = timeSlotRepository.findBySpecialistAndStartTimeBetween(specialist, startTime, endTime);
//
//        if (requiredSlots.size() != appointmentType.getDurationMinutes() / 30 || requiredSlots.stream().anyMatch(slot -> slot.getTimeSlotStatus() == TimeSlot.TimeSlotStatus.BOOKED)) {
//            throw new IllegalStateException("Required time slots are not available");
//        }
//        Appointment appointment = new Appointment();
//        appointment.setClient(client);
//        appointment.setSpecialist(specialist);
//        appointment.setStartTime(startTime);
//        appointment.setEndTime(endTime);
//        appointment.setAppointmentStatus(Appointment.AppointmentStatus.SCHEDULED);
//        appointment.setAppointmentType(appointmentType);
//
//        for (TimeSlot slot : requiredSlots) {
//            slot.setTimeSlotStatus(TimeSlot.TimeSlotStatus.BOOKED);
//            slot.setAppointment(appointment);
//            appointment.getTimeSlots().add(slot);
//        }
//        return appointmentRepository.save(appointment);
//
//
//    }
//
//    @Override
//    public void cancelAppointment(Long appointmentId) {
//        Appointment appointment = appointmentRepository.findById(appointmentId)
//                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));
//        appointment.setAppointmentStatus(Appointment.AppointmentStatus.CANCELLED);
//        for (TimeSlot slot : appointment.getTimeSlots()) {
//            slot.setTimeSlotStatus(TimeSlot.TimeSlotStatus.AVAILABLE);
//            slot.setAppointment(null);
//        }
//        appointmentRepository.save(appointment);
//    }
//
//    @Override
//    public List<Appointment> getClientAppointments(Long clientId) {
//        Client client = clientRepository.findById(clientId)
//                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
//        appointmentRepository.findByClientAndStatusNot(client, Appointment.AppointmentStatus.CANCELLED);
//    }
//
//    @Override
//    public List<Appointment> getSpecialistAppointments(Long specialistId) {
//        Specialist specialist=specialistRepository.findById(specialistId)
//                .orElseThrow(()->new EntityNotFoundException("Specialist not found"));
//        appointmentRepository.findBySpecialistAndStatusNot(specialist,Appointment.AppointmentStatus.CANCELLED);
//    }
//}
