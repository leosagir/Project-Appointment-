package project.appointment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.appointment.client.dto.ClientRequestDto;
import project.appointment.client.dto.ClientResponseDto;
import project.appointment.client.service.ClientService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
@Slf4j
public class PublicRegistrationController {

    private final ClientService clientService;

    @PostMapping("/client/register")
    public ResponseEntity<ClientResponseDto> registerClient(@Valid @RequestBody ClientRequestDto clientRequestDto) {
        log.info("Received registration request for client: {}", clientRequestDto.getEmail());
        ClientResponseDto response = clientService.registerClient(clientRequestDto);
        log.info("Client registered successfully: {}", response.getEmail());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
