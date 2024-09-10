package project.appointment.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.appointment.service.entity.Service;
@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
}
