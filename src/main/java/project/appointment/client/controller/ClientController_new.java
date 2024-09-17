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



//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import project.appointment.client.dto.ClientResponseDto;
//import project.appointment.client.dto.ClientUpdateDto;
//import project.appointment.client.service.ClientService;
//
//@RestController
//@RequestMapping("/api/clients")
//@RequiredArgsConstructor
//@Slf4j
//public class ClientController {
//    private final ClientService clientService;
//
//    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('ADMINISTRATOR') or @securityService.isCurrentUser(#id)")
//    public ResponseEntity<ClientResponseDto> getClientById(@PathVariable Long id) {
//        log.info("Received request to get client with id: {}", id);
//        ClientResponseDto client = clientService.getClientById(id);
//        return ResponseEntity.ok(client);
//    }
//
//    @PatchMapping("/{id}")
//    @PreAuthorize("hasRole('ADMINISTRATOR') or @securityService.isCurrentUser(#id)")
//    public ResponseEntity<ClientResponseDto> updateClient(@PathVariable Long id, @Valid @RequestBody ClientUpdateDto updateDto) {
//        log.info("Received request to update client with id: {}", id);
//        ClientResponseDto updatedClient = clientService.updateClient(id, updateDto);
//        return ResponseEntity.ok(updatedClient);
//    }
//
//    @PostMapping("/{id}/deactivate")
//    @PreAuthorize("hasRole('ADMINISTRATOR')")
//    public ResponseEntity<ClientResponseDto> deactivateClient(@PathVariable Long id) {
//        log.info("Received request to deactivate client with id: {}", id);
//        ClientResponseDto deactivatedClient = clientService.deactivateClient(id);
//        return ResponseEntity.ok(deactivatedClient);
//    }
//
//    @PostMapping("/{id}/reactivate")
//    @PreAuthorize("hasRole('ADMINISTRATOR')")
//    public ResponseEntity<ClientResponseDto> reactivateClient(@PathVariable Long id) {
//        log.info("Received request to reactivate client with id: {}", id);
//        ClientResponseDto reactivatedClient = clientService.reactivateClient(id);
//        return ResponseEntity.ok(reactivatedClient);
//    }
//}


