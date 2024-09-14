package project.appointment.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.appointment.admin.entity.Admin;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Long>{
    Optional<Admin> findByEmail(String email);
    Boolean existsByEmail(String email);
}
