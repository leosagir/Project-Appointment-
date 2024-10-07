package project.appointment.appointment.service;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.appointment.appointment.dto.*;
import project.appointment.appointment.entity.Appointment;
import project.appointment.appointment.AppointmentMapper;
import project.appointment.appointment.repository.AppointmentRepository;
import project.appointment.client.entity.Client;
import project.appointment.client.repository.ClientRepository;
import project.appointment.specialist.entity.Specialist;
import project.appointment.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);
    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;
    private final AppointmentMapper appointmentMapper;
    private final Validator validator;

    @Transactional
    @Override
    public AppointmentResponseDto createFreeAppointment(AppointmentCreateDto appointmentCreateDto) {
        Appointment appointment = appointmentMapper.toEntity(appointmentCreateDto);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toResponseDto(savedAppointment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AppointmentResponseDto> getAllAppointments() {
        logger.info("Fetching all appointments");
        return appointmentRepository.findAll().stream()
                .map(appointmentMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public AppointmentResponseDto bookAppointment(Long id, AppointmentBookDto appointmentBookDto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));

        if (appointment.getAppointmentStatus() != Appointment.AppointmentStatus.AVAILABLE) {
            throw new IllegalStateException("Appointment is not available for booking");
        }

        Client client = clientRepository.findById(appointmentBookDto.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + appointmentBookDto.getClientId()));

        appointment.setClient(client);
        appointment.setAppointmentStatus(Appointment.AppointmentStatus.BOOKED);
        Appointment savedAppointment = appointmentRepository.save(appointment);

        AppointmentResponseDto responseDto = appointmentMapper.toResponseDto(savedAppointment);
        appointmentMapper.updateResponseDtoFromBookDto(appointmentBookDto, responseDto);

        return responseDto;
    }

    @Transactional
    @Override
    public AppointmentResponseDto cancelClientAppointment(Long id) {
        logger.info("Cancelling client appointment with id: {}", id);
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));

        if (appointment.getAppointmentStatus() != Appointment.AppointmentStatus.BOOKED) {
            throw new IllegalStateException("Can only cancel booked appointments");
        }

        if (appointment.getStartTime().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Cannot cancel past appointments");
        }

        appointment.setClient(null);
        appointment.setAppointmentStatus(Appointment.AppointmentStatus.AVAILABLE);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toResponseDto(savedAppointment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponseDto> getClientPastAppointmentsWithoutReview(Long clientId) {
        logger.info("Fetching past appointments without review for client with id: {}", clientId);
        Client client = new Client();
        client.setId(clientId);
        LocalDateTime now = LocalDateTime.now();
        return appointmentRepository.findByClientAndEndTimeBeforeAndAppointmentStatus(client, now, Appointment.AppointmentStatus.COMPLETED)
                .stream()
                .filter(appointment -> appointment.getReview() == null)
                .map(appointmentMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Scheduled(fixedRate = 60000) // Выполняется каждую минуту
    public void updatePastAppointments() {
        LocalDateTime now = LocalDateTime.now();
        List<Appointment> pastAppointments = appointmentRepository.findByEndTimeBefore(now);

        for (Appointment appointment : pastAppointments) {
            if (appointment.getAppointmentStatus() == Appointment.AppointmentStatus.BOOKED) {
                appointment.setAppointmentStatus(Appointment.AppointmentStatus.COMPLETED);
                appointmentRepository.save(appointment);
                logger.info("Updated status to COMPLETED for appointment with id: {}", appointment.getId());
            }
        }
    }

    @Transactional
    @Override
    public AppointmentResponseDto cancelBooking(Long id) {
        logger.info("Cancelling booking for appointment with id: {}", id);
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
        if (appointment.getAppointmentStatus() != Appointment.AppointmentStatus.BOOKED) {
            throw new IllegalStateException("Appointment is not booked");
        }
        appointment.setClient(null);
        appointment.setAppointmentStatus(Appointment.AppointmentStatus.AVAILABLE);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentMapper.toResponseDto(savedAppointment);
    }

    @Transactional
    @Override
    public void deleteAppointment(Long id) {
        logger.info("Deleting or cancelling booking for appointment with id: {}", id);
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));

        if (appointment.getAppointmentStatus() == Appointment.AppointmentStatus.AVAILABLE) {
            appointmentRepository.delete(appointment);
        } else if (appointment.getAppointmentStatus() == Appointment.AppointmentStatus.BOOKED) {
            appointment.setClient(null);
            appointment.setAppointmentStatus(Appointment.AppointmentStatus.AVAILABLE);
            appointmentRepository.save(appointment);
        } else {
            throw new IllegalStateException("Cannot delete appointment with status: " + appointment.getAppointmentStatus());
        }
    }

    @Transactional(readOnly = true)
    @Override
    public AppointmentResponseDto getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
        return appointmentMapper.toResponseDto(appointment);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AppointmentResponseDto> getFreeAppointments(Long specialistId) {
        logger.info("Fetching free appointments for specialist with id: {}", specialistId);
        Specialist specialist = new Specialist();
        specialist.setId(specialistId);
        return appointmentRepository.findBySpecialistAndAppointmentStatus(specialist, Appointment.AppointmentStatus.AVAILABLE)
                .stream()
                .map(appointmentMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<AppointmentResponseDto> getBookedAppointments(Long specialistId) {
        logger.info("Fetching booked appointments for specialist with id: {}", specialistId);
        Specialist specialist = new Specialist();
        specialist.setId(specialistId);
        return appointmentRepository.findBySpecialistAndAppointmentStatus(specialist, Appointment.AppointmentStatus.BOOKED)
                .stream()
                .map(appointmentMapper::toResponseDto)
                .collect(Collectors.toList());
    }



    @Transactional(readOnly = true)
    @Override
    public List<AppointmentResponseDto> getSpecialistAppointments(Long specialistId) {
        logger.info("Fetching all appointments for specialist with id: {}", specialistId);
        Specialist specialist = new Specialist();
        specialist.setId(specialistId);
        return appointmentRepository.findBySpecialist(specialist)
                .stream()
                .map(appointmentMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponseDto> getClientAppointments(Long clientId) {
        logger.info("Fetching all appointments for client with id: {}", clientId);
        Client client = new Client();
        client.setId(clientId);
        return appointmentRepository.findByClient(client)
                .stream()
                .map(appointmentMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    private void validateAppointment(Appointment appointment) {
        var violations = validator.validate(appointment);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Invalid appointment: " + violations);
        }
    }
}