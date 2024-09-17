package project.appointment.specialist.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.appointment.security.SecurityService;
import project.appointment.specialist.dto.SpecialistRequestDto;
import project.appointment.specialist.dto.SpecialistResponseDto;
import project.appointment.specialist.dto.SpecialistUpdateDto;
import project.appointment.specialist.service.SpecialistService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/specialists")
@RequiredArgsConstructor
@Slf4j

public class SpecialistController_new {
    private final SpecialistService specialistService;
    private final SecurityService securityService;

    @GetMapping("/my")
    @PreAuthorize("hasRole('SPECIALIST')")
    public ResponseEntity<SpecialistResponseDto> getCurrentSpecialist() {
        Long currentUserId = securityService.getCurrentUserId();
        if (currentUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        SpecialistResponseDto specialist = specialistService.getSpecialistById(currentUserId);
        return ResponseEntity.ok(specialist);
    }
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<SpecialistResponseDto> registerSpecialist(@Valid @RequestBody SpecialistRequestDto specialistRequestDto) throws URISyntaxException {
        log.info("REST request to register Specialist : {}", specialistRequestDto);
        SpecialistResponseDto result = specialistService.registerSpecialist(specialistRequestDto);
        return ResponseEntity.created(new URI("/api/specialists/" + result.getId())).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialistResponseDto> getSpecialist(@PathVariable Long id) {
        log.info("REST request to get Specialist : {}", id);
        SpecialistResponseDto specialistDto = specialistService.getSpecialistById(id);
        return ResponseEntity.ok(specialistDto);
    }

    @GetMapping
    public ResponseEntity<List<SpecialistResponseDto>> getAllSpecialists() {
        log.info("REST request to get all Specialists");
        List<SpecialistResponseDto> specialists = specialistService.getAllSpecialists();
        return ResponseEntity.ok(specialists);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or @securityService.isCurrentUser(#id)")
    public ResponseEntity<SpecialistResponseDto> updateSpecialist(@PathVariable Long id, @Valid @RequestBody SpecialistUpdateDto specialistUpdateDto) {
        log.info("REST request to update Specialist : {}, {}", id, specialistUpdateDto);
        SpecialistResponseDto result = specialistService.updateSpecialist(id, specialistUpdateDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Void> deleteSpecialist(@PathVariable Long id) {
        log.info("REST request to delete Specialist : {}", id);
        specialistService.deleteSpecialist(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<SpecialistResponseDto> deactivateSpecialist(@PathVariable Long id) {
        log.info("REST request to deactivate Specialist : {}", id);
        SpecialistResponseDto result = specialistService.deactivateSpecialist(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/reactivate")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<SpecialistResponseDto> reactivateSpecialist(@PathVariable Long id) {
        log.info("REST request to reactivate Specialist : {}", id);
        SpecialistResponseDto result = specialistService.reactivateSpecialist(id);
        return ResponseEntity.ok(result);
    }

}
