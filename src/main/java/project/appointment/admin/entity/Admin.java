package project.appointment.admin.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import project.appointment.ENUM.Status;
import project.appointment.ENUM.Role;
import project.appointment.security.AppUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "t_admin")
@Inheritance(strategy = InheritanceType.JOINED)

public class Admin implements AppUser {

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

    @NotBlank
    @Size(max=255)
    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "phone", length = 15)
    @Size(min=7,max = 15)
    @Pattern(regexp = "^\\+?[0-9]{10,14}$",
            message = "Phone number must be 10-14 digits long and may start with a '+' symbol")
    @NotBlank
    private String phone;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role=Role.ADMINISTRATOR;

    public Admin(Long id, String email, String firstName, String lastName, LocalDate dateOfBirth, String address, String phone, Status status, Role role) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
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

