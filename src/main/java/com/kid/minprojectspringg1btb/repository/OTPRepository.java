package com.kid.minprojectspringg1btb.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.Date;


@Mapper
public interface OTPRepository {
    @Select("""
        INSERT INTO Otps VALUES (DEFAULT, #{otpCode}, #{issuedAt}, #{expiration}, DEFAULT, #{userId})
    """)
    @Results(
            id = "otpMapping",
            value = {
                    @Result(property = "otpCode", column = "otp_code"),
                    @Result(property = "issuedAt", column = "issued_at"),
                    @Result(property = "userId", column = "user_id")
            }
    )
    void addNewOtp(String otpCode, Date issuedAt, Boolean expiration, Integer userId);

    @Select("""
                SELECT otp_code FROM Otps WHERE user_id = #{userId}

            """)
    String getOtpByUserId(Integer userId);
    @Select("""
            UPDATE Otps SET verify = true, otp_code = 'verify' WHERE otp_code = #{otpCode}
            """)
    void updateVerifyOtp(String otpCode);
}
