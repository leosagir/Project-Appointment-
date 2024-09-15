package project.appointment.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.appointment.services.dto.SServiceRequestDto;
import project.appointment.services.dto.SServiceResponseDto;
import project.appointment.services.service.SServiceService;

import jakarta.validation.Valid;
import project.appointment.services.service.SServiceServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/admin/services")
@PreAuthorize("hasRole('ADMINISTRATOR')")
@RequiredArgsConstructor
public class AdminServiceController {

    private final SServiceServiceImpl serviceService;

    @PostMapping
    public ResponseEntity<SServiceResponseDto> createService(@Valid @RequestBody SServiceRequestDto requestDto) {
        SServiceResponseDto responseDto = serviceService.createService(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SServiceResponseDto> getService(@PathVariable Long id) {
        SServiceResponseDto responseDto = serviceService.getServiceById(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<SServiceResponseDto>> getAllServices() {
        List<SServiceResponseDto> services = serviceService.getAllServices();
        return ResponseEntity.ok(services);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SServiceResponseDto> updateService(@PathVariable Long id,
                                                             @RequestBody SServiceRequestDto requestDto) {
        SServiceResponseDto responseDto = serviceService.updateService(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
