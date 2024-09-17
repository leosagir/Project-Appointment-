package project.appointment.specialization.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.appointment.services.entity.Service;
import project.appointment.specialist.entity.Specialist;

import java.util.HashSet;
import java.util.Set;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_specialization")
public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    @Column(name = "title", unique = true)
    private String title;

    @ManyToMany(mappedBy = "specializations")
    private Set<Specialist> specialists = new HashSet<>();

    @OneToMany(mappedBy = "specialization")
    private Set<Service> services = new HashSet<>();

}
