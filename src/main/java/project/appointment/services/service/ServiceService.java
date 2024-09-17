package project.appointment.services.service;

import jakarta.transaction.Transactional;
import project.appointment.services.dto.ServiceRequestDto;
import project.appointment.services.dto.ServiceResponseDto;
import project.appointment.services.dto.ServiceUpdateDto;

import java.util.List;

public interface ServiceService {
    ServiceResponseDto createService(ServiceRequestDto requestDto);
    ServiceResponseDto getServiceById(Long id);
    List<ServiceResponseDto> getAllServices();

    @Transactional
    ServiceResponseDto updateService(Long id, ServiceUpdateDto serviceUpdateDto);

    void deleteService(Long id);
}
