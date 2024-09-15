package project.appointment.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.appointment.client.dto.ClientResponseDto;
import project.appointment.client.dto.ClientUpdateDto;
import project.appointment.client.service.ClientService;
import org.springframework.security.access.prepost.PreAuthorize;
import project.appointment.security.SecurityService;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final SecurityService securityService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or @securityService.isCurrentUser(#id)")
    public ResponseEntity<ClientResponseDto> getClientById(@PathVariable Long id) {
        ClientResponseDto client = clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientResponseDto> updateClient(@PathVariable Long id, @RequestBody ClientUpdateDto updateDto) {
        ClientResponseDto updatedClient = clientService.updateClient(id,updateDto);
        return ResponseEntity.ok(updatedClient);
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<ClientResponseDto> deactivateClient(@PathVariable Long id) {
        ClientResponseDto deactivatedClient = clientService.deactivateClient(id);
        return ResponseEntity.ok(deactivatedClient);
    }




}
