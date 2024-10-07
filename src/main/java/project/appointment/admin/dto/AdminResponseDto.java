package project.appointment.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import project.appointment.ENUM.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AdminResponseDto {
    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String address;

    private String phone;
    @JsonFormat(pattern = "dd MMMM yyyy HH:mm", timezone = "UTC")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd MMMM yyyy HH:mm", timezone = "UTC")
    private LocalDateTime updatedAt;

    private Status status;

}
