package project.appointment.appointment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.appointment.appointment.dto.*;
import project.appointment.appointment.entity.Appointment;
import project.appointment.appointment.service.AppointmentService;
import project.appointment.security.AppUser;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController_new {

    private final AppointmentService appointmentService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointments() {
        List<AppointmentResponseDto> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<AppointmentResponseDto> createFreeAppointment(@RequestBody AppointmentCreateDto appointmentCreateDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createFreeAppointment(appointmentCreateDto));
    }

    @PutMapping("/{id}/book")
    @PreAuthorize("hasAnyRole('CLIENT', 'SPECIALIST', 'ADMINISTRATOR')")
    public ResponseEntity<AppointmentResponseDto> bookAppointment(@PathVariable Long id,
                                                                  @RequestBody AppointmentBookDto appointmentBookDto,
                                                                  @AuthenticationPrincipal AppUser currentUser) {
        if (currentUser.getRole().equals("CLIENT")) {
            appointmentBookDto.setClientId(currentUser.getId());
        }
        return ResponseEntity.ok(appointmentService.bookAppointment(id, appointmentBookDto));
    }

    @PutMapping("/{id}/cancel-booking")
    @PreAuthorize("hasAnyRole('CLIENT', 'SPECIALIST', 'ADMINISTRATOR')")
    public ResponseEntity<AppointmentResponseDto> cancelBooking(@PathVariable Long id,
                                                                @AuthenticationPrincipal AppUser currentUser) {
        AppointmentResponseDto existingAppointment = appointmentService.getAppointmentById(id);
        if (currentUser.getRole().equals("CLIENT") && !existingAppointment.getClientId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (currentUser.getRole().equals("SPECIALIST") && !existingAppointment.getSpecialistId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(appointmentService.cancelBooking(id));
    }

    @PutMapping("/{id}/cancel-client")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<AppointmentResponseDto> cancelClientAppointment(@PathVariable Long id,
                                                                          @AuthenticationPrincipal AppUser currentUser) {
        AppointmentResponseDto existingAppointment = appointmentService.getAppointmentById(id);

        if (!existingAppointment.getClientId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(appointmentService.cancelClientAppointment(id));
    }

    @GetMapping("/client/past-without-review")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<AppointmentResponseDto>> getClientPastAppointmentsWithoutReview(
            @AuthenticationPrincipal AppUser currentUser) {
        List<AppointmentResponseDto> appointments = appointmentService.getClientPastAppointmentsWithoutReview(currentUser.getId());
        return ResponseEntity.ok(appointments);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SPECIALIST', 'ADMINISTRATOR')")
    public ResponseEntity<Void> deleteFreeAppointment(@PathVariable Long id,
                                                      @AuthenticationPrincipal AppUser currentUser) {
        AppointmentResponseDto existingAppointment = appointmentService.getAppointmentById(id);
        if (existingAppointment.getAppointmentStatus() != Appointment.AppointmentStatus.AVAILABLE) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (currentUser.getRole().equals("SPECIALIST") && !existingAppointment.getSpecialistId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/free")
    public ResponseEntity<List<AppointmentResponseDto>> getFreeAppointments(@RequestParam Long specialistId) {
        return ResponseEntity.ok(appointmentService.getFreeAppointments(specialistId));
    }

    @GetMapping("/booked")
    @PreAuthorize("hasAnyRole('CLIENT', 'SPECIALIST', 'ADMINISTRATOR')")
    public ResponseEntity<List<AppointmentResponseDto>> getBookedAppointments(@RequestParam Long specialistId,
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
    public ResponseEntity<AppointmentResponseDto> getAppointment(@PathVariable Long id,
                                                                 @AuthenticationPrincipal AppUser currentUser) {
        AppointmentResponseDto appointment = appointmentService.getAppointmentById(id);
        if (currentUser.getRole().equals("CLIENT") && !appointment.getClientId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (currentUser.getRole().equals("SPECIALIST") && !appointment.getSpecialistId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(appointment);
    }

    @GetMapping("/specialist/{specialistId}")
    @PreAuthorize("hasAnyRole('SPECIALIST', 'ADMINISTRATOR')")
    public ResponseEntity<List<AppointmentResponseDto>> getSpecialistAppointments(
            @PathVariable Long specialistId,
            @AuthenticationPrincipal AppUser currentUser) {
        if (currentUser.getRole().equals("SPECIALIST") && !currentUser.getId().equals(specialistId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(appointmentService.getSpecialistAppointments(specialistId));
    }

    @GetMapping("/client")
    @PreAuthorize("hasAnyRole('CLIENT', 'ADMINISTRATOR')")
    public ResponseEntity<List<AppointmentResponseDto>> getClientAppointments(
            @RequestParam(required = false) Long clientId,
            @AuthenticationPrincipal AppUser currentUser) {

        Long targetClientId = clientId;
        if (currentUser.getRole().equals("CLIENT")) {
            targetClientId = currentUser.getId();
        } else if (currentUser.getRole().equals("ADMINISTRATOR") && clientId == null) {
            return ResponseEntity.badRequest().build();
        }

        List<AppointmentResponseDto> appointments = appointmentService.getClientAppointments(targetClientId);
        return ResponseEntity.ok(appointments);
    }
}