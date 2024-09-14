package project.appointment.specialist.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import project.appointment.appointment.entity.Appointment;
import project.appointment.client.entity.Status;
import project.appointment.review.entity.Review;
import project.appointment.ENUM.Role;
import project.appointment.security.AppUser;
import project.appointment.services.entity.SService;
import project.appointment.specialization.entity.Specialization;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "t_specialist")
@Inheritance(strategy = InheritanceType.JOINED)

public class Specialist implements AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    @Column(name = "email", unique = true,nullable = false)
    private String email;

    @NotBlank
    @Size(min = 6, max = 100, message = "Password must be at least 6 characters")
    @Column(name = "password",nullable = false)
    private String password;

    @NotBlank
    @Size(min = 2,max = 20)
    @Column(name = "first_name", length = 20,nullable = false)
    private String firstName;

    @NotBlank
    @Column(name = "last_name", length = 20,nullable = false)
    @Size(min = 2,max = 20)
    private String lastName;

    @NotNull
    @Past
    @Column(name = "date_of_birth",nullable = false)
    private LocalDate dateOfBirth;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "specialist_specialization",
            joinColumns = @JoinColumn(name = "specialist_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    private Set<Specialization> specializations;

    @ManyToMany(mappedBy = "specialists")
    private Set<SService> services;

    @Size(min = 3,max = 510)
    @Column(name = "description", length = 510,nullable = false)
    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank
    @Size(max=255)
    @Column(name = "address", length = 255,nullable = false)
    private String address;

    @Column(name = "phone", length = 15,nullable = false)
    @Size(min=7,max = 15)
    @Pattern(regexp = "^\\+?[0-9]{10,14}$",
            message = "Phone number must be 10-14 digits long and may start with a '+' symbol")
    @NotBlank
    private String phone;

    @NotNull
    @CreationTimestamp
    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "role",nullable = false)
    private Role role=Role.SPECIALIST;

    @OneToMany(mappedBy = "specialist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "specialist", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;


    public Specialist(Long id, String email, String firstName, String lastName, LocalDate dateOfBirth, Set<Specialization> specializations, Set<SService> services, String description, String address, String phone, Status status, Role role) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.specializations = specializations;
        this.services = services;
        this.description = description;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getRole() {
        return role.name();
    }
}
