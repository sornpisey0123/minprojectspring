package com.kid.minprojectspringg1btb.repository;

import com.kid.minprojectspringg1btb.model.dto.request.UserRequest;
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


}
