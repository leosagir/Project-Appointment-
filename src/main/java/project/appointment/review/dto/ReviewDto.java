package project.appointment.review.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDto {
    private Long id;
    private Long specialistId;
    private Long clientId;
    private Long appointmentId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}
