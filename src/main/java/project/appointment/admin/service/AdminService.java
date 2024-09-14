package project.appointment.admin.service;

import project.appointment.admin.dto.AdminRequestDto;
import project.appointment.admin.dto.AdminResponseDto;
import project.appointment.admin.dto.AdminUpdateDto;
import project.appointment.client.dto.ClientResponseDto;

public interface AdminService {
    AdminResponseDto registerAdmin(AdminRequestDto adminRequestDto);
    AdminResponseDto updateAdmin(Long id, AdminUpdateDto adminUpdateDto);
    AdminResponseDto deactivateAdmin(Long id);
}
