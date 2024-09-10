package project.appointment.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.appointment.appointment.entity.Appointment;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
