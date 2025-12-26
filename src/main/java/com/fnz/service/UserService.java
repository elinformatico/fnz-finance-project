package com.fnz.service;

import com.fnz.dto.UserDto;
import com.fnz.dto.UserProfileResponseDto;
import com.fnz.dto.UserUpdateDto;
import com.fnz.entity.User;
import com.fnz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User register(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent() ||
                userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("Username or email already exists");
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }

    public User update(String id, UserUpdateDto updateDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        // Check uniqueness if changing
        if (!user.getUsername().equals(updateDto.getUsername()) && userRepository.findByUsername(updateDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (!user.getEmail().equals(updateDto.getEmail()) && userRepository.findByEmail(updateDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        user.setFullName(updateDto.getFullName());
        user.setUsername(updateDto.getUsername());
        user.setEmail(updateDto.getEmail());
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserProfileResponseDto getUserProfile(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfileResponseDto userDto = new UserProfileResponseDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());

        return userDto;
    }
}
