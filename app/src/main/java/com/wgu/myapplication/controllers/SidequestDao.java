package com.wgu.myapplication.controllers;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.wgu.myapplication.models.SideQuestModel;

@Dao
public interface SidequestDao {

    @Insert
    void insertSidequest(SideQuestModel sidequest);

    @Query("SELECT COUNT(*) FROM side_quests WHERE journey_id = :journeyId")
    int getSidequestCount(long journeyId);
}
