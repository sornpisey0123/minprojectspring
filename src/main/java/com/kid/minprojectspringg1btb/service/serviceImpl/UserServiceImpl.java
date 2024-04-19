package com.kid.minprojectspringg1btb.service.serviceImpl;

import com.kid.minprojectspringg1btb.exception.BadRequestExceptionCustom;
import com.kid.minprojectspringg1btb.exception.NotFoundExceptionCustom;
import com.kid.minprojectspringg1btb.model.dto.request.AuthRequest;
import com.kid.minprojectspringg1btb.model.dto.request.UserRequest;
import com.kid.minprojectspringg1btb.model.dto.response.UserResponse;
import com.kid.minprojectspringg1btb.model.entity.User;
import com.kid.minprojectspringg1btb.otp.OTPService;
import com.kid.minprojectspringg1btb.repository.OTPRepository;
import com.kid.minprojectspringg1btb.repository.UserRepository;
import com.kid.minprojectspringg1btb.service.UserService;
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
    private final OTPRepository otpRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder, JavaMailSender javaMailSender, OTPService otpService, OTPRepository otpRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.javaMailSender = javaMailSender;
        this.otpService = otpService;
        this.otpRepository = otpRepository;
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

        String otp = String.valueOf(otpService.generateOTP(userRequest.getEmail()));
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userRequest.getEmail());
        mailMessage.setSubject("Verify your email with opt code");
        mailMessage.setText(otp);
        javaMailSender.send(mailMessage);
        User user = userRepository.register(userRequest);
        if (user != null) {

            otpRepository.addNewOtp
                    (
                            otp,
                            otpService.getOtpRequestedTime(),
                            otpService.isOTPRequired(userRequest.getEmail()),
                            user.getUserId()
                    );
        }

        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public String verify(String otp) {
        if(otpService.verify(otp).equals("verified")) {
            otpRepository.updateVerifyOtp(otp);
        }
        return otpService.verify(otp);
    }

    //verify login
    @Override
    public Boolean login(AuthRequest authRequest) {
        Integer userId = userRepository.getUserIdByEmail(authRequest.getEmail());
        return otpRepository.checkVerifiedUserByUserId(userId);
    }
//    resend email
    @Override
    public Boolean resend(String email){
        if(userRepository.findUserByEmail(email) != null){
            String otp = String.valueOf(otpService.generateOTP(email));
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject("Verify your email with opt code");
            mailMessage.setText(otp);
            javaMailSender.send(mailMessage);
            Integer userId = userRepository.getUserIdByEmail(email);
             otpRepository.updateOtpByUserId(otp, userId);
            System.out.println(otp);
            return true;
        }else{
            throw new NotFoundExceptionCustom("Invalid Email");
        }
    }

}
