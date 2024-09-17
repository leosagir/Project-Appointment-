package project.appointment.services.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import project.appointment.specialist.dto.SpecialistShortDto;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
public class ServiceWithSpecialistsDto extends ServiceResponseDto {
    private Set<SpecialistShortDto> specialists;
}
