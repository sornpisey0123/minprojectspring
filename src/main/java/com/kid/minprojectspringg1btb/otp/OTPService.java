package com.kid.minprojectspringg1btb.otp;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.kid.minprojectspringg1btb.exception.NotFoundExceptionCustom;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;


@Setter
@Getter
@Service
public class OTPService {

    private static final Integer EXPIRE_MINS = 5;
    private static final long OTP_VALID_DURATION = 5 * 60 * 1000;
    private LoadingCache<String, Integer> otpCache;
    private Date otpRequestedTime = new Date();
    private String oneTimePassword;
    public OTPService(){
        super();
        otpCache = CacheBuilder.newBuilder().
                expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    public Integer generateOTP(String key){
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        otpCache.put(key, otp);
        setOneTimePassword(String.valueOf(otp));
        return otp;
    }

    public Integer getOtp(String key){
        try{
            return otpCache.get(key);
        }catch (Exception e){
            return 0;
        }
    }

    public void clearOTP(String key){
        otpCache.invalidate(key);
    }

    public boolean isOTPRequired(String key) {

        if (getOtp(key) == null) {
            return false;
        }

        Date otpRequestedTimeInMillis = new Date(System.currentTimeMillis() + OTP_VALID_DURATION);
        // OTP expires return false
        return otpRequestedTimeInMillis.before(new Date(System.currentTimeMillis()));
    }

    public String verify (String otp) {
        if(otp.equals(getOneTimePassword())) {
            return "verified";
        }
        throw new NotFoundExceptionCustom("Invalid otp code");
    }

}