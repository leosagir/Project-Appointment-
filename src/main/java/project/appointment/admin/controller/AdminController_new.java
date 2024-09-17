package project.appointment.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.appointment.admin.dto.AdminRequestDto;
import project.appointment.admin.dto.AdminResponseDto;
import project.appointment.admin.dto.AdminUpdateDto;
import project.appointment.admin.service.AdminService;
import project.appointment.security.SecurityService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController_new {
    private final AdminService adminService;
    private final SecurityService securityService;

    @GetMapping("/my")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<AdminResponseDto> getCurrentAdmin() {
        Long currentUserId = securityService.getCurrentUserId();
        if (currentUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        AdminResponseDto admin = adminService.getAdminById(currentUserId);
        return ResponseEntity.ok(admin);
    }
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<AdminResponseDto> registerAdmin(@Valid @RequestBody AdminRequestDto adminRequestDto) {
        AdminResponseDto createdAdmin = adminService.registerAdmin(adminRequestDto);
        return new ResponseEntity<>(createdAdmin, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<AdminResponseDto> getAdminById(@PathVariable Long id) {
        AdminResponseDto admin = adminService.getAdminById(id);
        return ResponseEntity.ok(admin);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<List<AdminResponseDto>> getAllAdmins() {
        List<AdminResponseDto> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<AdminResponseDto> updateAdmin(@PathVariable Long id, @Valid @RequestBody AdminUpdateDto adminUpdateDto) {
        AdminResponseDto updatedAdmin = adminService.updateAdmin(id, adminUpdateDto);
        return ResponseEntity.ok(updatedAdmin);
    }

    @PostMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<AdminResponseDto> deactivateAdmin(@PathVariable Long id) {
        AdminResponseDto deactivatedAdmin = adminService.deactivateAdmin(id);
        return ResponseEntity.ok(deactivatedAdmin);
    }

    @PostMapping("/{id}/reactivate")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<AdminResponseDto> reactivateAdmin(@PathVariable Long id) {
        AdminResponseDto reactivatedAdmin = adminService.reactivateAdmin(id);
        return ResponseEntity.ok(reactivatedAdmin);
    }
}
