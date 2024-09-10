package project.appointment.client.service;

import project.appointment.client.entity.Client;

import java.util.List;

public interface ClientService {
    List<Client> getAllClient();
    Client getClientById(Long id);
    List<Client> getClientByLastNameFirstName(String lastName, String firstName);
    Client saveClient(Client client);
    Client deleteClient(Long id);
    Client updateClient(Client client);

}
