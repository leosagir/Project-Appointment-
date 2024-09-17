package project.appointment.specialization.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import project.appointment.specialization.dto.SpecializationRequestDto;
import project.appointment.specialization.dto.SpecializationResponseDto;
import project.appointment.specialization.dto.SpecializationUpdateDto;

import java.util.List;
@Service
public interface SpecializationService {
    SpecializationResponseDto createSpecialization(SpecializationRequestDto requestDto);
    SpecializationResponseDto getSpecializationById(Long id);
    List<SpecializationResponseDto> getAllSpecializations();
    SpecializationResponseDto updateSpecialization(Long id, SpecializationUpdateDto specializationUpdateDto);
    void deleteSpecialization(Long id);
}
