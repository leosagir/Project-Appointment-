package project.appointment.admin.service;

import project.appointment.admin.dto.AdminRequestDto;
import project.appointment.admin.dto.AdminResponseDto;
import project.appointment.admin.dto.AdminUpdateDto;

import java.util.List;

public interface AdminService {
    AdminResponseDto registerAdmin(AdminRequestDto adminRequestDto);
    AdminResponseDto updateAdmin(Long id, AdminUpdateDto adminUpdateDto);
    AdminResponseDto deactivateAdmin(Long id);
    AdminResponseDto reactivateAdmin(Long id);
    AdminResponseDto getAdminById(Long id);
    List<AdminResponseDto> getAllAdmins();
}



//package project.appointment.admin.service;
//
//import project.appointment.admin.dto.AdminRequestDto;
//import project.appointment.admin.dto.AdminResponseDto;
//import project.appointment.admin.dto.AdminUpdateDto;
//import project.appointment.client.dto.ClientResponseDto;
//
//import java.util.List;
//
//public interface AdminService {
//    AdminResponseDto registerAdmin(AdminRequestDto adminRequestDto);
//    AdminResponseDto updateAdmin(Long id, AdminUpdateDto adminUpdateDto);
//    AdminResponseDto deactivateAdmin(Long id);
//    AdminResponseDto getAdminById(Long id);
//    List<AdminResponseDto> getAllAdmins();
//}
