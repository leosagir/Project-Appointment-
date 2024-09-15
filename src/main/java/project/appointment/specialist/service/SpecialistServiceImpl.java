package project.appointment.specialist.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.appointment.specialist.dto.SpecialistRequestDto;
import project.appointment.specialist.dto.SpecialistResponseDto;
import project.appointment.specialist.dto.SpecialistUpdateDto;
import project.appointment.specialist.entity.Specialist;
import project.appointment.specialist.repository.SpecialistRepository;
import project.appointment.specialization.repository.SpecializationRepository;
import project.appointment.services.repository.SServiceRepository;
import project.appointment.exception.ResourceNotFoundException;
import project.appointment.exception.EmailAlreadyExistsException;
import project.appointment.exception.RegistrationException;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import project.appointment.ENUM.Role;
import project.appointment.client.entity.Status;
import project.appointment.specialization.entity.Specialization;
import project.appointment.services.entity.SService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecialistServiceImpl implements SpecialistService {
    private static final Logger logger = LoggerFactory.getLogger(SpecialistServiceImpl.class);

    private final SpecialistRepository specialistRepository;
    private final SpecializationRepository specializationRepository;
    private final SServiceRepository serviceRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;

    @Override
    public SpecialistResponseDto getSpecialistById(Long id) {
        logger.info("Fetching specialist with id: {}", id);
        Specialist specialist = specialistRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Specialist not found with id: {}", id);
                    return new ResourceNotFoundException("Specialist not found with id: " + id);
                });
        logger.info("Specialist found with id: {}", id);
        return modelMapper.map(specialist, SpecialistResponseDto.class);
    }

    @Override
    public List<SpecialistResponseDto> getAllSpecialists() {
        logger.info("Fetching all specialists");
        List<Specialist> specialists = specialistRepository.findAll();
        logger.info("Found {} specialists", specialists.size());
        return specialists.stream()
                .map(specialist -> modelMapper.map(specialist, SpecialistResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SpecialistResponseDto registerSpecialist(SpecialistRequestDto specialistRequestDto) {
        logger.info("Registering new specialist with email: {}", specialistRequestDto.getEmail());
        if (specialistRequestDto == null) {
            logger.error("SpecialistRequestDto is null");
            throw new IllegalArgumentException("SpecialistRequestDto must not be null");
        }
        validateSpecialistRequest(specialistRequestDto);

        if (specialistRepository.existsByEmail(specialistRequestDto.getEmail())) {
            logger.error("Email already exists: {}", specialistRequestDto.getEmail());
            throw new EmailAlreadyExistsException("Email already exists");
        }
        Specialist specialist = modelMapper.map(specialistRequestDto, Specialist.class);
        specialist.setPassword(passwordEncoder.encode(specialistRequestDto.getPassword()));
        specialist.setCreatedAt(LocalDateTime.now());
        specialist.setUpdatedAt(LocalDateTime.now());
        specialist.setRole(Role.SPECIALIST);
        specialist.setStatus(Status.ACTIVE);

        try {
            Specialist savedSpecialist = specialistRepository.save(specialist);
            logger.info("Specialist registered successfully with id: {}", savedSpecialist.getId());
            return modelMapper.map(savedSpecialist, SpecialistResponseDto.class);
        } catch (DataIntegrityViolationException e) {
            logger.error("Error during specialist registration", e);
            throw new RegistrationException("Error during specialist registration", e);
        }
    }

    @Override
    @Transactional
    public SpecialistResponseDto updateSpecialist(Long id, SpecialistUpdateDto updateDto) {
        logger.info("Updating specialist with id: {}", id);
        Specialist specialist = specialistRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Specialist not found with id: {}", id);
                    return new ResourceNotFoundException("Specialist not found with id: " + id);
                });

        updateSpecialistFields(specialist, updateDto);

        specialist.setUpdatedAt(LocalDateTime.now());

        Specialist updatedSpecialist = specialistRepository.save(specialist);
        logger.info("Specialist updated successfully with id: {}", updatedSpecialist.getId());
        return modelMapper.map(updatedSpecialist, SpecialistResponseDto.class);
    }

    @Override
    @Transactional
    public SpecialistResponseDto deactivateSpecialist(Long id) {
        logger.info("Deactivating specialist with id: {}", id);
        Specialist specialist = specialistRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Specialist not found with id: {}", id);
                    return new ResourceNotFoundException("Specialist with id " + id + " not found");
                });

        if (specialist.getStatus() == Status.INACTIVE) {
            logger.warn("Specialist with id: {} is already inactive", id);
            throw new IllegalStateException("Specialist is already inactive");
        }

        specialist.setStatus(Status.INACTIVE);
        specialist.setUpdatedAt(LocalDateTime.now());

        Specialist updatedSpecialist = specialistRepository.save(specialist);
        logger.info("Specialist deactivated successfully with id: {}", updatedSpecialist.getId());
        return modelMapper.map(updatedSpecialist, SpecialistResponseDto.class);
    }

    private void validateSpecialistRequest(SpecialistRequestDto specialistRequestDto) {
        Set<ConstraintViolation<SpecialistRequestDto>> violations = validator.validate(specialistRequestDto);
        if (!violations.isEmpty()) {
            logger.error("Validation failed for SpecialistRequestDto: {}", violations);
            throw new ConstraintViolationException(violations);
        }
    }

    private void updateSpecialistFields(Specialist specialist, SpecialistUpdateDto updateDto) {
        if (updateDto.getFirstName() != null) {
            specialist.setFirstName(updateDto.getFirstName());
        }
        if (updateDto.getLastName() != null) {
            specialist.setLastName(updateDto.getLastName());
        }
        if (updateDto.getSpecializations() != null && !updateDto.getSpecializations().isEmpty()) {
            Set<Specialization> specializations = updateDto.getSpecializations().stream()
                    .map(specDto -> specializationRepository.findByTitle(specDto.getTitle())
                            .orElseThrow(() -> {
                                logger.error("Specialization not found: {}", specDto.getTitle());
                                return new ResourceNotFoundException("Specialization not found: " + specDto.getTitle());
                            }))
                    .collect(Collectors.toSet());
            specialist.setSpecializations(specializations);
        }
        if (updateDto.getServices() != null && !updateDto.getServices().isEmpty()) {
            Set<SService> services = updateDto.getServices().stream()
                    .map(serviceDto -> serviceRepository.findByTitle(serviceDto.getTitle())
                            .orElseThrow(() -> {
                                logger.error("Service not found: {}", serviceDto.getTitle());
                                return new ResourceNotFoundException("Service not found: " + serviceDto.getTitle());
                            }))
                    .collect(Collectors.toSet());
            specialist.setServices(services);
        }
        if (updateDto.getDescription() != null) {
            specialist.setDescription(updateDto.getDescription());
        }
        if (updateDto.getDateOfBirth() != null) {
            specialist.setDateOfBirth(updateDto.getDateOfBirth());
        }
        if (updateDto.getAddress() != null) {
            specialist.setAddress(updateDto.getAddress());
        }
        if (updateDto.getPhone() != null) {
            specialist.setPhone(updateDto.getPhone());
        }
    }
}