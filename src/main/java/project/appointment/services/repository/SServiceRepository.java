package project.appointment.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.appointment.services.entity.SService;
import project.appointment.specialization.entity.Specialization;

import java.util.Optional;

@Repository
public interface SServiceRepository extends JpaRepository<SService, Long> {
    Optional<SService> findByTitle(String title);
}
