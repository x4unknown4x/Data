package com.application.vehicleservicemanagement.service.implementation;

import com.application.vehicleservicemanagement.dto.ApiResponse;
import com.application.vehicleservicemanagement.dto.RegisterRequest;
import com.application.vehicleservicemanagement.dto.UserDto;
import com.application.vehicleservicemanagement.entity.Role;
import com.application.vehicleservicemanagement.entity.User;
import com.application.vehicleservicemanagement.exception.ResourceNotFoundException;
import com.application.vehicleservicemanagement.repository.UserRepository;
import com.application.vehicleservicemanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponse createUser(RegisterRequest registerRequest) {
        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .phone(registerRequest.getPhone())
                .address(registerRequest.getAddress())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .scheduledVehicleCount(0)
                .isAvailable(Boolean.TRUE)
                .role(Role.SERVICE_ADVISOR)
                .build();
        userRepository.save(user);
        return ApiResponse.builder()
                .message("User registered successfully !!")
                .status("Success")
                .build();
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(user -> modelMapper.map(user, UserDto.class)).toList();
    }

    @Override
    public List<UserDto> getAllServiceAdvisors() {
        List<User> userList = userRepository.findAllByRole(Role.SERVICE_ADVISOR);
        return userList.stream().sorted(Comparator.comparingInt(User::getScheduledVehicleCount)).map(user -> modelMapper.map(user, UserDto.class)).toList();
    }

    @Override
    public ApiResponse updateUserById(Long id, UserDto userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAddress(userDTO.getAddress());
        user.setPhone(userDTO.getPhone());
        userRepository.save(user);
        return ApiResponse.builder().message("User Updated successfully.").status("Success").build();
    }

    @Override
    public ApiResponse deleteUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));
        userRepository.delete(user);
        return ApiResponse.builder().message("User deleted successfully.").status("Success").build();
    }

}
