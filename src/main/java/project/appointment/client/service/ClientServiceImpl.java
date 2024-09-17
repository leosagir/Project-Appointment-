package project.appointment.client.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.appointment.admin.entity.Admin;
import project.appointment.client.ClientMapper;
import project.appointment.client.dto.ClientRequestDto;
import project.appointment.client.dto.ClientResponseDto;
import project.appointment.client.dto.ClientUpdateDto;
import project.appointment.client.entity.Client;
import project.appointment.client.entity.Status;
import project.appointment.client.repository.ClientRepository;
import project.appointment.exception.EmailAlreadyExistsException;
import project.appointment.exception.ResourceNotFoundException;
import project.appointment.specialist.entity.Specialist;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;

    @Override
    @Transactional
    public ClientResponseDto registerClient(ClientRequestDto clientRequestDto) {
        log.info("Attempting to register new client with email: {}", clientRequestDto.getEmail());
        validateClientRequest(clientRequestDto);

        if (clientRepository.existsByEmail(clientRequestDto.getEmail())) {
            log.warn("Registration failed: Email already exists: {}", clientRequestDto.getEmail());
            throw new EmailAlreadyExistsException("Email already registered");
        }

        Client client = clientMapper.clientRequestDtoToClient(clientRequestDto);
        client.setPassword(passwordEncoder.encode(clientRequestDto.getPassword()));
        Client savedClient = clientRepository.save(client);

        log.info("Client registered successfully with id: {}", savedClient.getId());
        return clientMapper.clientToClientResponseDto(savedClient);
    }

    @Override
    @Transactional
    public ClientResponseDto updateClient(Long id, ClientUpdateDto updateDto) {
        log.info("Attempting to update client with id: {}", id);
        validateClientUpdateRequest(updateDto);

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Update failed: Client not found with id: {}", id);
                    return new ResourceNotFoundException("Client not found with id: " + id);
                });

        clientMapper.updateClientFromDto(updateDto, client);
        Client updatedClient = clientRepository.save(client);

        log.info("Client updated successfully with id: {}", updatedClient.getId());
        return clientMapper.clientToClientResponseDto(updatedClient);
    }

    @Override
    @Transactional
    public ClientResponseDto deactivateClient(Long id) {
        log.info("Attempting to deactivate client with id: {}", id);
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Deactivation failed: Client not found with id: {}", id);
                    return new ResourceNotFoundException("Client not found with id: " + id);
                });

        if (client.getStatus() == Status.INACTIVE) {
            log.warn("Client with id: {} is already inactive", id);
            throw new IllegalStateException("Client is already inactive");
        }

        client.setStatus(Status.INACTIVE);
        Client deactivatedClient = clientRepository.save(client);

        log.info("Client deactivated successfully with id: {}", deactivatedClient.getId());
        return clientMapper.clientToClientResponseDto(deactivatedClient);
    }

    @Override
    @Transactional
    public ClientResponseDto reactivateClient(Long id) {
        log.info("Attempting to reactivate client with id: {}", id);
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Reactivation failed: Client not found with id: {}", id);
                    return new ResourceNotFoundException("Client not found with id: " + id);
                });

        if (client.getStatus() == Status.ACTIVE) {
            log.warn("Client with id: {} is already active", id);
            throw new IllegalStateException("Client is already active");
        }

        client.setStatus(Status.ACTIVE);
        Client reactivatedClient = clientRepository.save(client);

        log.info("Client reactivated successfully with id: {}", reactivatedClient.getId());
        return clientMapper.clientToClientResponseDto(reactivatedClient);
    }

    @Override
    @Transactional
    public ClientResponseDto getClientById(Long id) {
        log.info("Fetching client with id: {}", id);
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Fetch failed: Client not found with id: {}", id);
                    return new ResourceNotFoundException("Client not found with id: " + id);
                });

        return clientMapper.clientToClientResponseDto(client);
    }

    @Override
    @Transactional
    public List<ClientResponseDto> getAllClients() {
        log.info("Fetching all clients");
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(clientMapper::clientToClientResponseDto)
                .collect(Collectors.toList());
    }

    private void validateClientRequest(ClientRequestDto clientRequestDto) {
        Set<ConstraintViolation<ClientRequestDto>> violations = validator.validate(clientRequestDto);
        if (!violations.isEmpty()) {
            log.error("Validation failed for ClientRequestDto: {}", violations);
            throw new ConstraintViolationException(violations);
        }
    }

    private void validateClientUpdateRequest(ClientUpdateDto clientUpdateDto) {
        Set<ConstraintViolation<ClientUpdateDto>> violations = validator.validate(clientUpdateDto);
        if (!violations.isEmpty()) {
            log.error("Validation failed for ClientUpdateDto: {}", violations);
            throw new ConstraintViolationException(violations);
        }
    }
}



