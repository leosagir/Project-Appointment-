package project.appointment.admin.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import project.appointment.admin.dto.AdminRequestDto;
import project.appointment.admin.dto.AdminResponseDto;
import project.appointment.admin.dto.AdminUpdateDto;
import project.appointment.admin.service.AdminServiceImpl;
import project.appointment.appointment.service.AppointmentServiceImpl;
import project.appointment.client.dto.ClientRequestDto;
import project.appointment.client.dto.ClientResponseDto;
import project.appointment.client.dto.ClientUpdateDto;
import project.appointment.client.service.ClientServiceImpl;
import project.appointment.notification.service.NotificationsServiceImpl;
import project.appointment.review.service.ReviewServiceImpl;
import project.appointment.services.service.SServiceServiceImpl;
import project.appointment.specialist.dto.SpecialistRequestDto;
import project.appointment.specialist.dto.SpecialistResponseDto;
import project.appointment.specialist.dto.SpecialistUpdateDto;
import project.appointment.specialist.service.SpecialistServiceImpl;
import project.appointment.specialization.service.SpecializationServiceImpl;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMINISTRATOR')")
@RequestMapping("/api/admin")
public class AdminController {
    private final ClientServiceImpl clientServiceImpl;
    private final AdminServiceImpl administratorService;
    private final AppointmentServiceImpl appointmentService;
    private final ClientServiceImpl clientService;
    private final NotificationsServiceImpl notificationsService;
    private final ReviewServiceImpl reviewService;
    private final SServiceServiceImpl serviceService;
    private final SpecialistServiceImpl specialistService;
    private final SpecializationServiceImpl specializationService;

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);


    @Transactional
    @PostMapping("/clients/register")
    public ClientResponseDto registerClient(@RequestBody ClientRequestDto clientRequestDto){
        try {
            return clientServiceImpl.registerClient(clientRequestDto);
        }catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Ошибка регистрации клиента",e);
        }
    }

    @Transactional
    @PostMapping("/admin/register")
    public AdminResponseDto registerAdmin(@RequestBody AdminRequestDto adminRequestDto){
        try {
            return administratorService.registerAdmin(adminRequestDto);
        }catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Ошибка регистрации администратора",e);
        }
    }

    @Transactional
    @PostMapping("/specialists/register")
    public SpecialistResponseDto registerSpecialist(@RequestBody SpecialistRequestDto specialistRequestDto){
        try {
            return specialistService.registerSpecialist(specialistRequestDto);
        }catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Ошибка регистрации специалиста",e);
        }
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<AdminResponseDto> updateAdmin(@PathVariable Long id, @RequestBody AdminUpdateDto updateDto) {
        logger.info("Updating Admin with id: {}", id);
        AdminResponseDto updatedAdmin = administratorService.updateAdmin(id, updateDto);
        logger.info("Admin with id: {} successfully updated", id);
        return ResponseEntity.ok(updatedAdmin);
    }

    @PatchMapping("/specialists/{id}")
    @Transactional
    public ResponseEntity<SpecialistResponseDto> updateSpecialist(@PathVariable Long id, @RequestBody SpecialistUpdateDto updateDto) {
        logger.info("Updating Specialist with id: {}", id);
        SpecialistResponseDto updatedSpecialist = specialistService.updateSpecialist(id,updateDto);
        logger.info("Specialist with id: {} successfully updated", id);
        return ResponseEntity.ok(updatedSpecialist);
    }

    @PatchMapping("/clients/{id}")
    @Transactional
    public ResponseEntity<ClientResponseDto> updateClient(@PathVariable Long id, @RequestBody ClientUpdateDto updateDto) {
        logger.info("Updating client with id: {}", id);
        ClientResponseDto updatedClient = clientService.updateClient(id,updateDto);
        logger.info("Client with id: {} successfully updated", id);
        return ResponseEntity.ok(updatedClient);
    }

    @PatchMapping("/clients/{id}/deactivate")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<ClientResponseDto> deactivateClient(@PathVariable Long id) {
        logger.info("Deactivating client with id: {}", id);
        ClientResponseDto deactivatedClient = clientService.deactivateClient(id);
        logger.info("Client with id: {} successfully deactivated", id);
        return ResponseEntity.ok(deactivatedClient);
    }

    @PatchMapping("/admin/{id}/deactivate")
    public ResponseEntity<AdminResponseDto> deactivateAdmin(@PathVariable Long id) {
        logger.info("Deactivating Admin with id: {}", id);
        AdminResponseDto deactivatedAdmin = administratorService.deactivateAdmin(id);
        logger.info("Admin with id: {} successfully deactivated", id);
        return ResponseEntity.ok(deactivatedAdmin);
    }

    @PatchMapping("/specialists/{id}/deactivate")
    public ResponseEntity<SpecialistResponseDto> deactivateSpecialist(@PathVariable Long id) {
        logger.info("Deactivating Specialist with id: {}", id);
        SpecialistResponseDto deactivatedSpecialist = specialistService.deactivateSpecialist(id);
        logger.info("Specialist with id: {} successfully deactivated", id);
        return ResponseEntity.ok(deactivatedSpecialist);
    }



}
