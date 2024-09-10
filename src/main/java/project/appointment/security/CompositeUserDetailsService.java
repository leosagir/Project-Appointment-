package project.appointment.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.appointment.admin.repository.AdministratorRepository;
import project.appointment.client.repository.ClientRepository;
import project.appointment.specialist.repository.SpecialistRepository;

@Service
public class CompositeUserDetailsService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SpecialistRepository specialistRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = clientRepository.findByEmail(username)
                .orElse(null);

        if (user == null) {
            user = specialistRepository.findByEmail(username)
                    .orElse(null);
        }

        if (user == null) {
            user = administratorRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        }

        return user;
    }
}
