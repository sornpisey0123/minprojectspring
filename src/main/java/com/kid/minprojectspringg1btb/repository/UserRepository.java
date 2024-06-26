package com.kid.minprojectspringg1btb.repository;

import com.kid.minprojectspringg1btb.model.dto.request.UserRequest;
import com.kid.minprojectspringg1btb.model.dto.response.UserResponse;
import com.kid.minprojectspringg1btb.model.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserRepository {
    @Select("""
            INSERT INTO Users (email, password, profile_image)
            VALUES (#{req.email}, #{req.password}, #{req.profileImage})
            RETURNING *
            """)
    @Results(
            id = "authMapping",
            value = {
                    @Result(property = "userId", column = "user_id"),
                    @Result(property = "profileImage", column = "profile_image")
            }
    )
    User register(@Param("req") UserRequest userRequest);

    @Select("""
            SELECT email from Users;
            """)
    List<String> findAllEmail();

    @Select("""
            SELECT * FROM Users WHERE email = #{email}
            """)
    User findUserByEmail(String email);

    // retrieve userid to verify
    @Select("""
            SELECT user_id FROM Users WHERE email = #{email}
            """)
    Integer getUserIdByEmail(String email);
    //update password by Email
    @Select("""
    UPDATE users SET password  =  #{password} WHERE email = #{email}
    """)
    Boolean forgetPassword(String password, String email);

    @Select("""
            SELECT * FROM Users WHERE user_id = #{userId}
            """)
    @ResultMap("authMapping")
    User findUserById(Integer userId);

    @Select("""
            SELECT * FROM Users WHERE user_id = #{userId}
            """)
    @Results(
            id = "userMapping",
            value = {
                    @Result(property = "userId", column = "user_id"),
                    @Result(property = "profileImage", column = "profile_image")
            }
    )
    UserResponse findUserCategoryById(Integer userId);

}
