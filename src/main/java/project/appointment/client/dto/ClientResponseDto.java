package project.appointment.client.dto;

import lombok.Data;
import project.appointment.client.entity.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ClientResponseDto {
        private Long id;

        private String email;

        private String firstName;

        private String lastName;

        private LocalDate dateOfBirth;

        private String address;

        private String phone;

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;

        private Status status;

    }
