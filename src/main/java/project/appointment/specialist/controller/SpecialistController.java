package project.appointment.specialist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.appointment.client.dto.ClientResponseDto;
import project.appointment.specialist.dto.SpecialistResponseDto;
import project.appointment.specialist.service.SpecialistServiceImpl;
@RestController
@RequestMapping("/api/specialists")
@RequiredArgsConstructor
public class SpecialistController {
    private final SpecialistServiceImpl specialistService;



}
