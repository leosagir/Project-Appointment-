package project.appointment.timeSlot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.appointment.specialist.entity.Specialist;
import project.appointment.timeSlot.entity.TimeSlot;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findBySpecialistAndStartTimeBetween(Specialist specialist, LocalDateTime startTime, LocalDateTime endTime);
}
