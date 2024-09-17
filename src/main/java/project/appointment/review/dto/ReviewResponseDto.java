package project.appointment.review.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponseDto {
    private Long id;
    private String specialistName;
    private String clientName;
    private LocalDateTime appointmentDate;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}
