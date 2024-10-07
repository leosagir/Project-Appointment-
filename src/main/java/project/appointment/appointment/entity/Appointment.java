package project.appointment.appointment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import project.appointment.specialist.entity.Specialist;
import project.appointment.client.entity.Client;
import project.appointment.services.entity.Service;
import project.appointment.review.entity.Review;
import project.appointment.notification.entity.Notification;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "t_appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialist_id", nullable = false)
    @NotNull(message = "Specialist is required")
    private Specialist specialist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = true)
    private Service service;

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
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AppointmentStatus appointmentStatus;

    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Review review;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Notification> notifications = new HashSet<>();

    public enum AppointmentStatus {
        AVAILABLE, BOOKED, COMPLETED, CANCELLED
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//import project.appointment.specialist.dto.SpecialistShortDto;
//import project.appointment.specialist.entity.Specialist;
//import project.appointment.client.entity.Client;
//import java.time.LocalDateTime;
//
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "t_appointment")
//public class Appointment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name = "specialist_id")
//    @NotNull(message = "Specialist is required")
//    private Specialist specialist;
//
//    @NotNull(message = "Start time is required")
//    @Column(name = "start_time", nullable = false)
//    private LocalDateTime startTime;
//
//    @NotNull(message = "End time is required")
//    @Column(name = "end_time", nullable = false)
//    private LocalDateTime endTime;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "client_id", referencedColumnName = "id",nullable = false)
////    @NotNull(message = "Client is required")
//    private Client client;
//
//    @CreationTimestamp
//    @Column(name = "created_at", nullable = false, updatable = false)
//    private LocalDateTime createdAt;
//
//    @UpdateTimestamp
//    @Column(name = "updated_at", nullable = false)
//    private LocalDateTime updatedAt;
//
//    @NotNull(message = "Appointment status is required")
//    private AppointmentStatus appointmentStatus;
//
//
//    public enum AppointmentStatus{
//        AVAILABLE, BOOKED, COMPLETED, CANCELLED}
//}
//
//
