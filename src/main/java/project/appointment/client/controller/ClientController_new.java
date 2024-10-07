package project.appointment.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.appointment.client.dto.ClientResponseDto;
import project.appointment.client.dto.ClientUpdateDto;
import project.appointment.client.service.ClientService;
import project.appointment.security.SecurityService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController_new {

    private final ClientService clientService;
    private final SecurityService securityService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or @securityService.isCurrentUser(#id)")
    public ResponseEntity<ClientResponseDto> getClientById(@PathVariable Long id) {
        ClientResponseDto client = clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("@securityService.isCurrentUser(#id)")
    public ResponseEntity<ClientResponseDto> updateClient(@PathVariable Long id, @Valid @RequestBody ClientUpdateDto updateDto) {
        ClientResponseDto updatedClient = clientService.updateClient(id, updateDto);
        return ResponseEntity.ok(updatedClient);
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDto> getCurrentClient() {
        Long currentUserId = securityService.getCurrentUserId();
        ClientResponseDto client = clientService.getClientById(currentUserId);
        return ResponseEntity.ok(client);
    }
}
