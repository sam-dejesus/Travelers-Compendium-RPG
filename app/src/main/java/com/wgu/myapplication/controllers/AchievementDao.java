package com.wgu.myapplication.controllers;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.wgu.myapplication.models.AchievementModel;

import java.util.List;

@Dao
public interface AchievementDao {

    @Insert
    void insertAchievement(AchievementModel achievement);

    @Update
    void updateAchievement(AchievementModel achievement);

    @Query("SELECT * FROM achievements WHERE title = :title LIMIT 1")
    AchievementModel getAchievementByName(String title);

    @Query("SELECT * FROM achievements")
    List<AchievementModel> getAllAchievements();
}
