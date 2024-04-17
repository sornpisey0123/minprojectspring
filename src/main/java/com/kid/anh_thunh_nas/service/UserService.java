package com.kid.anh_thunh_nas.service;

import com.kid.anh_thunh_nas.model.dto.request.UserRequest;
import com.kid.anh_thunh_nas.model.dto.response.UserResponse;

public interface UserService {
    UserResponse register(UserRequest userRequest);
}
