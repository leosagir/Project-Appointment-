package project.appointment.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.appointment.client.entity.Client;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByLastNameAndFirstName(String lastName, String firstName);

    Boolean existsByEmail(String email);

    Optional<Client> findByEmail(String email);
}
