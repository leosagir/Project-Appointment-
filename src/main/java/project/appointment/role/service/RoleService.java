package project.appointment.role.service;

import project.appointment.role.entity.Role;
import project.appointment.client.entity.Client;

public interface RoleService {
    void assignRoleToUser(Client admin, Client user, Role role);
}
