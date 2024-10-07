package project.appointment.specialist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.appointment.appointment.dto.AppointmentResponseDto;
import project.appointment.appointment.service.AppointmentService;
import project.appointment.security.AppUser;
import project.appointment.specialist.dto.SpecialistRequestDto;
import project.appointment.specialist.dto.SpecialistResponseDto;
import project.appointment.specialist.dto.SpecialistUpdateDto;
import project.appointment.specialist.service.SpecialistService;

import java.util.List;

@RestController
@RequestMapping("/api/specialists")
@RequiredArgsConstructor
public class SpecialistController_new {

    private final SpecialistService specialistService;
    private final AppointmentService appointmentService;

    @PostMapping("/register")
    public ResponseEntity<SpecialistResponseDto> registerSpecialist(@RequestBody SpecialistRequestDto specialistRequestDto) {
        SpecialistResponseDto createdSpecialist = specialistService.registerSpecialist(specialistRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSpecialist);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialistResponseDto> getSpecialistById(@PathVariable Long id) {
        SpecialistResponseDto specialist = specialistService.getSpecialistById(id);
        return ResponseEntity.ok(specialist);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('SPECIALIST')")
    public ResponseEntity<SpecialistResponseDto> getCurrentSpecialist(@AuthenticationPrincipal AppUser currentUser) {
        SpecialistResponseDto specialist = specialistService.getSpecialistById(currentUser.getId());
        return ResponseEntity.ok(specialist);
    }

    @GetMapping
    public ResponseEntity<List<SpecialistResponseDto>> getAllSpecialists() {
        List<SpecialistResponseDto> specialists = specialistService.getAllSpecialists();
        return ResponseEntity.ok(specialists);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SPECIALIST') or hasRole('ADMINISTRATOR')")
    public ResponseEntity<SpecialistResponseDto> updateSpecialist(
            @PathVariable Long id,
            @RequestBody SpecialistUpdateDto specialistUpdateDto,
            @AuthenticationPrincipal AppUser currentUser) {
        if (currentUser.getRole().equals("SPECIALIST") && !currentUser.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        SpecialistResponseDto updatedSpecialist = specialistService.updateSpecialist(id, specialistUpdateDto);
        return ResponseEntity.ok(updatedSpecialist);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Void> deleteSpecialist(@PathVariable Long id) {
        specialistService.deleteSpecialist(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<SpecialistResponseDto> deactivateSpecialist(@PathVariable Long id) {
        SpecialistResponseDto deactivatedSpecialist = specialistService.deactivateSpecialist(id);
        return ResponseEntity.ok(deactivatedSpecialist);
    }

    @PostMapping("/{id}/reactivate")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<SpecialistResponseDto> reactivateSpecialist(@PathVariable Long id) {
        SpecialistResponseDto reactivatedSpecialist = specialistService.reactivateSpecialist(id);
        return ResponseEntity.ok(reactivatedSpecialist);
    }

    @GetMapping("/{id}/appointments")
    @PreAuthorize("hasRole('SPECIALIST') or hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<AppointmentResponseDto>> getSpecialistAppointments(
            @PathVariable Long id,
            @AuthenticationPrincipal AppUser currentUser) {
        if (currentUser.getRole().equals("SPECIALIST") && !currentUser.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<AppointmentResponseDto> appointments = appointmentService.getSpecialistAppointments(id);
        return ResponseEntity.ok(appointments);
    }
}
