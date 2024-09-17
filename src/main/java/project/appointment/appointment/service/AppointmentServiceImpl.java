package project.appointment.appointment.service;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.appointment.appointment.dto.*;
import project.appointment.appointment.entity.Appointment;
import project.appointment.appointment.AppointmentMapper;
import project.appointment.appointment.repository.AppointmentRepository;
import project.appointment.client.entity.Client;
import project.appointment.specialist.entity.Specialist;
import project.appointment.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService{

    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final Validator validator;

    @Transactional
    @Override
    public AppointmentDto createAppointment(AppointmentCreateDto appointmentCreateDto) {
        logger.info("Creating new appointment");
        Appointment appointment = appointmentMapper.toEntity(appointmentCreateDto);
        validateAppointment(appointment);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toDto(savedAppointment);
    }

    @Transactional(readOnly = true)
    @Override
    public AppointmentDto getAppointmentById(Long id) {
        logger.info("Fetching appointment with id: {}", id);
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
        return appointmentMapper.toDto(appointment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AppointmentDto> getAllAppointments() {
        logger.info("Fetching all appointments");
        return appointmentRepository.findAll().stream()
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public AppointmentDto updateAppointment(Long id, AppointmentUpdateDto appointmentUpdateDto) {
        logger.info("Updating appointment with id: {}", id);
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
        appointmentMapper.updateAppointmentFromDto(appointmentUpdateDto, appointment);
        validateAppointment(appointment);
        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toDto(updatedAppointment);
    }

    @Transactional
    @Override
    public void deleteAppointment(Long id) {
        logger.info("Deleting appointment with id: {}", id);
        appointmentRepository.deleteById(id);
    }

    @Transactional
    @Override
    public AppointmentDto createFreeAppointment(AppointmentCreateDto appointmentCreateDto) {
        logger.info("Creating free appointment");
        Appointment appointment = appointmentMapper.toEntity(appointmentCreateDto);
        appointment.setAppointmentStatus(Appointment.AppointmentStatus.AVAILABLE);
        validateAppointment(appointment);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toDto(savedAppointment);
    }

    @Transactional
    @Override
    public AppointmentDto bookAppointment(Long id, AppointmentBookDto appointmentBookDto) {
        logger.info("Booking appointment with id: {} for client: {}", id, appointmentBookDto.getClientId());
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
        if (appointment.getAppointmentStatus() != Appointment.AppointmentStatus.AVAILABLE) {
            throw new IllegalStateException("Appointment is not available for booking");
        }
        Client client = new Client();
        client.setId(appointmentBookDto.getClientId());
        appointment.setClient(client);
        appointment.setAppointmentStatus(Appointment.AppointmentStatus.BOOKED);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toDto(savedAppointment);
    }

    @Transactional
    @Override
    public AppointmentDto cancelBooking(Long id) {
        logger.info("Cancelling booking for appointment with id: {}", id);
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
        if (appointment.getAppointmentStatus() != Appointment.AppointmentStatus.BOOKED) {
            throw new IllegalStateException("Appointment is not booked");
        }
        appointment.setClient(null);
        appointment.setAppointmentStatus(Appointment.AppointmentStatus.AVAILABLE);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toDto(savedAppointment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AppointmentDto> getFreeAppointments(Long specialistId) {
        logger.info("Fetching free appointments for specialist with id: {}", specialistId);
        Specialist specialist = new Specialist();
        specialist.setId(specialistId);
        return appointmentRepository.findBySpecialistAndAppointmentStatus(specialist, Appointment.AppointmentStatus.AVAILABLE)
                .stream()
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<AppointmentDto> getBookedAppointments(Long specialistId) {
        logger.info("Fetching booked appointments for specialist with id: {}", specialistId);
        Specialist specialist = new Specialist();
        specialist.setId(specialistId);
        return appointmentRepository.findBySpecialistAndAppointmentStatus(specialist, Appointment.AppointmentStatus.BOOKED)
                .stream()
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
    }

    private void validateAppointment(Appointment appointment) {
        var violations = validator.validate(appointment);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Invalid appointment: " + violations);
        }
    }
}