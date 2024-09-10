package project.appointment.specialization.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.appointment.service.entity.Service;
import project.appointment.specialist.entity.Specialist;

import java.util.Set;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long specializationId;

    @Column(name = "specialization", unique = true)
    private String specialization;

    @ManyToMany(mappedBy = "specializations", fetch = FetchType.LAZY)
    private Set<Specialist> specialists;

    @OneToMany(mappedBy = "specialization", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Service> services;
}
