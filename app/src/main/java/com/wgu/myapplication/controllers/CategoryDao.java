package com.wgu.myapplication.controllers;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.wgu.myapplication.models.AchievementModel;
import com.wgu.myapplication.models.CategoryModel;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert
    void insertCategory(CategoryModel category);

    @Query("SELECT * FROM categories WHERE title = :title LIMIT 1")
    CategoryModel getCategoryName(String title);

    @Query("SELECT * FROM categories WHERE counter = :counter LIMIT 1")
    CategoryModel getCount(int counter);

    @Query("SELECT * FROM categories")
    List<CategoryModel> getAllCategories();
    @Update
    void updateCategory(CategoryModel category);
}
