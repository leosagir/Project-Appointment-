package project.appointment.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.appointment.admin.entity.Administrator;
import project.appointment.client.entity.Client;

import java.util.Optional;

public interface AdministratorRepository extends JpaRepository<Administrator,Long>{
    Optional<Administrator> findByEmail(String email);
}
