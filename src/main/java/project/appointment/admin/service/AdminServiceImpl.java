package project.appointment.admin.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.appointment.admin.dto.AdminRequestDto;
import project.appointment.admin.dto.AdminResponseDto;
import project.appointment.admin.dto.AdminUpdateDto;
import project.appointment.admin.entity.Admin;
import project.appointment.admin.AdminMapper;
import project.appointment.admin.repository.AdminRepository;
import project.appointment.client.entity.Status;
import project.appointment.exception.EmailAlreadyExistsException;
import project.appointment.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;
    private final Validator validator;

    @Override
    @Transactional
    public AdminResponseDto registerAdmin(AdminRequestDto adminRequestDto) {
        logger.info("Attempting to register new admin with email: {}", adminRequestDto.getEmail());
        validateAdminRequest(adminRequestDto);
        if (adminRepository.existsByEmail(adminRequestDto.getEmail())) {
            logger.warn("Registration failed: Email already exists: {}", adminRequestDto.getEmail());
            throw new EmailAlreadyExistsException("Email already exists");
        }
        Admin admin = adminMapper.adminRequestDtoToAdmin(adminRequestDto);
        admin.setPassword(passwordEncoder.encode(adminRequestDto.getPassword()));
        try {
            Admin savedAdmin = adminRepository.save(admin);
            logger.info("Admin registered successfully with id: {}", savedAdmin.getId());
            return adminMapper.adminToAdminResponseDto(savedAdmin);
        } catch (DataIntegrityViolationException e) {
            logger.error("Error during admin registration", e);
            throw new RuntimeException("Error during admin registration", e);
        }
    }

    @Override
    @Transactional
    public AdminResponseDto updateAdmin(Long id, AdminUpdateDto adminUpdateDto) {
        logger.info("Attempting to update admin with id: {}", id);
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Update failed: Admin not found with id: {}", id);
                    return new ResourceNotFoundException("Admin not found with id: " + id);
                });
        adminMapper.updateAdminFromDto(adminUpdateDto, admin);
        Admin updatedAdmin = adminRepository.save(admin);
        logger.info("Admin updated successfully with id: {}", updatedAdmin.getId());
        return adminMapper.adminToAdminResponseDto(updatedAdmin);
    }

    @Override
    @Transactional
    public AdminResponseDto deactivateAdmin(Long id) {
        logger.info("Attempting to deactivate admin with id: {}", id);
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Deactivation failed: Admin not found with id: {}", id);
                    return new ResourceNotFoundException("Admin not found with id: " + id);
                });
        if (admin.getStatus() == Status.INACTIVE) {
            logger.warn("Admin with id: {} is already inactive", id);
            throw new IllegalStateException("Admin is already inactive");
        }
        admin.setStatus(Status.INACTIVE);
        Admin deactivatedAdmin = adminRepository.save(admin);
        logger.info("Admin deactivated successfully with id: {}", deactivatedAdmin.getId());
        return adminMapper.adminToAdminResponseDto(deactivatedAdmin);
    }

    @Override
    @Transactional
    public AdminResponseDto reactivateAdmin(Long id) {
        logger.info("Attempting to reactivate admin with id: {}", id);
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Reactivation failed: Admin not found with id: {}", id);
                    return new ResourceNotFoundException("Admin not found with id: " + id);
                });
        if (admin.getStatus() == Status.ACTIVE) {
            logger.warn("Admin with id: {} is already active", id);
            throw new IllegalStateException("Admin is already active");
        }
        admin.setStatus(Status.ACTIVE);
        Admin reactivatedAdmin = adminRepository.save(admin);
        logger.info("Admin reactivated successfully with id: {}", reactivatedAdmin.getId());
        return adminMapper.adminToAdminResponseDto(reactivatedAdmin);
    }

    @Override
    public AdminResponseDto getAdminById(Long id) {
        logger.info("Fetching admin with id: {}", id);
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Fetch failed: Admin not found with id: {}", id);
                    return new ResourceNotFoundException("Admin not found with id: " + id);
                });
        return adminMapper.adminToAdminResponseDto(admin);
    }

    @Override
    public List<AdminResponseDto> getAllAdmins() {
        logger.info("Fetching all admins");
        List<Admin> admins = adminRepository.findAll();
        return admins.stream()
                .map(adminMapper::adminToAdminResponseDto)
                .collect(Collectors.toList());
    }

    private void validateAdminRequest(AdminRequestDto adminRequestDto) {
        Set<ConstraintViolation<AdminRequestDto>> violations = validator.validate(adminRequestDto);
        if (!violations.isEmpty()) {
            logger.error("Validation failed for AdminRequestDto: {}", violations);
            throw new ConstraintViolationException(violations);
        }
    }
}





