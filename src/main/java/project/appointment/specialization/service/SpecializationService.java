package project.appointment.specialization.service;

import org.springframework.stereotype.Service;
import project.appointment.specialization.dto.SpecializationRequestDto;
import project.appointment.specialization.dto.SpecializationResponseDto;

import java.util.List;
@Service
public interface SpecializationService {
    SpecializationResponseDto createSpecialization(SpecializationRequestDto requestDto);
    SpecializationResponseDto getSpecialization(Long id);
    List<SpecializationResponseDto> getAllSpecializations();
    SpecializationResponseDto updateSpecialization(Long id, SpecializationRequestDto updateDto);
    void deleteSpecialization(Long id);
}
