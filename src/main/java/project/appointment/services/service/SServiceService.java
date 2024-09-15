package project.appointment.services.service;

import org.springframework.stereotype.Service;
import project.appointment.services.dto.SServiceRequestDto;
import project.appointment.services.dto.SServiceResponseDto;
import project.appointment.services.entity.SService;

import java.util.List;

@Service
public interface SServiceService {
    SServiceResponseDto createService(SServiceRequestDto requestDto);
    SServiceResponseDto getServiceById(Long id);
    List<SServiceResponseDto> getAllServices();
    SServiceResponseDto updateService(Long id,SServiceRequestDto requestDto);
    void deleteService(Long id);
}
