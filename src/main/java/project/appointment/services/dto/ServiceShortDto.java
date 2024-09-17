package project.appointment.services.dto;

import lombok.Data;

@Data
public class ServiceShortDto {
    private Long id;
    private String title;
    private Integer duration;
    private String price;
}
