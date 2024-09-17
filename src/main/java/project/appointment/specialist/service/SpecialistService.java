package project.appointment.specialist.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import project.appointment.specialist.dto.SpecialistRequestDto;
import project.appointment.specialist.dto.SpecialistResponseDto;
import project.appointment.specialist.dto.SpecialistUpdateDto;
import project.appointment.specialist.entity.Specialist;

import java.util.List;
import java.util.Set;

@Service
public interface SpecialistService {
   SpecialistResponseDto registerSpecialist(SpecialistRequestDto specialistRequestDto);
   SpecialistResponseDto updateSpecialist(Long id, SpecialistUpdateDto updateDto);
   void deleteSpecialist(Long id);
   SpecialistResponseDto deactivateSpecialist(Long id);
   SpecialistResponseDto getSpecialistById(Long id);
   List<SpecialistResponseDto> getAllSpecialists();
   SpecialistResponseDto reactivateSpecialist(Long id);
    void setSpecializationsAndServices(Specialist specialist, Set<Long> specializationIds, Set<Long> serviceIds);
}
