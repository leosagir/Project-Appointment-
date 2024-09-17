package project.appointment.specialization.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.appointment.exception.ResourceNotFoundException;
import project.appointment.specialization.SpecializationMapper;
import project.appointment.specialization.dto.SpecializationRequestDto;
import project.appointment.specialization.dto.SpecializationResponseDto;
import project.appointment.specialization.dto.SpecializationUpdateDto;
import project.appointment.specialization.entity.Specialization;
import project.appointment.specialization.repository.SpecializationRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpecializationServiceImpl implements SpecializationService {
    private final SpecializationRepository specializationRepository;
    private final SpecializationMapper specializationMapper;
    private final Validator validator;

    @Transactional
    @Override
    public SpecializationResponseDto createSpecialization(SpecializationRequestDto specializationRequestDto) {
        log.info("Creating new specialization with title: {}", specializationRequestDto.getTitle());
        validateSpecializationRequest(specializationRequestDto);

        Specialization specialization = specializationMapper.specializationRequestDtoToSpecialization(specializationRequestDto);
        Specialization savedSpecialization = specializationRepository.save(specialization);
        log.info("Specialization created successfully with id: {}", savedSpecialization.getId());
        return specializationMapper.specializationToSpecializationResponseDto(savedSpecialization);
    }
    @Override
    @Transactional
    public SpecializationResponseDto getSpecializationById(Long id) {
        log.info("Fetching specialization with id: {}", id);
        Specialization specialization = specializationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialization not found"));
        return specializationMapper.specializationToSpecializationResponseDto(specialization);
    }
    @Override
    @Transactional
    public List<SpecializationResponseDto> getAllSpecializations() {
        log.info("Fetching all specializations");
        List<Specialization> specializations = specializationRepository.findAll();
        return specializations.stream()
                .map(specializationMapper::specializationToSpecializationResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public SpecializationResponseDto updateSpecialization(Long id, SpecializationUpdateDto specializationUpdateDto) {
        log.info("Updating specialization with id: {}", id);
        Specialization specialization = specializationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Specialization not found"));

        specializationMapper.updateSpecializationFromDto(specializationUpdateDto, specialization);
        Specialization updatedSpecialization = specializationRepository.save(specialization);
        log.info("Specialization updated successfully with id: {}", updatedSpecialization.getId());
        return specializationMapper.specializationToSpecializationResponseDto(updatedSpecialization);
    }

    @Transactional
    @Override
    public void deleteSpecialization(Long id) {
        log.info("Deleting specialization with id: {}", id);
        if (!specializationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Specialization not found");
        }
        specializationRepository.deleteById(id);
        log.info("Specialization deleted successfully with id: {}", id);
    }

    private void validateSpecializationRequest(SpecializationRequestDto specializationRequestDto) {
        Set<ConstraintViolation<SpecializationRequestDto>> violations = validator.validate(specializationRequestDto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}