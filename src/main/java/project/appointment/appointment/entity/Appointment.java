package project.appointment.appointment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import project.appointment.specialist.entity.Specialist;
import project.appointment.client.entity.Client;
import project.appointment.timeSlot.entity.TimeSlot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id",nullable = false)
    @NotNull(message = "Client is required")
    private Client client;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "specialist_id")
    @NotNull(message = "Specialist is required")
    private Specialist specialist;

    @NotNull(message = "Start time is required")
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @NotNull(message = "Appointment status is required")
    private AppointmentStatus appointmentStatus;

    @NotNull(message = "Appointment type is required")
    @Enumerated(EnumType.STRING)
    public AppointmentType appointmentType;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TimeSlot> timeSlots = new ArrayList<>();

    public enum AppointmentType {
        INITIAL(30), FOLLOW_UP(60);

        private final int durationMinutes;

        AppointmentType(int durationMinutes) {
            this.durationMinutes = durationMinutes;
        }

        public int getDurationMinutes() {
            return durationMinutes;
        }
    }
    public enum AppointmentStatus{
        SCHEDULED,COMPLETED,CANCELLED}
}


