package project.appointment.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.appointment.client.dto.ClientResponseDto;
import project.appointment.client.dto.ClientUpdateDto;
import project.appointment.client.service.ClientService;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

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
