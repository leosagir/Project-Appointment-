package project.appointment.client.service;

import project.appointment.client.dto.ClientRequestDto;
import project.appointment.client.dto.ClientResponseDto;
import project.appointment.client.dto.ClientUpdateDto;

import java.util.List;

public interface ClientService {
    ClientResponseDto registerClient(ClientRequestDto clientRequestDto);
    ClientResponseDto updateClient(Long id, ClientUpdateDto updateDto);
    ClientResponseDto deactivateClient(Long id);
    ClientResponseDto reactivateClient(Long id);
    ClientResponseDto getClientById(Long id);
    List<ClientResponseDto> getAllClients();
}


//import project.appointment.client.dto.ClientRequestDto;
//import project.appointment.client.dto.ClientResponseDto;
//import project.appointment.client.dto.ClientUpdateDto;
//
//import java.util.List;
//
//public interface ClientService {
//    ClientResponseDto registerClient(ClientRequestDto clientRequestDto);
//    ClientResponseDto updateClient(Long id, ClientUpdateDto updateDto);
//    ClientResponseDto deactivateClient(Long id);
//    ClientResponseDto getClientById(Long id);
//    List<ClientResponseDto> getAllClients();
//}
