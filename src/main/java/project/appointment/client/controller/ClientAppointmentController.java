package project.appointment.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.appointment.appointment.dto.AppointmentRequestDto;
import project.appointment.appointment.dto.AppointmentResponseDto;
import project.appointment.appointment.service.AppointmentService;
import project.appointment.client.entity.Client;

import java.util.List;

@RestController
@RequestMapping("/api/client/appointments")
@PreAuthorize("hasRole('CLIENT')")
@RequiredArgsConstructor
public class ClientAppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/specialist/{specialistId}/free")
    public ResponseEntity<List<AppointmentResponseDto>> getFreeAppointmentsForSpecialist(@PathVariable Long specialistId) {
        return ResponseEntity.ok(appointmentService.getFreeAppointmentsForSpecialist(specialistId));
    }

    @PostMapping("/{appointmentId}/book")
    public ResponseEntity<AppointmentResponseDto> bookAppointment(
            @AuthenticationPrincipal Client client,
            @PathVariable Long appointmentId) {
        return ResponseEntity.ok(appointmentService.bookAppointment(client.getId(), appointmentId));
    }

    @PostMapping("/{appointmentId}/cancel")
    public ResponseEntity<AppointmentResponseDto> cancelBookedAppointment(
            @AuthenticationPrincipal Client client,
            @PathVariable Long appointmentId) {
        return ResponseEntity.ok(appointmentService.cancelBookedAppointment(client.getId(), appointmentId));
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponseDto> updateBookedAppointment(
            @AuthenticationPrincipal Client client,
            @PathVariable Long appointmentId,
            @RequestBody AppointmentRequestDto appointmentRequestDto) {
        return ResponseEntity.ok(appointmentService.updateBookedAppointment(client.getId(), appointmentId, appointmentRequestDto));
    }

    @GetMapping("/my")
    public ResponseEntity<List<AppointmentResponseDto>> getMyAppointments(@AuthenticationPrincipal Client client) {
        return ResponseEntity.ok(appointmentService.getAppointmentsForClient(client.getId()));
    }
}