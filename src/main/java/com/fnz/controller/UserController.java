package com.fnz.controller;

import com.fnz.dto.UserDto;
import com.fnz.dto.UserProfileResponseDto;
import com.fnz.dto.UserUpdateDto;
import com.fnz.entity.User;
import com.fnz.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping("/me")
    public ResponseEntity<User> update(@Valid @RequestBody UserUpdateDto updateDto, Authentication authentication) {
        User updatedUser = userService.update(authentication.getName(), updateDto);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponseDto> getProfile(Authentication authentication) {
        // Spring will inject "authentication" automatically
        return ResponseEntity.ok(
                userService.getUserProfile(authentication.getName())
        );
    }
}
