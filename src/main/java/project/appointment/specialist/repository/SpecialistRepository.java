package project.appointment.specialist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.appointment.client.entity.Client;
import project.appointment.specialist.entity.Specialist;

import java.util.Optional;

@Repository
public interface SpecialistRepository extends JpaRepository<Specialist, Long> {
    Optional<Specialist> findByEmail(String email);
}
