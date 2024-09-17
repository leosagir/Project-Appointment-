package project.appointment.review.dto;

import lombok.Data;

@Data
public class ReviewCreateDto {
    private Long specialistId;
    private Long clientId;
    private Long appointmentId;
    private Integer rating;
    private String comment;
}
