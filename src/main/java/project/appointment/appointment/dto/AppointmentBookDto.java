package project.appointment.appointment.dto;

import lombok.Data;

@Data
public class AppointmentBookDto {
    private Long clientId;
    private String clientFirstName;
    private String clientLastName;
}
