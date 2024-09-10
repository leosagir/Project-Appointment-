package project.appointment.review.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import project.appointment.specialist.entity.Specialist;
import project.appointment.client.entity.Client;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "specialist_id")
    private Specialist specialist;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Client client;

    @Min(1)
    @Max(5)
    @NotNull
    @Column(name = "rating")
    private Integer rating;

    @Size(min = 3,max = 510)
    @Column(name = "comment", length = 510)
    private String comment;

    @CreationTimestamp
    @NotNull
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
