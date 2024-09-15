package project.appointment.services.service;

import jakarta.transaction.Transactional;
import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import project.appointment.exception.ServiceNotFoundException;
import project.appointment.exception.SpecialistNotFoundException;
import project.appointment.exception.SpecializationNotFoundException;
import project.appointment.services.dto.SServiceRequestDto;
import project.appointment.services.dto.SServiceResponseDto;
import project.appointment.services.entity.SService;
import project.appointment.services.repository.SServiceRepository;
import project.appointment.specialist.entity.Specialist;
import project.appointment.specialist.repository.SpecialistRepository;
import project.appointment.specialization.entity.Specialization;
import project.appointment.specialization.repository.SpecializationRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class SServiceServiceImpl implements SServiceService {

    private final Logger logger = LoggerFactory.getLogger(SServiceServiceImpl.class);

    private final SServiceRepository serviceRepository;
    private final SpecialistRepository specialistRepository;
    private final SpecializationRepository specializationRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;



    @Override
    @Transactional
    public SServiceResponseDto createService(@Valid SServiceRequestDto requestDto) {
        logger.info("Creating new service with title: {}", requestDto.getTitle());
        SService service = modelMapper.map(requestDto, SService.class);
        setRelatedEntities(service, requestDto);
        SService savedService = serviceRepository.save(service);
        logger.info("Service created successfully with ID: {}", savedService.getId());
        return modelMapper.map(savedService, SServiceResponseDto.class);
    }

    @Override
    @Transactional
    public SServiceResponseDto getServiceById(Long id) {
       logger.info("Fetching service with ID: {}", id);
       SService service = serviceRepository.findById(id)
               .orElseThrow(()->{
                   logger.error("Service with ID: {} not found", id);
                   return new ServiceNotFoundException("Service with ID: " + id + " not found");
               });
       return modelMapper.map(service, SServiceResponseDto.class);
    }

    @Override
    @Transactional
    public List<SServiceResponseDto> getAllServices() {
       logger.info("Fetching all services");
       List<SService> services = serviceRepository.findAll();
       return services.stream()
               .map(service->modelMapper.map(service,SServiceResponseDto.class))
               .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteService(Long id) {
    logger.info("Deleting service with ID: {}", id);
    if(!serviceRepository.existsById(id)) {
        logger.error("Service with ID: {} not found", id);
        throw new ServiceNotFoundException("Service with ID: " + id + " not found");
    }
    serviceRepository.deleteById(id);
    logger.info("Service deleted successfully with ID: {}", id);
    }


    private void setRelatedEntities(SService service, SServiceRequestDto dto) {
        if (dto.getSpecializationId() == null) {
            throw new ValidationException("Specialization ID must be provided");
        }
        Specialization specialization = specializationRepository.findById(dto.getSpecializationId())
                .orElseThrow(() -> new SpecializationNotFoundException("Specialization not found"));
        service.setSpecialization(specialization);

        if (dto.getSpecialistId() == null || dto.getSpecialistId().isEmpty()) {
            throw new SpecialistNotFoundException("Specialist IDs must be provided");
        }
        Set<Specialist> specialists = new HashSet<>(specialistRepository.findAllById(dto.getSpecialistId()));
        if (specialists.size() != dto.getSpecialistId().size()) {
            throw new SpecialistNotFoundException("One or more specialists not found");
        }
        service.setSpecialists(specialists);
    }
    @Override
    @Transactional
    public SServiceResponseDto updateService(Long id, SServiceRequestDto requestDto) {
        logger.info("Updating service with ID: {}", id);
        SService existingService = serviceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException(id));
        SService updatedService = modelMapper.map(existingService, SService.class);
        modelMapper.map(requestDto, updatedService);
        updateRelatedEntities(updatedService, requestDto);
        Set<ConstraintViolation<SService>> violations = validator.validate(updatedService);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        SService savedService = serviceRepository.save(updatedService);
        logger.info("Service updated successfully with ID: {}", savedService.getId());

        return modelMapper.map(savedService, SServiceResponseDto.class);
    }

    private void updateRelatedEntities(SService service, SServiceRequestDto dto) {
        if (dto.getSpecializationId() != null) {
            Specialization specialization = specializationRepository.findById(dto.getSpecializationId())
                    .orElseThrow(() -> new SpecializationNotFoundException(dto.getSpecializationId()));
            service.setSpecialization(specialization);
        }

        if (dto.getSpecialistId() != null && !dto.getSpecialistId().isEmpty()) {
            Set<Specialist> specialists = new HashSet<>(specialistRepository.findAllById(dto.getSpecialistId()));
            if (specialists.size() != dto.getSpecialistId().size()) {
                Set<Long> foundIds = specialists.stream().map(Specialist::getId).collect(Collectors.toSet());
                Set<Long> notFoundIds = new HashSet<>(dto.getSpecialistId());
                notFoundIds.removeAll(foundIds);
                throw new SpecialistNotFoundException("Specialists not found for IDs: " + notFoundIds);
            }
            service.setSpecialists(specialists);
        }
    }
}

