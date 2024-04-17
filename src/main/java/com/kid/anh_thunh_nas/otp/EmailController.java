package com.kid.anh_thunh_nas.otp;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public String sendEmail(@RequestBody EmailData emailData) {
        emailService.sendEmail(emailData.to(), "Verify your email with opt code", emailData.text());
        return "Done !!!";
    }
}
