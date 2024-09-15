package project.appointment.admin.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.appointment.ENUM.Role;
import project.appointment.admin.dto.AdminRequestDto;
import project.appointment.admin.dto.AdminResponseDto;
import project.appointment.admin.dto.AdminUpdateDto;
import project.appointment.admin.entity.Admin;
import project.appointment.admin.repository.AdminRepository;
import project.appointment.client.entity.Status;
import project.appointment.exception.EmailAlreadyExistsException;
import project.appointment.exception.RegistrationException;
import project.appointment.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

  private final AdminRepository administratorRepository;
  private final ModelMapper modelMapper;
  private final PasswordEncoder passwordEncoder;
  private final Validator validator;


    @Override
    @Transactional
    public AdminResponseDto registerAdmin(AdminRequestDto adminRequestDto) {
        if (adminRequestDto==null){
            throw new IllegalArgumentException("AdminRequestDto must not be null");
        }
        validateAdminRequest(adminRequestDto);
        if(administratorRepository.existsByEmail(adminRequestDto.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists");
        }
        Admin admin = modelMapper.map(adminRequestDto,Admin.class);
        admin.setPassword(passwordEncoder.encode(adminRequestDto.getPassword()));
        admin.setCreatedAt(LocalDateTime.now());
        admin.setRole(Role.ADMINISTRATOR);
        admin.setStatus(Status.ACTIVE);
        try{
            Admin savedAdmin = administratorRepository.save(admin);
            return modelMapper.map(savedAdmin,AdminResponseDto.class);
        }catch(DataIntegrityViolationException e){
            throw new RegistrationException("Error during admin registration",e);
        }

    }

    @Override
    public AdminResponseDto updateAdmin(Long id, AdminUpdateDto updateDto) {
        Admin admin = administratorRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Admin not found with id: "+id));
        if(updateDto.getFirstName() !=null){
            admin.setFirstName(updateDto.getFirstName());
        }
        if(updateDto.getLastName() !=null){
            admin.setLastName((updateDto.getLastName()));
        }
        if(updateDto.getDateOfBirth() !=null){
            admin.setDateOfBirth(updateDto.getDateOfBirth());
        }
        if(updateDto.getAddress() !=null){
            admin.setAddress(updateDto.getAddress());
        }
        if(updateDto.getPhone() != null){
            admin.setPhone(updateDto.getPhone());
        }
        Admin updatedAdmin = administratorRepository.save(admin);
        return modelMapper.map(updatedAdmin,AdminResponseDto.class);
    }

    @Override
    @Transactional
    public AdminResponseDto deactivateAdmin(Long id) {
        Admin admin = administratorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Administrator with id " + id + " not found"));

        if (admin.getStatus() == Status.INACTIVE) {
            throw new IllegalStateException("Administrator is already inactive");
        }

        admin.setStatus(Status.INACTIVE);

        Admin updatedAdmin = administratorRepository.save(admin);
        return modelMapper.map(updatedAdmin, AdminResponseDto.class);
    }

    private void validateAdminRequest(AdminRequestDto adminRequestDto) {
        Set<ConstraintViolation<AdminRequestDto>> violations = validator.validate(adminRequestDto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