//package project.appointment.admin.service;
//
//import jakarta.transaction.Transactional;
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.ConstraintViolationException;
//import jakarta.validation.Validator;
//import lombok.RequiredArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import project.appointment.ENUM.Role;
//import project.appointment.admin.dto.AdminRequestDto;
//import project.appointment.admin.dto.AdminResponseDto;
//import project.appointment.admin.dto.AdminUpdateDto;
//import project.appointment.admin.entity.Admin;
//import project.appointment.admin.repository.AdminRepository;
//import project.appointment.client.entity.Status;
//import project.appointment.exception.EmailAlreadyExistsException;
//import project.appointment.exception.RegistrationException;
//import project.appointment.exception.ResourceNotFoundException;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class AdminServiceImpl implements AdminService {
//
//    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
//
//    private final AdminRepository administratorRepository;
//    private final ModelMapper modelMapper;
//    private final PasswordEncoder passwordEncoder;
//    private final Validator validator;
//
//    @Override
//    public AdminResponseDto getAdminById(Long id) {
//        logger.info("Fetching admin with id: {}", id);
//        Admin admin = administratorRepository.findById(id)
//                .orElseThrow(() -> {
//                    logger.error("Admin not found with id: {}", id);
//                    return new ResourceNotFoundException("Admin not found with id: " + id);
//                });
//        logger.info("Admin found with id: {}", id);
//        return modelMapper.map(admin, AdminResponseDto.class);
//    }
//
//    @Override
//    public List<AdminResponseDto> getAllAdmins() {
//        logger.info("Fetching all admins");
//        List<Admin> admins = administratorRepository.findAll();
//        logger.info("Found {} admins", admins.size());
//        return admins.stream()
//                .map(admin -> modelMapper.map(admin, AdminResponseDto.class))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    @Transactional
//    public AdminResponseDto registerAdmin(AdminRequestDto adminRequestDto) {
//        logger.info("Registering new admin with email: {}", adminRequestDto.getEmail());
//        if (adminRequestDto == null) {
//            logger.error("AdminRequestDto is null");
//            throw new IllegalArgumentException("AdminRequestDto must not be null");
//        }
//        validateAdminRequest(adminRequestDto);
//        if (administratorRepository.existsByEmail(adminRequestDto.getEmail())) {
//            logger.error("Email already exists: {}", adminRequestDto.getEmail());
//            throw new EmailAlreadyExistsException("Email already exists");
//        }
//        Admin admin = modelMapper.map(adminRequestDto, Admin.class);
//        admin.setPassword(passwordEncoder.encode(adminRequestDto.getPassword()));
//        admin.setCreatedAt(LocalDateTime.now());
//        admin.setRole(Role.ADMINISTRATOR);
//        admin.setStatus(Status.ACTIVE);
//        try {
//            Admin savedAdmin = administratorRepository.save(admin);
//            logger.info("Admin registered successfully with id: {}", savedAdmin.getId());
//            return modelMapper.map(savedAdmin, AdminResponseDto.class);
//        } catch (DataIntegrityViolationException e) {
//            logger.error("Error during admin registration", e);
//            throw new RegistrationException("Error during admin registration", e);
//        }
//    }
//
//    @Override
//    public AdminResponseDto updateAdmin(Long id, AdminUpdateDto updateDto) {
//        logger.info("Updating admin with id: {}", id);
//        Admin admin = administratorRepository.findById(id)
//                .orElseThrow(() -> {
//                    logger.error("Admin not found with id: {}", id);
//                    return new ResourceNotFoundException("Admin not found with id: " + id);
//                });
//
//        updateAdminFields(admin, updateDto);
//
//        Admin updatedAdmin = administratorRepository.save(admin);
//        logger.info("Admin updated successfully with id: {}", updatedAdmin.getId());
//        return modelMapper.map(updatedAdmin, AdminResponseDto.class);
//    }
//
//    @Override
//    @Transactional
//    public AdminResponseDto deactivateAdmin(Long id) {
//        logger.info("Deactivating admin with id: {}", id);
//        Admin admin = administratorRepository.findById(id)
//                .orElseThrow(() -> {
//                    logger.error("Administrator not found with id: {}", id);
//                    return new ResourceNotFoundException("Administrator with id " + id + " not found");
//                });
//
//        if (admin.getStatus() == Status.INACTIVE) {
//            logger.warn("Administrator with id: {} is already inactive", id);
//            throw new IllegalStateException("Administrator is already inactive");
//        }
//
//        admin.setStatus(Status.INACTIVE);
//
//        Admin updatedAdmin = administratorRepository.save(admin);
//        logger.info("Administrator deactivated successfully with id: {}", updatedAdmin.getId());
//        return modelMapper.map(updatedAdmin, AdminResponseDto.class);
//    }
//
//    private void validateAdminRequest(AdminRequestDto adminRequestDto) {
//        Set<ConstraintViolation<AdminRequestDto>> violations = validator.validate(adminRequestDto);
//        if (!violations.isEmpty()) {
//            logger.error("Validation failed for AdminRequestDto: {}", violations);
//            throw new ConstraintViolationException(violations);
//        }
//    }
//
//    private void updateAdminFields(Admin admin, AdminUpdateDto updateDto) {
//        if (updateDto.getFirstName() != null) {
//            admin.setFirstName(updateDto.getFirstName());
//        }
//        if (updateDto.getLastName() != null) {
//            admin.setLastName(updateDto.getLastName());
//        }
//        if (updateDto.getDateOfBirth() != null) {
//            admin.setDateOfBirth(updateDto.getDateOfBirth());
//        }
//        if (updateDto.getAddress() != null) {
//            admin.setAddress(updateDto.getAddress());
//        }
//        if (updateDto.getPhone() != null) {
//            admin.setPhone(updateDto.getPhone());
//        }
//    }
//}