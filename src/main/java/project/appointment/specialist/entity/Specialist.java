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
import project.appointment.service.entity.Service;
import project.appointment.specialization.entity.Specialization;
import project.appointment.timeSlot.entity.TimeSlot;

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
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank
    @Size(min = 6, max = 100, message = "Password must be at least 6 characters")
    @Column(name = "password")
    private String password;

    @NotBlank
    @Size(min = 2,max = 20)
    @Column(name = "first_name", length = 20)
    private String firstName;

    @NotBlank
    @Column(name = "last_name", length = 20)
    @Size(min = 2,max = 20)
    private String lastName;

    @NotNull
    @Past
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "specialist_specialization",
            joinColumns = @JoinColumn(name = "specialist_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    private Set<Specialization> specializations;

    @ManyToMany(mappedBy = "specialists")
    private Set<Service> services;

    @Size(min = 3,max = 510)
    @Column(name = "description", length = 510)
    @NotBlank(message = "Description is required")
    private String description;

    @Min(1)
    @Max(50)
    @Column(name = "experience")
    @NotNull(message = "Experience is required")
    private Integer experience;

    @NotBlank
    @Size(max=255)
    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "phone", length = 15)
    @Size(min=7,max = 15)
    @Pattern(regexp = "^\\+?([0-9]{10}|[0-9]{3}[- .]?[0-9]{3}[- .]?[0-9]{4})$")
    @NotBlank
    private String phone;

    @NotNull
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role=Role.SPECIALIST;

    @OneToMany(mappedBy = "specialist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "specialist", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @OneToMany(mappedBy = "specialist", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<TimeSlot> timeSlots = new ArrayList<>();

    public Specialist(Long id, String email, String firstName, String lastName, LocalDate dateOfBirth, Set<Specialization> specializations, Set<Service> services, String description, Integer experience, String address, String phone, Status status, Role role) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.specializations = specializations;
        this.services = services;
        this.description = description;
        this.experience = experience;
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
