package com.kid.minprojectspringg1btb.controller;

import com.kid.minprojectspringg1btb.jwt.JwtService;
import com.kid.minprojectspringg1btb.model.dto.request.AuthRequest;
import com.kid.minprojectspringg1btb.model.dto.request.UserRequest;
import com.kid.minprojectspringg1btb.model.dto.response.AuthResponse;
import com.kid.minprojectspringg1btb.model.dto.response.UserResponse;
import com.kid.minprojectspringg1btb.model.entity.User;
import com.kid.minprojectspringg1btb.service.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auths")
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;
    public AuthController(AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder, UserService userService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtService = jwtService;
    }
    @PutMapping("/verify")
    public String verify(String otp) {
        return userService.verify(otp);
    }
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(userRequest));
    }
    public static Integer currentId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUserId();

    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) throws Exception {
        authenticate(authRequest.getEmail(), authRequest.getPassword());
        final UserDetails userDetails = userService.loadUserByUsername(authRequest.getEmail());
        final String token = jwtService.generateToken(userDetails);
        AuthResponse authResponse = new AuthResponse(token);
        return ResponseEntity.ok(authResponse);
    }
    private void authenticate(String username, String password) throws Exception {
        try {
            UserDetails userApp = userService.loadUserByUsername(username);
            if (userApp == null) {
                throw new BadRequestException("Wrong Email");
            }
            if (!passwordEncoder.matches(password, userApp.getPassword())) {
                throw new BadRequestException("Wrong Password");
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }




}
