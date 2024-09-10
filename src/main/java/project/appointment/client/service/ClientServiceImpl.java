package project.appointment.client.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.appointment.client.entity.Client;
import project.appointment.client.exception.ClientNotFoundException;
import project.appointment.client.repository.ClientRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Client> getAllClient() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(()-> new ClientNotFoundException("Client with id" + id + " not found"));
    }

    @Override
    public List<Client> getClientByLastNameFirstName(String lastName, String firstName) {
        return clientRepository.findByLastNameAndFirstName(lastName,firstName);
    }

    @Override
    @Transactional
    public Client saveClient(@Valid @NotNull Client client) {
        try{
            return clientRepository.save(client);
        }catch (Exception e){
            throw new RuntimeException("Failed to save user", e);
        }
    }

    @Override
    public Client deleteClient(Long id) {
        return clientRepository.findById(id)
                .map(user->{
                    clientRepository.delete(user);
                    return user;
                })
                .orElseThrow(()-> new ClientNotFoundException("Client with id" + id + " not found"));
    }

    @Override
    @Transactional
    public Client updateClient(Client client) {
        Client updateUser=clientRepository.findById(client.getId())
                .orElseThrow(()->new ClientNotFoundException("User with id" + client.getId() + " not found"));

        updateUser.setPassword(client.getPassword());
        updateUser.setEmail(client.getEmail());
        updateUser.setFirstName(client.getFirstName());
        updateUser.setLastName(client.getLastName());
        updateUser.setPhone(client.getPhone());

        updateUser.setStatus(client.getStatus());
        updateUser.setReviews(client.getReviews());
        updateUser.setAppointments(client.getAppointments());
        updateUser.setNotifications(client.getNotifications());
        return clientRepository.save(updateUser);
    }

        public Client save(Client client) {
            client.setPassword(passwordEncoder.encode(client.getPassword()));
            return clientRepository.save(client);
        }

        public Optional<Client> findByEmail(String email) {
            return clientRepository.findByEmail(email);
        }

        public Boolean existsByEmail(String email) {
            return clientRepository.existsByEmail(email);
        }

        public List<Client> findAll() {
            return clientRepository.findAll();
        }

        public Optional<Client> findById(Long id) {
            return clientRepository.findById(id);
        }

        public void deleteById(Long id) {
            clientRepository.deleteById(id);
        }
    }





