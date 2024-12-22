package com.wgu.myapplication.controllers;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.wgu.myapplication.models.UserModel;

@Dao
public interface UserDao {

    @Insert
    void insertUser(UserModel User);

    @Update
    void  updateUser(UserModel User);

    @Query("SELECT * FROM user WHERE id = :id")
    UserModel getUserById(int id);


}
