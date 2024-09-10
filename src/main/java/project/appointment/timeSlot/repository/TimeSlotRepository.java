package project.appointment.timeSlot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.appointment.timeSlot.entity.TimeSlot;
@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
}
