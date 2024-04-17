package com.kid.anh_thunh_nas.service.serviceImpl;

import com.kid.anh_thunh_nas.exception.BadRequestExceptionCustom;
import com.kid.anh_thunh_nas.model.dto.request.UserRequest;
import com.kid.anh_thunh_nas.model.dto.response.UserResponse;
import com.kid.anh_thunh_nas.model.entity.User;
import com.kid.anh_thunh_nas.otp.OTPService;
import com.kid.anh_thunh_nas.repository.UserRepository;
import com.kid.anh_thunh_nas.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender javaMailSender;
    private final OTPService otpService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder, JavaMailSender javaMailSender, OTPService otpService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.javaMailSender = javaMailSender;
        this.otpService = otpService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public UserResponse register(UserRequest userRequest) {
        for (String email : userRepository.findAllEmail()) {
            if (userRequest.getEmail().equals(email)) {
                throw new BadRequestExceptionCustom("This email is already registered");
            }
        }
        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            throw new BadRequestExceptionCustom("Your confirm password does not match with your password");
        }
        userRequest.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));

        Integer otp = otpService.generateOTP(userRequest.getEmail());
        System.out.println("otp: " + otp);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userRequest.getEmail());
        mailMessage.setSubject("Verify your email with opt code");
        mailMessage.setText(String.valueOf(otp));
        javaMailSender.send(mailMessage);

        User user = userRepository.register(userRequest);
        return modelMapper.map(user, UserResponse.class);
    }


}
