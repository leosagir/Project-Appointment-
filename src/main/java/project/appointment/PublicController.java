package project.appointment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.appointment.appointment.dto.AppointmentResponseDto;
import project.appointment.appointment.service.AppointmentService;
import project.appointment.client.dto.ClientRequestDto;
import project.appointment.client.dto.ClientResponseDto;
import project.appointment.client.service.ClientService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
@Slf4j
public class PublicController {

    private final ClientService clientService;
    private final AppointmentService appointmentService;

    @PostMapping("/client/register")
    public ResponseEntity<ClientResponseDto> registerClient(@Valid @RequestBody ClientRequestDto clientRequestDto) {
        log.info("Received registration request for client: {}", clientRequestDto.getEmail());
        ClientResponseDto response = clientService.registerClient(clientRequestDto);
        log.info("Client registered successfully: {}", response.getEmail());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/appointments/specialist/{specialistId}/free")
    public ResponseEntity<List<AppointmentResponseDto>> getFreeAppointmentsForSpecialist(@PathVariable Long specialistId) {
        return ResponseEntity.ok(appointmentService.getFreeAppointmentsForSpecialist(specialistId));
    }
}
