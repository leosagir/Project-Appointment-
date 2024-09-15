package project.appointment.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.appointment.appointment.entity.Appointment;
import project.appointment.client.entity.Client;
import project.appointment.specialist.entity.Specialist;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findBySpecialistAndAppointmentStatus(Specialist specialist, Appointment.AppointmentStatus status);
    List<Appointment> findByClient(Client client);

}
