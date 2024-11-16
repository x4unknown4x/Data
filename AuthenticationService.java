package com.application.vehicleservicemanagement.service;

import com.application.vehicleservicemanagement.dto.*;

public interface AuthenticationService {
    ApiResponse register(RegisterRequest registerRequest);

    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
