package project.appointment.specialist.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.appointment.ENUM.Status;
import project.appointment.exception.EmailAlreadyExistsException;
import project.appointment.exception.ResourceNotFoundException;
import project.appointment.services.entity.Service;
import project.appointment.services.repository.ServiceRepository;
import project.appointment.specialist.SpecialistMapper;
import project.appointment.specialist.dto.SpecialistRequestDto;
import project.appointment.specialist.dto.SpecialistResponseDto;
import project.appointment.specialist.dto.SpecialistUpdateDto;
import project.appointment.specialist.entity.Specialist;
import project.appointment.specialist.repository.SpecialistRepository;
import project.appointment.specialization.entity.Specialization;
import project.appointment.specialization.repository.SpecializationRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class SpecialistServiceImpl implements SpecialistService {
    private final SpecialistRepository specialistRepository;
    private final SpecializationRepository specializationRepository;
    private final ServiceRepository serviceRepository;
    private final SpecialistMapper specialistMapper;
    private final Validator validator;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public SpecialistResponseDto registerSpecialist(SpecialistRequestDto specialistRequestDto) {
        log.info("Registering new specialist with email: {}", specialistRequestDto.getEmail());
        validateSpecialistRequest(specialistRequestDto);

        if (specialistRepository.existsByEmail(specialistRequestDto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        Specialist specialist = specialistMapper.specialistRequestDtoToSpecialist(specialistRequestDto);
        specialist.setPassword(passwordEncoder.encode(specialistRequestDto.getPassword()));
        specialist.setStatus(Status.ACTIVE);
        setSpecializationsAndServices(specialist, specialistRequestDto.getSpecializationIds(), specialistRequestDto.getServiceIds());

        Specialist savedSpecialist = specialistRepository.save(specialist);
        log.info("Specialist registered successfully with id: {}", savedSpecialist.getId());
        return specialistMapper.specialistToSpecialistResponseDto(savedSpecialist);
    }
    @Override
    @Transactional
    public SpecialistResponseDto getSpecialistById(Long id) {
        log.info("Fetching specialist with id: {}", id);
        Specialist specialist = specialistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialist not found"));
        return specialistMapper.specialistToSpecialistResponseDto(specialist);
    }
    @Override
    @Transactional
    public List<SpecialistResponseDto> getAllSpecialists() {
        log.info("Fetching all specialists");
        List<Specialist> specialists = specialistRepository.findAll();
        return specialists.stream()
                .map(specialistMapper::specialistToSpecialistResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public SpecialistResponseDto updateSpecialist(Long id, SpecialistUpdateDto specialistUpdateDto) {
        log.info("Updating specialist with id: {}", id);
        Specialist specialist = specialistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialist not found"));

        specialistMapper.updateSpecialistFromDto(specialistUpdateDto, specialist);
        if (specialistUpdateDto.getSpecializationIds() != null) {
            setSpecializationsAndServices(specialist, specialistUpdateDto.getSpecializationIds(), specialistUpdateDto.getServiceIds());
        }

        Specialist updatedSpecialist = specialistRepository.save(specialist);
        log.info("Specialist updated successfully with id: {}", updatedSpecialist.getId());
        return specialistMapper.specialistToSpecialistResponseDto(updatedSpecialist);
    }

    @Transactional
    @Override
    public void deleteSpecialist(Long id) {
        log.info("Deleting specialist with id: {}", id);
        if (!specialistRepository.existsById(id)) {
            throw new ResourceNotFoundException("Specialist not found");
        }
        specialistRepository.deleteById(id);
        log.info("Specialist deleted successfully with id: {}", id);
    }

    @Transactional
    @Override
    public SpecialistResponseDto deactivateSpecialist(Long id) {
        log.info("Deactivating specialist with id: {}", id);
        Specialist specialist = specialistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialist not found"));
        specialist.setStatus(Status.INACTIVE);
        Specialist updatedSpecialist = specialistRepository.save(specialist);
        log.info("Specialist deactivated successfully with id: {}", updatedSpecialist.getId());
        return specialistMapper.specialistToSpecialistResponseDto(updatedSpecialist);
    }

    @Transactional
    @Override
    public SpecialistResponseDto reactivateSpecialist(Long id) {
        log.info("Reactivating specialist with id: {}", id);
        Specialist specialist = specialistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialist not found"));
        specialist.setStatus(Status.ACTIVE);
        Specialist updatedSpecialist = specialistRepository.save(specialist);
        log.info("Specialist reactivated successfully with id: {}", updatedSpecialist.getId());
        return specialistMapper.specialistToSpecialistResponseDto(updatedSpecialist);
    }
    @Override
    @Transactional
    public void setSpecializationsAndServices(Specialist specialist, Set<Long> specializationIds, Set<Long> serviceIds) {
        Set<Specialization> specializations = specializationRepository.findAllById(specializationIds)
                .stream().collect(Collectors.toSet());
        specialist.setSpecializations(specializations);

        Set<Service> services = serviceRepository.findAllById(serviceIds)
                .stream().collect(Collectors.toSet());
        specialist.setServices(services);
    }

    private void validateSpecialistRequest(SpecialistRequestDto specialistRequestDto) {
        Set<ConstraintViolation<SpecialistRequestDto>> violations = validator.validate(specialistRequestDto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}