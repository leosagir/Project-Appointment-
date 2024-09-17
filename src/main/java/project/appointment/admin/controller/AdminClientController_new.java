package project.appointment.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.appointment.client.dto.ClientRequestDto;
import project.appointment.client.dto.ClientResponseDto;
import project.appointment.client.dto.ClientUpdateDto;
import project.appointment.client.service.ClientService;

import jakarta.validation.Valid;
import java.util.List;

    @RestController
    @RequestMapping("/api/admin")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @RequiredArgsConstructor
    public class AdminClientController_new {

        private final ClientService clientService;


        @GetMapping("/clients")

        public ResponseEntity<List<ClientResponseDto>> getAllClients() {
            List<ClientResponseDto> clients = clientService.getAllClients();
            return ResponseEntity.ok(clients);
        }

        @GetMapping("/clients/{id}")
        public ResponseEntity<ClientResponseDto> getClientById(@PathVariable Long id) {
            ClientResponseDto client = clientService.getClientById(id);
            return ResponseEntity.ok(client);
        }

        @PostMapping("/clients")
        public ResponseEntity<ClientResponseDto> createClient(@Valid @RequestBody ClientRequestDto clientRequestDto) {
            ClientResponseDto createdClient = clientService.registerClient(clientRequestDto);
            return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
        }

        @PutMapping("/clients/{id}")
        public ResponseEntity<ClientResponseDto> updateClient(@PathVariable Long id, @Valid @RequestBody ClientUpdateDto updateDto) {
            ClientResponseDto updatedClient = clientService.updateClient(id, updateDto);
            return ResponseEntity.ok(updatedClient);
        }

        @PostMapping("/clients/{id}/deactivate")
        public ResponseEntity<ClientResponseDto> deactivateClient(@PathVariable Long id) {
            ClientResponseDto deactivatedClient = clientService.deactivateClient(id);
            return ResponseEntity.ok(deactivatedClient);
        }

        @PostMapping("/clients/{id}/reactivate")
        public ResponseEntity<ClientResponseDto> reactivateClient(@PathVariable Long id) {
            ClientResponseDto reactivatedClient = clientService.reactivateClient(id);
            return ResponseEntity.ok(reactivatedClient);
        }
    }
