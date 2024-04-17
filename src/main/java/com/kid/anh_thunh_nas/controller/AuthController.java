package com.kid.anh_thunh_nas.controller;

import com.kid.anh_thunh_nas.model.dto.request.UserRequest;
import com.kid.anh_thunh_nas.model.dto.response.UserResponse;
import com.kid.anh_thunh_nas.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auths")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam Integer otp) {
        return null;
    }
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(userRequest));
    }
}
