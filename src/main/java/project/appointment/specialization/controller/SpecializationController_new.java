package project.appointment.specialization.controller;

import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.appointment.specialization.dto.SpecializationRequestDto;
import project.appointment.specialization.dto.SpecializationResponseDto;
import project.appointment.specialization.dto.SpecializationUpdateDto;
import project.appointment.specialization.service.SpecializationServiceImpl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/specializations")
@RequiredArgsConstructor
@Slf4j
public class SpecializationController_new {

    private final SpecializationServiceImpl specializationService;
    private final Validator validator;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<SpecializationResponseDto> createSpecialization(@Valid @RequestBody SpecializationRequestDto specializationRequestDto) throws URISyntaxException {
        log.info("REST request to create Specialization : {}", specializationRequestDto);
        SpecializationResponseDto result = specializationService.createSpecialization(specializationRequestDto);
        return ResponseEntity.created(new URI("/api/specializations/" + result.getId())).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecializationResponseDto> getSpecialization(@PathVariable Long id) {
        log.info("REST request to get Specialization : {}", id);
        SpecializationResponseDto specializationDto = specializationService.getSpecializationById(id);
        return ResponseEntity.ok(specializationDto);
    }

    @GetMapping
    public ResponseEntity<List<SpecializationResponseDto>> getAllSpecializations() {
        log.info("REST request to get all Specializations");
        List<SpecializationResponseDto> specializations = specializationService.getAllSpecializations();
        return ResponseEntity.ok(specializations);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<SpecializationResponseDto> updateSpecialization(@PathVariable Long id, @Valid @RequestBody SpecializationUpdateDto specializationUpdateDto) {
        log.info("REST request to update Specialization : {}, {}", id, specializationUpdateDto);
        SpecializationResponseDto result = specializationService.updateSpecialization(id, specializationUpdateDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Void> deleteSpecialization(@PathVariable Long id) {
        log.info("REST request to delete Specialization : {}", id);
        specializationService.deleteSpecialization(id);
        return ResponseEntity.noContent().build();
    }
}
