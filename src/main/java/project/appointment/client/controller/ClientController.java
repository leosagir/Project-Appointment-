package project.appointment.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.appointment.client.service.ClientService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;


}
