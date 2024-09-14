package project.appointment.client.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.appointment.ENUM.Role;
import project.appointment.client.dto.ClientRequestDto;
import project.appointment.client.dto.ClientResponseDto;
import project.appointment.client.dto.ClientUpdateDto;
import project.appointment.client.entity.Client;
import project.appointment.client.entity.Status;
import project.appointment.client.exception.EmailAlreadyExistsException;
import project.appointment.client.exception.RegistrationException;
import project.appointment.client.exception.ResourceNotFoundException;
import project.appointment.client.repository.ClientRepository;

import java.time.LocalDateTime;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;


    @Override
    @Transactional
    public ClientResponseDto registerClient(ClientRequestDto clientRequestDto) {
        if (clientRequestDto == null) {
            throw new IllegalArgumentException("ClientRequestDto must not be null");
        }

        validateClientRequest(clientRequestDto);

        if (clientRepository.existsByEmail(clientRequestDto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered");
        }

        Client client = modelMapper.map(clientRequestDto, Client.class);
        client.setPassword(passwordEncoder.encode(clientRequestDto.getPassword()));
        client.setCreatedAt(LocalDateTime.now());
        client.setRole(Role.CLIENT);
        client.setStatus(Status.ACTIVE);

        try {
            Client savedClient = clientRepository.save(client);
            return modelMapper.map(savedClient, ClientResponseDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new RegistrationException("Error during client registration", e);
        }
    }

    @Override
    public ClientResponseDto updateClient(Long id, ClientUpdateDto updateDto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client with id " + id + " not found"));
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
        Client updatedClient = clientRepository.save(client);
        return modelMapper.map(updatedClient, ClientResponseDto.class);
    }

    @Override
    @Transactional
    public ClientResponseDto deactivateClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client with id " + id + " not found"));

        if (client.getStatus() == Status.INACTIVE) {
            throw new IllegalStateException("Client is already inactive");
        }

        client.setStatus(Status.INACTIVE);

        Client updatedClient = clientRepository.save(client);
        return modelMapper.map(updatedClient, ClientResponseDto.class);
    }


    private void validateClientRequest(ClientRequestDto clientRequestDto) {
        Set<ConstraintViolation<ClientRequestDto>> violations = validator.validate(clientRequestDto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}







