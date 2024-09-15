package project.appointment.specialist.dto;

import lombok.Data;

@Data
public class SpecialistShortDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String specialization;
}
