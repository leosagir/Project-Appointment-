package project.appointment.specialization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.appointment.specialization.entity.Specialization;

import java.util.Optional;

public interface SpecializationRepository extends JpaRepository <Specialization,Long>{
    Optional<Specialization> findByTitle(String title);
}

