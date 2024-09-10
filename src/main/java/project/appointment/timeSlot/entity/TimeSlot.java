package project.appointment.timeSlot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialist_id", referencedColumnName = "id")
    @NotNull
    private Specialist specialist;

    @NotNull
    @Future
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @NotNull
    @Future
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @NotNull
    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = true;

}