package project.appointment.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.appointment.appointment.entity.Appointment;
import project.appointment.client.entity.Client;
import project.appointment.specialist.entity.Specialist;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findBySpecialistAndAppointmentStatus(Specialist specialist, Appointment.AppointmentStatus status);
    List<Appointment> findByClient(Client client);
    List<Appointment> findBySpecialist(Specialist specialist);
    List<Appointment> findByEndTimeBefore(LocalDateTime dateTime);
    List<Appointment> findByClientAndEndTimeBeforeAndAppointmentStatus(Client client, LocalDateTime endTime, Appointment.AppointmentStatus status);
}
