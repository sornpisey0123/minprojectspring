package com.kid.minprojectspringg1btb.repository;

import com.kid.minprojectspringg1btb.model.dto.request.CategoryRequest;
import com.kid.minprojectspringg1btb.model.dto.response.CategoryExpenseResponse;
import com.kid.minprojectspringg1btb.model.dto.response.CategoryResponse;
import com.kid.minprojectspringg1btb.model.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

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
                            one = @One(select = "com.kid.minprojectspringg1btb.repository.UserRepository.findUserCategoryById")
                    )
            }
    )
    CategoryResponse getCategoryById(Integer categoryId);


    @Select("""
            SELECT * FROM categories WHERE category_id = #{categoryId}
            """)
    @Results(
            id = "categoryExpenseMapping",
            value = {
                    @Result(property = "categoryId", column = "category_id"),
            }
    )
    CategoryExpenseResponse getCategoryExpenseById(Integer categoryId);


    @Select("""
            INSERT INTO categories (name, description, user_id) VALUES (#{req.name}, #{req.description}, #{userId})
            RETURNING category_id
            """)
    Integer addNewCategory(@Param("req") CategoryRequest categoryRequest, Integer userId);


    @Delete("DELETE FROM categories WHERE category_id = #{categoryId}")
    Boolean removeCategory(Integer categoryId);

    @Select("UPDATE categories " +
            "SET name = #{req.name} ,description = #{req.description} " +
            "WHERE category_id = #{categoryId} " +
            "RETURNING category_id")
    Integer editCategory(@Param("req") CategoryRequest categoryRequest, Integer categoryId);


    @Select("SELECT * FROM categories LIMIT #{size} OFFSET #{size} * (#{page} -1)")
    @ResultMap("categoryMapping")
    List<CategoryResponse> getCategoryList(Integer page, Integer size);
}