package project.appointment.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.appointment.appointment.dto.AppointmentRequestDto;
import project.appointment.appointment.dto.AppointmentResponseDto;
import project.appointment.appointment.service.AppointmentService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/appointments")
@PreAuthorize("hasRole('ADMINISTRATOR')")
@RequiredArgsConstructor
public class AdminAppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/specialist/{specialistId}")
    public ResponseEntity<AppointmentResponseDto> createFreeAppointment(
            @PathVariable Long specialistId,
            @RequestBody AppointmentRequestDto appointmentRequestDto) {
        return ResponseEntity.ok(appointmentService.createFreeAppointment(specialistId, appointmentRequestDto));
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponseDto> updateFreeAppointment(
            @PathVariable Long appointmentId,
            @RequestBody AppointmentRequestDto appointmentRequestDto) {
        return ResponseEntity.ok(appointmentService.updateFreeAppointment(appointmentId, appointmentRequestDto));
    }

    @DeleteMapping("/specialist/{specialistId}/appointment/{appointmentId}")
    public ResponseEntity<Void> deleteFreeAppointment(
            @PathVariable Long specialistId,
            @PathVariable Long appointmentId) {
        appointmentService.deleteFreeAppointment(specialistId, appointmentId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{appointmentId}/book/{clientId}")
    public ResponseEntity<AppointmentResponseDto> bookAppointment(
            @PathVariable Long appointmentId,
            @PathVariable Long clientId) {
        return ResponseEntity.ok(appointmentService.bookAppointment(clientId, appointmentId));
    }

    @PutMapping("/booked/{appointmentId}")
    public ResponseEntity<AppointmentResponseDto> updateBookedAppointment(
            @PathVariable Long appointmentId,
            @RequestBody AppointmentRequestDto appointmentRequestDto) {
        // Значение -1 позволит обойти проверку на принадлежность аппоинтмента клиенту в сервисе
        return ResponseEntity.ok(appointmentService.updateBookedAppointment(-1L, appointmentId, appointmentRequestDto));
    }

    @PostMapping("/{appointmentId}/cancel")
    public ResponseEntity<AppointmentResponseDto> cancelBookedAppointment(@PathVariable Long appointmentId) {
        // Значение -1 позволит обойти проверку на принадлежность аппоинтмента клиенту в сервисе
        return ResponseEntity.ok(appointmentService.cancelBookedAppointment(-1L, appointmentId));
    }

    @GetMapping("/specialist/{specialistId}/free")
    public ResponseEntity<List<AppointmentResponseDto>> getFreeAppointmentsForSpecialist(@PathVariable Long specialistId) {
        return ResponseEntity.ok(appointmentService.getFreeAppointmentsForSpecialist(specialistId));
    }

    @GetMapping("/specialist/{specialistId}/booked")
    public ResponseEntity<List<AppointmentResponseDto>> getBookedAppointmentsForSpecialist(@PathVariable Long specialistId) {
        return ResponseEntity.ok(appointmentService.getBookedAppointmentsForSpecialist(specialistId));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentsForClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsForClient(clientId));
    }
}
