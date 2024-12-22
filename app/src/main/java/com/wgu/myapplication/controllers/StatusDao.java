//package com.wgu.myapplication.controllers;
//
//import androidx.room.Dao;
//import androidx.room.Insert;
//import androidx.room.Query;
//import androidx.room.Update;
//
//import com.wgu.myapplication.models.JourneyModel;
//import com.wgu.myapplication.models.StatusModel;
//import com.wgu.myapplication.models.UserModel;
//@Dao
//public interface StatusDao {
//
//    @Insert
//    void insertJourney(JourneyModel journey);
//
//    @Insert
//    void insertStatus(StatusModel status);
//
//    @Update
//    void  updateStatus(StatusModel status);
//
//    @Query("SELECT * FROM status WHERE id = :id")
//    StatusModel getStatusById(int id);
//
//    @Query("SELECT * FROM journey WHERE id = :journey_id")
//    JourneyModel getJourneyById(int journey_id);
//}
