package project.appointment.specialist.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.appointment.client.entity.Client;
import project.appointment.specialist.entity.Specialist;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialistRepository extends JpaRepository<Specialist, Long> {
    Boolean existsByEmail(String email);
    Optional<Specialist> findByEmail(String email);
}
