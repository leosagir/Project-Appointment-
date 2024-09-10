package project.appointment.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface AppUser extends UserDetails {
    Long getId();
    String getEmail();
    String getRole();
}
