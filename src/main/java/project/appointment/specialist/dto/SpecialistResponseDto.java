package project.appointment.specialist.dto;

import lombok.Data;
import project.appointment.client.entity.Status;
import project.appointment.timeSlot.entity.TimeSlot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SpecialistResponseDto {
    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private String specialization;

    private String description;

    private Integer experience;

    private LocalDate dateOfBirth;

    private String address;

    private String phone;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Status status;

}
