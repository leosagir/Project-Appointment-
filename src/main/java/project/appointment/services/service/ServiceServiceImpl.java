package project.appointment.services.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import project.appointment.exception.ResourceNotFoundException;
import project.appointment.services.ServiceMapper;
import project.appointment.services.dto.ServiceRequestDto;
import project.appointment.services.dto.ServiceResponseDto;
import project.appointment.services.dto.ServiceUpdateDto;
import project.appointment.services.entity.Service;
import project.appointment.services.repository.ServiceRepository;
import project.appointment.specialization.entity.Specialization;
import project.appointment.specialization.repository.SpecializationRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class ServiceServiceImpl implements ServiceService{
    private final ServiceRepository serviceRepository;
    private final SpecializationRepository specializationRepository;
    private final ServiceMapper serviceMapper;
    private final Validator validator;

    @Transactional
    @Override
    public ServiceResponseDto createService(ServiceRequestDto serviceRequestDto) {
        log.info("Creating new service with title: {}", serviceRequestDto.getTitle());
        validateServiceRequest(serviceRequestDto);

        Service service = serviceMapper.serviceRequestDtoToService(serviceRequestDto);
        Specialization specialization = specializationRepository.findById(serviceRequestDto.getSpecializationId())
                .orElseThrow(() -> new ResourceNotFoundException("Specialization not found"));
        service.setSpecialization(specialization);

        Service savedService = serviceRepository.save(service);
        log.info("Service created successfully with id: {}", savedService.getId());
        return serviceMapper.serviceToServiceResponseDto(savedService);
    }
    @Override
    @Transactional
    public ServiceResponseDto getServiceById(Long id) {
        log.info("Fetching service with id: {}", id);
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));
        return serviceMapper.serviceToServiceResponseDto(service);
    }
    @Override
    @Transactional
    public List<ServiceResponseDto> getAllServices() {
        log.info("Fetching all services");
        List<Service> services = serviceRepository.findAll();
        return services.stream()
                .map(serviceMapper::serviceToServiceResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ServiceResponseDto updateService(Long id, ServiceUpdateDto serviceUpdateDto) {
        log.info("Updating service with id: {}", id);
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));

        serviceMapper.updateServiceFromDto(serviceUpdateDto, service);
        if (serviceUpdateDto.getSpecializationId() != null) {
            Specialization specialization = specializationRepository.findById(serviceUpdateDto.getSpecializationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Specialization not found"));
            service.setSpecialization(specialization);
        }

        Service updatedService = serviceRepository.save(service);
        log.info("Service updated successfully with id: {}", updatedService.getId());
        return serviceMapper.serviceToServiceResponseDto(updatedService);
    }

    @Transactional
    @Override
    public void deleteService(Long id) {
        log.info("Deleting service with id: {}", id);
        if (!serviceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Service not found");
        }
        serviceRepository.deleteById(id);
        log.info("Service deleted successfully with id: {}", id);
    }

    private void validateServiceRequest(ServiceRequestDto serviceRequestDto) {
        Set<ConstraintViolation<ServiceRequestDto>> violations = validator.validate(serviceRequestDto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
