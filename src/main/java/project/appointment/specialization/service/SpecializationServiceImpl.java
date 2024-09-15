package project.appointment.specialization.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import project.appointment.exception.ResourceNotFoundException;
import project.appointment.specialist.entity.Specialist;
import project.appointment.specialist.repository.SpecialistRepository;
import project.appointment.specialization.dto.SpecializationRequestDto;
import project.appointment.specialization.dto.SpecializationResponseDto;
import project.appointment.specialization.entity.Specialization;
import project.appointment.specialization.repository.SpecializationRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecializationServiceImpl implements SpecializationService{

    private static final Logger logger = LoggerFactory.getLogger(SpecializationServiceImpl.class);

    private final SpecializationRepository specializationRepository;
    private final SpecialistRepository specialistRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;

    @Override
    @Transactional
    public SpecializationResponseDto createSpecialization(SpecializationRequestDto requestDto) {
        logger.info("Attempting to create a new specialization with title: {}",requestDto.getTitle());
        if(requestDto==null){
            logger.error("SpecializationRequestDto is null");
            throw new IllegalArgumentException("SpecializationRequestDto must not be null");
        }
        validateSpecializationRequest(requestDto);

        if(specializationRepository.findByTitle(requestDto.getTitle()).isPresent()){
            throw new IllegalStateException("Specialization with this title already exists");
        }
        Specialization specialization = modelMapper.map(requestDto,Specialization.class);
        specialization.setSpecialists(getSpecialistFromIds(requestDto.getSpecialistIds()));

        try{
            Specialization savedSpecialization = specializationRepository.save(specialization);
            logger.info("Successfully created specialization with id:{}",savedSpecialization.getId());
            return mapToResponseDto(savedSpecialization);
        }catch(DataIntegrityViolationException e){
            logger.error("Error during specialization creation", e);
            throw new RuntimeException("Error during specialization creation",e);
        }
    }

    @Override
    @Transactional
    public SpecializationResponseDto getSpecialization(Long id) {
        logger.info("Fetching specialization with id: {}",id);
        Specialization specialization = specializationRepository.findById(id)
                .orElseThrow(()->{
                    logger.error("Specialization not found with id: {}",id);
                    return new ResourceNotFoundException("Specialization not found with id: "+id);
                });
        return mapToResponseDto(specialization);
    }

    @Override
    @Transactional
    public List<SpecializationResponseDto> getAllSpecializations() {
        logger.info("Fetching all specializations");
        List<Specialization> specializations = specializationRepository.findAll();
        return specializations.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SpecializationResponseDto updateSpecialization(Long id, SpecializationRequestDto updateDto) {
        logger.info("Attempting to update specialization with id: {}",id);
        Specialization specialization = specializationRepository.findById(id)
                .orElseThrow(()->{
                    logger.error("Specialization not found with id: ",id);
                    return new ResourceNotFoundException("Specialisation not found with id: "+id);
                });
        if(updateDto.getTitle() != null){
            specialization.setTitle(updateDto.getTitle());
        }
        if(updateDto.getSpecialistIds() != null){
            specialization.setSpecialists(getSpecialistFromIds(updateDto.getSpecialistIds()));
        }
        Specialization updateSpecialisation = specializationRepository.save(specialization);
        logger.info("Successfully updated specialization with id: {}",id);
        return mapToResponseDto(updateSpecialisation);
    }

    @Override
    @Transactional
    public void deleteSpecialization(Long id) {
        logger.info("Attempting to delete specialization with id: {}",id);
        if(!specializationRepository.existsById(id)){
            logger.error("Specialization not found with id:{}",id);
            throw new ResourceNotFoundException("Specialization not found with id: "+id);
        }
        specializationRepository.deleteById(id);
        logger.info("Successfully deleted specialization with id: {}",id);
    }

    private Set<Specialist> getSpecialistFromIds(Set<Long> specialistIds){
        if(specialistIds==null){
            return Set.of();
        }
        return specialistIds.stream()
                .map(specId->specialistRepository.findById(specId)
                        .orElseThrow(()->{
                            logger.error("Specialist not found with id: {}",specId);
                            return new ResourceNotFoundException("Specialist not found with id: "+specId);
                        }))
                .collect(Collectors.toSet());
    }

    private void validateSpecializationRequest(SpecializationRequestDto requestDto) {
        Set<ConstraintViolation<SpecializationRequestDto>> violations = validator.validate(requestDto);
        if (!violations.isEmpty()) {
            logger.error("Validation failed for SpecializationRequestDto: {}", violations);
            throw new ConstraintViolationException(violations);
        }
    }

    private SpecializationResponseDto mapToResponseDto(Specialization specialization) {
        SpecializationResponseDto responseDto = modelMapper.map(specialization, SpecializationResponseDto.class);
        responseDto.setSpecialists(
                specialization.getSpecialists().stream()
                        .map(Specialist::getId)
                        .collect(Collectors.toSet())
        );
        return responseDto;
    }
}
