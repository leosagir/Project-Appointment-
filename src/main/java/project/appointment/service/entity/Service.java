package project.appointment.service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import project.appointment.specialist.entity.Specialist;
import project.appointment.specialization.entity.Specialization;
import project.appointment.timeSlot.entity.TimeSlot;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @NotBlank
    @Size(min = 3, max = 1000)
    @Column(name = "description", length = 1000, nullable = false)
    private String description;

    @NotNull
    @Min(15)
    @Max(60)
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @NotBlank(message = "Price is required")
    @Column(name = "price", nullable = false)
    private String price;

    @CreationTimestamp
    @NotNull
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @NotNull
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
            name = "service_specialist",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "specialist_id")
    )
    private Set<Specialist> specialists;

    @ManyToOne
    @JoinColumn(name = "specialization_id", nullable = false)
    private Specialization specialization;

}
