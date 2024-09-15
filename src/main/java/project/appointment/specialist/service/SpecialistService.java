package project.appointment.specialist.service;

import org.springframework.stereotype.Service;
import project.appointment.specialist.dto.SpecialistRequestDto;
import project.appointment.specialist.dto.SpecialistResponseDto;
import project.appointment.specialist.dto.SpecialistUpdateDto;

import java.util.List;

@Service
public interface SpecialistService {
   SpecialistResponseDto registerSpecialist(SpecialistRequestDto specialistRequestDto);
   SpecialistResponseDto updateSpecialist(Long id, SpecialistUpdateDto updateDto);
   SpecialistResponseDto deactivateSpecialist(Long id);
   SpecialistResponseDto getSpecialistById(Long id);
   List<SpecialistResponseDto> getAllSpecialists();
}
