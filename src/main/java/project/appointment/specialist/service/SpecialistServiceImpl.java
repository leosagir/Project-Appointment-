package project.appointment.specialist.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.appointment.services.entity.SService;
import project.appointment.ENUM.Role;
import project.appointment.client.entity.Status;
import project.appointment.exception.EmailAlreadyExistsException;
import project.appointment.exception.RegistrationException;
import project.appointment.exception.ResourceNotFoundException;
import project.appointment.services.repository.SServiceRepository;
import project.appointment.specialist.dto.SpecialistRequestDto;
import project.appointment.specialist.dto.SpecialistResponseDto;
import project.appointment.specialist.dto.SpecialistUpdateDto;
import project.appointment.specialist.entity.Specialist;
import project.appointment.specialist.repository.SpecialistRepository;
import project.appointment.specialization.entity.Specialization;
import project.appointment.specialization.repository.SpecializationRepository;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SpecialistServiceImpl implements SpecialistService {
    private final SpecialistRepository specialistRepository;
    private final SpecializationRepository specializationRepository;
    private final SServiceRepository serviceRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;

    @Override
    @Transactional
    public SpecialistResponseDto registerSpecialist(SpecialistRequestDto specialistRequestDto) {
        if (specialistRequestDto == null) {
            throw new IllegalArgumentException("SpecialistRequestDto must not be null");
        }
        validateSpecialistRequest(specialistRequestDto);

        if (specialistRepository.existsByEmail(specialistRequestDto.getEmail())) {
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
            return modelMapper.map(savedSpecialist, SpecialistResponseDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new RegistrationException("Error during specialist registration", e);
        }
    }

    @Override
    @Transactional
    public SpecialistResponseDto updateSpecialist(Long id, SpecialistUpdateDto updateDto) {
        Specialist specialist = specialistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialist not found with id: " + id));

        if (updateDto.getFirstName() != null) {
            specialist.setFirstName(updateDto.getFirstName());
        }
        if (updateDto.getLastName() != null) {
            specialist.setLastName(updateDto.getLastName());
        }
        if (updateDto.getSpecializations() != null && !updateDto.getSpecializations().isEmpty()) {
            Set<Specialization> specializations = updateDto.getSpecializations().stream()
                    .map(specDto -> specializationRepository.findByTitle(specDto.getTitle())
                            .orElseThrow(() -> new ResourceNotFoundException("Specialization not found: " + specDto.getTitle())))
                    .collect(Collectors.toSet());
            specialist.setSpecializations(specializations);
        }
        if (updateDto.getServices() != null && !updateDto.getServices().isEmpty()) {
            Set<SService> services = updateDto.getServices().stream()
                    .map(serviceDto -> serviceRepository.findByTitle(serviceDto.getTitle())
                            .orElseThrow(() -> new ResourceNotFoundException("Service not found: " + serviceDto.getTitle())))
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

        specialist.setUpdatedAt(LocalDateTime.now());

        Specialist updatedSpecialist = specialistRepository.save(specialist);
        return modelMapper.map(updatedSpecialist, SpecialistResponseDto.class);
    }

    @Override
    @Transactional
    public SpecialistResponseDto deactivateSpecialist(Long id) {
        Specialist specialist = specialistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialist with id " + id + " not found"));

        if (specialist.getStatus() == Status.INACTIVE) {
            throw new IllegalStateException("Specialist is already inactive");
        }

        specialist.setStatus(Status.INACTIVE);
        specialist.setUpdatedAt(LocalDateTime.now());

        Specialist updatedSpecialist = specialistRepository.save(specialist);
        return modelMapper.map(updatedSpecialist, SpecialistResponseDto.class);
    }

    private void validateSpecialistRequest(SpecialistRequestDto specialistRequestDto) {
        Set<ConstraintViolation<SpecialistRequestDto>> violations = validator.validate(specialistRequestDto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}

