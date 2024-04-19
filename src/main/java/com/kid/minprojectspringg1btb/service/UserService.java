package com.kid.minprojectspringg1btb.service;

import com.kid.minprojectspringg1btb.model.dto.request.AuthRequest;
import com.kid.minprojectspringg1btb.model.dto.request.UserRequest;
import com.kid.minprojectspringg1btb.model.dto.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserResponse register(UserRequest userRequest);
    String verify(String otp);
    Boolean login(AuthRequest authRequest);
    Boolean resend(String email);

}
