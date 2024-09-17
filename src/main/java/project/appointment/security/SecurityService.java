package project.appointment.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import project.appointment.client.entity.Client;
import project.appointment.specialist.entity.Specialist;
import project.appointment.admin.entity.Admin;

@Service
public class SecurityService {

    public boolean isCurrentUser(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof Client) {
            return ((Client) principal).getId().equals(userId);
        } else if (principal instanceof Specialist) {
            return ((Specialist) principal).getId().equals(userId);
        } else if (principal instanceof Admin) {
            return ((Admin) principal).getId().equals(userId);
        }

        return false;
    }

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof Client) {
            return ((Client) principal).getId();
        } else if (principal instanceof Specialist) {
            return ((Specialist) principal).getId();
        } else if (principal instanceof Admin) {
            return ((Admin) principal).getId();
        }

        return null;
    }
}




//package project.appointment.security;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//import project.appointment.client.entity.Client;
//import project.appointment.specialist.entity.Specialist;
//import project.appointment.admin.entity.Admin;
//
//@Service
//public class SecurityService {
//
//    public boolean isCurrentUser(Long userId) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return false;
//        }
//
//        Object principal = authentication.getPrincipal();
//        if (principal instanceof Client) {
//            return ((Client) principal).getId().equals(userId);
//        } else if (principal instanceof Specialist) {
//            return ((Specialist) principal).getId().equals(userId);
//        } else if (principal instanceof Admin) {
//            return ((Admin) principal).getId().equals(userId);
//        }
//
//        return false;
//    }
//}
