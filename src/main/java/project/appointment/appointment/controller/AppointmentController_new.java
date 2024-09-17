package project.appointment.appointment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.appointment.appointment.dto.*;
import project.appointment.appointment.service.AppointmentService;
import project.appointment.security.AppUser;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController_new {

    private final AppointmentService appointmentService;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<AppointmentDto> createAppointment(@RequestBody AppointmentCreateDto appointmentCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createFreeAppointment(appointmentCreateDto));
    }

    @PutMapping("/{id}/book")
    @PreAuthorize("hasAnyRole('CLIENT', 'SPECIALIST', 'ADMINISTRATOR')")
    public ResponseEntity<AppointmentDto> bookAppointment(@PathVariable Long id,
                                                          @RequestBody AppointmentBookDto appointmentBookDto,
                                                          @AuthenticationPrincipal AppUser currentUser) {
        if (currentUser.getRole().equals("CLIENT")) {
            appointmentBookDto.setClientId(currentUser.getId());
        }
        return ResponseEntity.ok(appointmentService.bookAppointment(id, appointmentBookDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENT', 'SPECIALIST', 'ADMINISTRATOR')")
    public ResponseEntity<AppointmentDto> updateAppointment(@PathVariable Long id,
                                                            @RequestBody AppointmentUpdateDto appointmentUpdateDto,
                                                            @AuthenticationPrincipal AppUser currentUser) {
        AppointmentDto existingAppointment = appointmentService.getAppointmentById(id);
        if (currentUser.getRole().equals("CLIENT") && !existingAppointment.getClientId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (currentUser.getRole().equals("SPECIALIST") && !existingAppointment.getSpecialistId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(appointmentService.updateAppointment(id, appointmentUpdateDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENT', 'SPECIALIST', 'ADMINISTRATOR')")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id,
                                                  @AuthenticationPrincipal AppUser currentUser) {
        AppointmentDto existingAppointment = appointmentService.getAppointmentById(id);
        if (currentUser.getRole().equals("CLIENT") && !existingAppointment.getClientId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (currentUser.getRole().equals("SPECIALIST") && !existingAppointment.getSpecialistId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        appointmentService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/free")
    public ResponseEntity<List<AppointmentDto>> getFreeAppointments(@RequestParam Long specialistId) {
        return ResponseEntity.ok(appointmentService.getFreeAppointments(specialistId));
    }

    @GetMapping("/booked")
    @PreAuthorize("hasAnyRole('CLIENT', 'SPECIALIST', 'ADMINISTRATOR')")
    public ResponseEntity<List<AppointmentDto>> getBookedAppointments(@RequestParam Long specialistId,
                                                                      @AuthenticationPrincipal AppUser currentUser) {
        if (currentUser.getRole().equals("CLIENT") || currentUser.getRole().equals("SPECIALIST")) {
            if (!currentUser.getId().equals(specialistId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        return ResponseEntity.ok(appointmentService.getBookedAppointments(specialistId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENT', 'SPECIALIST', 'ADMINISTRATOR')")
    public ResponseEntity<AppointmentDto> getAppointment(@PathVariable Long id,
                                                         @AuthenticationPrincipal AppUser currentUser) {
        AppointmentDto appointment = appointmentService.getAppointmentById(id);
        if (currentUser.getRole().equals("CLIENT") && !appointment.getClientId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (currentUser.getRole().equals("SPECIALIST") && !appointment.getSpecialistId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(appointment);
    }
}
