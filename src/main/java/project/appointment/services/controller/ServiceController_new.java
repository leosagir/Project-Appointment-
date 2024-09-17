package project.appointment.services.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.appointment.services.dto.ServiceRequestDto;
import project.appointment.services.dto.ServiceResponseDto;
import project.appointment.services.dto.ServiceUpdateDto;
import project.appointment.services.service.ServiceServiceImpl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
@Slf4j
public class ServiceController_new {

        private final ServiceServiceImpl serviceService;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<ServiceResponseDto> createService(@Valid @RequestBody ServiceRequestDto serviceRequestDto) {
        log.info("REST request to create Service : {}", serviceRequestDto);
        ServiceResponseDto result = serviceService.createService(serviceRequestDto);
        try {
            return ResponseEntity.created(new URI("/api/services/" + result.getId())).body(result);
        } catch (URISyntaxException e) {
            log.error("Failed to create URI for new service", e);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
    }

        @GetMapping("/{id}")
        public ResponseEntity<ServiceResponseDto> getService(@PathVariable Long id) {
            log.info("REST request to get Service : {}", id);
            ServiceResponseDto serviceDto = serviceService.getServiceById(id);
            return ResponseEntity.ok(serviceDto);
        }

        @GetMapping
        public ResponseEntity<List<ServiceResponseDto>> getAllServices() {
            log.info("REST request to get all Services");
            List<ServiceResponseDto> services = serviceService.getAllServices();
            return ResponseEntity.ok(services);
        }

        @PutMapping("/{id}")
        @PreAuthorize("hasRole('ADMINISTRATOR')")
        public ResponseEntity<ServiceResponseDto> updateService(@PathVariable Long id, @Valid @RequestBody ServiceUpdateDto serviceUpdateDto) {
            log.info("REST request to update Service : {}, {}", id, serviceUpdateDto);
            ServiceResponseDto result = serviceService.updateService(id, serviceUpdateDto);
            return ResponseEntity.ok(result);
        }

        @DeleteMapping("/{id}")
        @PreAuthorize("hasRole('ADMINISTRATOR')")
        public ResponseEntity<Void> deleteService(@PathVariable Long id) {
            log.info("REST request to delete Service : {}", id);
            serviceService.deleteService(id);
            return ResponseEntity.noContent().build();
        }
    }
