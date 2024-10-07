package project.appointment.security;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.appointment.admin.repository.AdminRepository;
import project.appointment.client.repository.ClientRepository;
import project.appointment.specialist.repository.SpecialistRepository;

@Service
@RequiredArgsConstructor
public class CompositeUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CompositeUserDetailsService.class);

    private final ClientRepository clientRepository;
    private final SpecialistRepository specialistRepository;
    private final AdminRepository administratorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Attempting to load user by username: {}", username);

        AppUser user = clientRepository.findByEmail(username)
                .orElse(null);

        if (user == null) {
            logger.info("User not found in ClientRepository, checking SpecialistRepository");
            user = specialistRepository.findByEmail(username)
                    .orElse(null);
        }

        if (user == null) {
            logger.info("User not found in SpecialistRepository, checking AdministratorRepository");
            user = administratorRepository.findByEmail(username)
                    .orElseThrow(() -> {
                        logger.error("User not found with email: {}", username);
                        return new UsernameNotFoundException("User not found with email: " + username);
                    });
        }

        logger.info("User found: {} with role: {}", user.getUsername(), user.getAuthorities());
        return user;
    }
}



