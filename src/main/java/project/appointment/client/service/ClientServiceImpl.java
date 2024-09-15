package project.appointment.client.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.appointment.ENUM.Role;
import project.appointment.client.dto.ClientRequestDto;
import project.appointment.client.dto.ClientResponseDto;
import project.appointment.client.dto.ClientUpdateDto;
import project.appointment.client.entity.Client;
import project.appointment.client.entity.Status;
import project.appointment.exception.EmailAlreadyExistsException;
import project.appointment.exception.RegistrationException;
import project.appointment.exception.ResourceNotFoundException;
import project.appointment.client.repository.ClientRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;

    @Override
    public ClientResponseDto getClientById(Long id) {
        logger.info("Fetching client with id: {}", id);
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Client not found with id: {}", id);
                    return new ResourceNotFoundException("Client not found with id: " + id);
                });
        logger.info("Client found with id: {}", id);
        return modelMapper.map(client, ClientResponseDto.class);
    }

    @Override
    public List<ClientResponseDto> getAllClients() {
        logger.info("Fetching all clients");
        List<Client> clients = clientRepository.findAll();
        logger.info("Found {} clients", clients.size());
        return clients.stream()
                .map(client -> modelMapper.map(client, ClientResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ClientResponseDto registerClient(ClientRequestDto clientRequestDto) {
        logger.info("Registering new client with email: {}", clientRequestDto.getEmail());
        if (clientRequestDto == null) {
            logger.error("ClientRequestDto is null");
            throw new IllegalArgumentException("ClientRequestDto must not be null");
        }

        validateClientRequest(clientRequestDto);

        if (clientRepository.existsByEmail(clientRequestDto.getEmail())) {
            logger.error("Email already exists: {}", clientRequestDto.getEmail());
            throw new EmailAlreadyExistsException("Email already registered");
        }

        Client client = modelMapper.map(clientRequestDto, Client.class);
        client.setPassword(passwordEncoder.encode(clientRequestDto.getPassword()));
        client.setCreatedAt(LocalDateTime.now());
        client.setRole(Role.CLIENT);
        client.setStatus(Status.ACTIVE);

        try {
            Client savedClient = clientRepository.save(client);
            logger.info("Client registered successfully with id: {}", savedClient.getId());
            return modelMapper.map(savedClient, ClientResponseDto.class);
        } catch (DataIntegrityViolationException e) {
            logger.error("Error during client registration", e);
            throw new RegistrationException("Error during client registration", e);
        }
    }

    @Override
    public ClientResponseDto updateClient(Long id, ClientUpdateDto updateDto) {
        logger.info("Updating client with id: {}", id);
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Client not found with id: {}", id);
                    return new ResourceNotFoundException("Client with id " + id + " not found");
                });

        updateClientFields(client, updateDto);

        Client updatedClient = clientRepository.save(client);
        logger.info("Client updated successfully with id: {}", updatedClient.getId());
        return modelMapper.map(updatedClient, ClientResponseDto.class);
    }

    @Override
    @Transactional
    public ClientResponseDto deactivateClient(Long id) {
        logger.info("Deactivating client with id: {}", id);
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Client not found with id: {}", id);
                    return new ResourceNotFoundException("Client with id " + id + " not found");
                });

        if (client.getStatus() == Status.INACTIVE) {
            logger.warn("Client with id: {} is already inactive", id);
            throw new IllegalStateException("Client is already inactive");
        }

        client.setStatus(Status.INACTIVE);

        Client updatedClient = clientRepository.save(client);
        logger.info("Client deactivated successfully with id: {}", updatedClient.getId());
        return modelMapper.map(updatedClient, ClientResponseDto.class);
    }

    private void validateClientRequest(ClientRequestDto clientRequestDto) {
        Set<ConstraintViolation<ClientRequestDto>> violations = validator.validate(clientRequestDto);
        if (!violations.isEmpty()) {
            logger.error("Validation failed for ClientRequestDto: {}", violations);
            throw new ConstraintViolationException(violations);
        }
    }

    private void updateClientFields(Client client, ClientUpdateDto updateDto) {
        if (updateDto.getFirstName() != null) {
            client.setFirstName(updateDto.getFirstName());
        }
        if (updateDto.getLastName() != null) {
            client.setLastName(updateDto.getLastName());
        }
        if (updateDto.getDateOfBirth() != null) {
            client.setDateOfBirth(updateDto.getDateOfBirth());
        }
        if (updateDto.getAddress() != null) {
            client.setAddress(updateDto.getAddress());
        }
        if (updateDto.getPhone() != null) {
            client.setPhone(updateDto.getPhone());
        }
    }
}





