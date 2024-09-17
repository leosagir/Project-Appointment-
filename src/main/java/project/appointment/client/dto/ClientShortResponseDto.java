package project.appointment.client.dto;

import lombok.Data;

@Data
public class ClientShortResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
}
