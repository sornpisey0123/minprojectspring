package com.kid.minprojectspringg1btb.repository;

import com.kid.minprojectspringg1btb.model.dto.request.CategoryRequest;
import com.kid.minprojectspringg1btb.model.dto.response.CategoryResponse;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CategoryRepository {

    @Select("""
            SELECT * FROM categories WHERE category_id = #{categoryId}
            """)
    @Results(
            id = "categoryMapping",
            value = {
                    @Result(property = "categoryId", column = "category_id"),
                    @Result(property = "user", column = "user_id",
                            one = @One(select = "com.kid.minprojectspringg1btb.repository.UserRepository.findUserById")
                    )
            }
    )
    CategoryResponse getCategoryById(Integer categoryId);


    @Select("""
            INSERT INTO categories (name, description, user_id) VALUES (#{req.name}, #{req.description}, #{userId})
            RETURNING category_id
            """)
    Integer addNewCategory(@Param("req") CategoryRequest categoryRequest, Integer userId);
}