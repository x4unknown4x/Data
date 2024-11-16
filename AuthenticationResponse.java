package com.application.vehicleservicemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String status;
    private UserDto userDto;
    private String token;
    private LocalDateTime issuedAt;
    private Date expiration;
}
