package project.appointment.client.service;

import org.springframework.web.bind.annotation.PathVariable;
import project.appointment.client.dto.ClientRequestDto;
import project.appointment.client.dto.ClientResponseDto;
import project.appointment.client.dto.ClientUpdateDto;
import project.appointment.client.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    ClientResponseDto registerClient(ClientRequestDto clientRequestDto);
    ClientResponseDto updateClient(Long id, ClientUpdateDto updateDto);
    ClientResponseDto deactivateClient(Long id);
}
