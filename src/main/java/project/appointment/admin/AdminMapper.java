package project.appointment.admin;

import org.mapstruct.*;
import project.appointment.admin.dto.AdminRequestDto;
import project.appointment.admin.dto.AdminResponseDto;
import project.appointment.admin.dto.AdminUpdateDto;
import project.appointment.admin.entity.Admin;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdminMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "role", constant = "ADMINISTRATOR")
    Admin adminRequestDtoToAdmin(AdminRequestDto adminRequestDto);

    AdminResponseDto adminToAdminResponseDto(Admin admin);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "role", ignore = true)
    void updateAdminFromDto(AdminUpdateDto adminUpdateDto, @MappingTarget Admin admin);

    @InheritInverseConfiguration(name = "adminToAdminResponseDto")
    Admin adminResponseDtoToAdmin(AdminResponseDto adminResponseDto);

    @InheritInverseConfiguration(name = "adminRequestDtoToAdmin")
    AdminRequestDto adminToAdminRequestDto(Admin admin);

    @InheritConfiguration(name = "updateAdminFromDto")
    @Mapping(target = "id", ignore = true)
    Admin adminUpdateDtoToAdmin(AdminUpdateDto adminUpdateDto);

    @InheritInverseConfiguration(name = "adminUpdateDtoToAdmin")
    AdminUpdateDto adminToAdminUpdateDto(Admin admin);
}
