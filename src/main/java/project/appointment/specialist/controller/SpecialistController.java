package project.appointment.specialist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.appointment.client.dto.ClientResponseDto;
import project.appointment.exception.ResourceNotFoundException;
import project.appointment.specialist.dto.SpecialistResponseDto;
import project.appointment.specialist.entity.Specialist;
import project.appointment.specialist.service.SpecialistServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/specialists")
@RequiredArgsConstructor
public class SpecialistController {
    private final SpecialistServiceImpl specialistService;

    @GetMapping("/{id}")
    public ResponseEntity<SpecialistResponseDto> getSpecialistById(@PathVariable Long id) {
        SpecialistResponseDto specialist = specialistService.getSpecialistById(id);
        return ResponseEntity.ok(specialist);
    }

    @GetMapping
    public ResponseEntity<List<SpecialistResponseDto>> getAllSpecialists() {
        List<SpecialistResponseDto> specialists = specialistService.getAllSpecialists();
        return ResponseEntity.ok(specialists);
    }

}
