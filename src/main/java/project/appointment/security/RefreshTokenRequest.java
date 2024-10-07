package project.appointment.security;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}