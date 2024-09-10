package project.appointment.role.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.appointment.role.entity.Role;
import project.appointment.role.entity.RoleName;
import project.appointment.role.repository.RoleRepository;
import project.appointment.client.entity.Client;
import project.appointment.client.repository.ClientRepository;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{
    private RoleRepository roleRepository;
    private ClientRepository userRepository;


    @Override
    public void assignRoleToUser(Client admin, Client user, Role role) {
        if(!adminHasRole(admin, RoleName.ADMIN)){
            throw new SecurityException("Only admins can assign roles.");
        }
        if(user.getRoles()==null){
            user.setRoles(new HashSet<>());
        }
        user.getRoles().add(role);
        userRepository.save(user);
    }
    private boolean adminHasRole(Client admin, RoleName roleName){
        return admin.getRoles().stream()
                .anyMatch(role->role.getRoleName()
                .equals(roleName));
    }
}
