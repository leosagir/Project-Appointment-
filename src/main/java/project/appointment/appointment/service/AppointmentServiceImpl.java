package project.appointment.appointment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import project.appointment.appointment.dto.AppointmentRequestDto;
import project.appointment.appointment.dto.AppointmentResponseDto;
import project.appointment.appointment.entity.Appointment;
import project.appointment.appointment.repository.AppointmentRepository;
import project.appointment.client.entity.Client;
import project.appointment.client.repository.ClientRepository;
import project.appointment.exception.ResourceNotFoundException;
import project.appointment.exception.UnauthorizedAccessException;
import project.appointment.specialist.entity.Specialist;
import project.appointment.specialist.repository.SpecialistRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);
    private final AppointmentRepository appointmentRepository;
    private final SpecialistRepository specialistRepository;
    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public AppointmentResponseDto createFreeAppointment(Long specialistId, AppointmentRequestDto appointmentRequestDto) {
        logger.info("Creating free appointment for specialist with ID: {}",specialistId);
        Specialist specialist =specialistRepository.findById(specialistId)
                .orElseThrow(() -> {
                    logger.error("Specialist not found with ID: ",specialistId);
                    return new ResourceNotFoundException("Specialist not found");
                });
        Appointment appointment = modelMapper.map(appointmentRequestDto, Appointment.class);
        appointment.setSpecialist(specialist);
        appointment.setAppointmentStatus(Appointment.AppointmentStatus.AVAILABLE);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        logger.info("Free appointment for specialist with ID: {}",savedAppointment.getId());
        return modelMapper.map(savedAppointment, AppointmentResponseDto.class);
    }

    @Override
    @Transactional
    public AppointmentResponseDto updateFreeAppointment(Long appointmentId, AppointmentRequestDto appointmentRequestDto) {
        logger.info("Updating free appointment for specialist with ID: {}",appointmentId);
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(()->{
                    logger.error("Appointment not found with ID: ",appointmentId);
                    return new ResourceNotFoundException("Appointment not found");
                });
        if(appointment.getAppointmentStatus()!=Appointment.AppointmentStatus.AVAILABLE){
            logger.warn("Attempt to update non-free appointment with ID: {}", appointmentId);
            throw  new IllegalStateException("Cannot update a non-free appointment");
        }
        modelMapper.map(appointmentRequestDto, appointment);
        Appointment updatedAppointment = appointmentRepository.save(appointment);
        logger.info("Free appointment for specialist with ID: {}",updatedAppointment.getId());
        return modelMapper.map(updatedAppointment, AppointmentResponseDto.class);
    }

    @Override
    public void deleteFreeAppointment(Long specialistId, Long appointmentId) {
        logger.info("Deleting free appointment with ID:{} for specialist with ID: {}",appointmentId,specialistId);
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(()->{
                    logger.error("Appointment not found with ID: ",appointmentId);
                    return new ResourceNotFoundException("Appointment not found");
                });
        if(!appointment.getSpecialist().getId().equals(specialistId)){
            logger.warn("Attempt to delete appointment {} not belonging to specialist ID: {}", appointmentId,specialistId);
            throw  new IllegalStateException("Appointment does not belong to specified specialist");
        }
        if(appointment.getAppointmentStatus()!=Appointment.AppointmentStatus.AVAILABLE){
            logger.warn("Attempt to delete non-free appointment with ID: {}", appointmentId);
            throw new IllegalStateException("Cannot delete a non-free appointment");
        }
        appointmentRepository.delete(appointment);
        logger.info("Free appointment deleted successfully with ID: {}",appointmentId);

    }

    @Override
    @Transactional
    public AppointmentResponseDto bookAppointment(Long clientId, Long appointmentId) {
        logger.info("Booking appointment with ID: {} for client with ID: {}", appointmentId, clientId);
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> {
                    logger.error("Appointment not found with ID: {}", appointmentId);
                    return new ResourceNotFoundException("Appointment not found");
                });
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> {
                    logger.error("Client not found with ID: {}", clientId);
                    return new ResourceNotFoundException("Client not found");
                });

        if (appointment.getAppointmentStatus() != Appointment.AppointmentStatus.AVAILABLE) {
            logger.warn("Attempt to book unavailable appointment with ID: {}", appointmentId);
            throw new IllegalStateException("Appointment is not available for booking");
        }

        appointment.setClient(client);
        appointment.setAppointmentStatus(Appointment.AppointmentStatus.BOOKED);
        Appointment bookedAppointment = appointmentRepository.save(appointment);
        logger.info("Appointment booked successfully with ID: {}", bookedAppointment.getId());
        return modelMapper.map(bookedAppointment, AppointmentResponseDto.class);
    }

    @Override
    @Transactional
    public AppointmentResponseDto cancelBookedAppointment(Long clientId, Long appointmentId) {
        logger.info("Cancelling booked appointment with ID: {} for client ID: {}", appointmentId, clientId);
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> {
                    logger.error("Appointment not found with ID: {}", appointmentId);
                    return new ResourceNotFoundException("Appointment not found");
                });

        if (clientId != -1 && !appointment.getClient().getId().equals(clientId)) {
            logger.warn("Unauthorized attempt to cancel appointment. Client ID: {}, Appointment ID: {}", clientId, appointmentId);
            throw new UnauthorizedAccessException("You are not authorized to cancel this appointment");
        }

        if (appointment.getAppointmentStatus() != Appointment.AppointmentStatus.BOOKED) {
            logger.warn("Attempt to cancel non-booked appointment with ID: {}", appointmentId);
            throw new IllegalStateException("Cannot cancel a non-booked appointment");
        }

        appointment.setClient(null);
        appointment.setAppointmentStatus(Appointment.AppointmentStatus.AVAILABLE);
        Appointment cancelledAppointment = appointmentRepository.save(appointment);
        logger.info("Booked appointment cancelled successfully with ID: {}", cancelledAppointment.getId());
        return modelMapper.map(cancelledAppointment, AppointmentResponseDto.class);
    }

    @Override
    @Transactional
    public AppointmentResponseDto updateBookedAppointment(Long clientId, Long appointmentId, AppointmentRequestDto appointmentRequestDto) {
        logger.info("Updating booked appointment with ID: {} for client ID: {}", appointmentId, clientId);
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> {
                    logger.error("Appointment not found with ID: {}", appointmentId);
                    return new ResourceNotFoundException("Appointment not found");
                });

        if (clientId != -1 && !appointment.getClient().getId().equals(clientId)) {
            logger.warn("Unauthorized attempt to update appointment. Client ID: {}, Appointment ID: {}", clientId, appointmentId);
            throw new UnauthorizedAccessException("You are not authorized to update this appointment");
        }

        if (appointment.getAppointmentStatus() != Appointment.AppointmentStatus.BOOKED) {
            logger.warn("Attempt to update non-booked appointment with ID: {}", appointmentId);
            throw new IllegalStateException("Cannot update a non-booked appointment");
        }

        modelMapper.map(appointmentRequestDto, appointment);
        Appointment updatedAppointment = appointmentRepository.save(appointment);
        logger.info("Booked appointment updated successfully with ID: {}", updatedAppointment.getId());
        return modelMapper.map(updatedAppointment, AppointmentResponseDto.class);
    }

    @Override
    @Transactional
    public List<AppointmentResponseDto> getFreeAppointmentsForSpecialist(Long specialistId) {
        logger.info("Fetching free appointments for specialist with ID: {}", specialistId);
        Specialist specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() -> {
                    logger.error("Specialist not found with ID: {}", specialistId);
                    return new ResourceNotFoundException("Specialist not found");
                });

        List<Appointment> freeAppointments = appointmentRepository.findBySpecialistAndAppointmentStatus(
                specialist, Appointment.AppointmentStatus.AVAILABLE);

        logger.info("Found {} free appointments for specialist with ID: {}", freeAppointments.size(), specialistId);
        return freeAppointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<AppointmentResponseDto> getBookedAppointmentsForSpecialist(Long specialistId) {
        logger.info("Fetching booked appointments for specialist with ID: {}", specialistId);
        Specialist specialist = specialistRepository.findById(specialistId)
                .orElseThrow(() -> {
                    logger.error("Specialist not found with ID: {}", specialistId);
                    return new ResourceNotFoundException("Specialist not found");
                });

        List<Appointment> bookedAppointments = appointmentRepository.findBySpecialistAndAppointmentStatus(
                specialist, Appointment.AppointmentStatus.BOOKED);

        logger.info("Found {} booked appointments for specialist with ID: {}", bookedAppointments.size(), specialistId);
        return bookedAppointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<AppointmentResponseDto> getAppointmentsForClient(Long clientId) {
        logger.info("Fetching appointments for client with ID: {}", clientId);
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> {
                    logger.error("Client not found with ID: {}", clientId);
                    return new ResourceNotFoundException("Client not found");
                });

        List<Appointment> clientAppointments = appointmentRepository.findByClient(client);

        logger.info("Found {} appointments for client with ID: {}", clientAppointments.size(), clientId);
        return clientAppointments.stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                .collect(Collectors.toList());
    }
}