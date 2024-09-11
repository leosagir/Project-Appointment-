package project.appointment.timeSlot.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.appointment.appointment.entity.Appointment;
import project.appointment.specialist.entity.Specialist;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_time_slot")
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "specialist_id", nullable = false)
    private Specialist specialist;

    @NotNull
    @FutureOrPresent
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @NotNull
    @Future
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

   @Enumerated(EnumType.STRING)
    @Column(name = "time_slot_status")
    public TimeSlotStatus timeSlotStatus;

    public enum TimeSlotStatus {
        AVAILABLE,
        BOOKED
    }

}