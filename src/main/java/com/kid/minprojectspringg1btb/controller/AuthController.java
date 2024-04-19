package com.kid.minprojectspringg1btb.controller;

import com.kid.minprojectspringg1btb.exception.BadRequestExceptionCustom;
import com.kid.minprojectspringg1btb.jwt.JwtService;
import com.kid.minprojectspringg1btb.model.dto.request.AuthRequest;
import com.kid.minprojectspringg1btb.model.dto.request.ForgetRequest;
import com.kid.minprojectspringg1btb.model.dto.request.UserRequest;
import com.kid.minprojectspringg1btb.model.dto.response.AuthResponse;
import com.kid.minprojectspringg1btb.model.dto.response.UserResponse;
import com.kid.minprojectspringg1btb.model.entity.User;
import com.kid.minprojectspringg1btb.service.UserService;
import jakarta.validation.Valid;
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

import javax.swing.text.StyledEditorKit;

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

    private void authenticate(String email, String password) throws Exception {
        try {
            UserDetails userApp = userService.loadUserByUsername(email);
            if (userApp == null) {
                throw new BadRequestExceptionCustom("Invalid email");
            }
            if (!passwordEncoder.matches(password, userApp.getPassword())) {
                throw new BadRequestExceptionCustom("Invalid password");
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest) throws Exception {
        Boolean verifiedUser = userService.login(authRequest);
        if (verifiedUser) {
            authenticate(authRequest.getEmail(), authRequest.getPassword());
            final UserDetails userDetails = userService.loadUserByUsername(authRequest.getEmail());
            final String token = jwtService.generateToken(userDetails);
            AuthResponse authResponse = new AuthResponse(token);
            return ResponseEntity.ok(authResponse);
        } else {
            throw new BadRequestExceptionCustom("Your account is not verify yet");
        }
    }
    //check verify( true/false)
    @PostMapping("/resend")
    public ResponseEntity<String> resendEmail( @RequestParam String email) throws Exception {
        Boolean isValidUser = userService.resend(email);
        if(isValidUser){
            return ResponseEntity.ok("Resend otp code successful");
        }
        return ResponseEntity.badRequest().body("Invalid Email");
    }

    @PutMapping("/forget")
    public ResponseEntity<String> forgetPassword(@RequestParam String email,@Valid @RequestBody ForgetRequest forgetRequest) throws Exception {
        if(userService.forgetPassword(email, forgetRequest)){
            return ResponseEntity.ok("your password is reset successful");
        }
    return ResponseEntity.badRequest().body("Your account is not verify yet ");

    }

}
