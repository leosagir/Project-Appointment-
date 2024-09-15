package project.appointment.specialist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.appointment.appointment.dto.AppointmentResponseDto;
import project.appointment.appointment.service.AppointmentService;
import project.appointment.specialist.entity.Specialist;

import java.util.List;

@RestController
@RequestMapping("/api/specialist/appointments")
@PreAuthorize("hasRole('SPECIALIST')")
@RequiredArgsConstructor
public class SpecialistAppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/my/free")
    public ResponseEntity<List<AppointmentResponseDto>> getMyFreeAppointments(@AuthenticationPrincipal Specialist specialist) {
        return ResponseEntity.ok(appointmentService.getFreeAppointmentsForSpecialist(specialist.getId()));
    }

    @GetMapping("/my/booked")
    public ResponseEntity<List<AppointmentResponseDto>> getMyBookedAppointments(@AuthenticationPrincipal Specialist specialist) {
        return ResponseEntity.ok(appointmentService.getBookedAppointmentsForSpecialist(specialist.getId()));
    }

    @GetMapping("/specialist/{specialistId}/free")
    public ResponseEntity<List<AppointmentResponseDto>> getFreeAppointmentsForSpecialist(@PathVariable Long specialistId) {
        return ResponseEntity.ok(appointmentService.getFreeAppointmentsForSpecialist(specialistId));
    }
}
