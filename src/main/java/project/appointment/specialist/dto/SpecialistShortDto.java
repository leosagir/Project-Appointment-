package project.appointment.specialist.dto;

import lombok.Data;
import project.appointment.specialization.entity.Specialization;

import java.util.Set;

@Data
public class SpecialistShortDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
