package com.wgu.myapplication.controllers;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.wgu.myapplication.models.JourneyModel;
import com.wgu.myapplication.models.SideQuestModel;

import java.util.List;

@Dao
public interface JourneyDao {
    @Insert
    long insertJourney(JourneyModel Journey);
    @Update
    void updateJourney(JourneyModel journey);

    @Delete
    void deleteJourney(JourneyModel journey);
    @Query("SELECT * FROM journey")
    LiveData<List<JourneyModel>> getAllJourneys();

    @Query("SELECT * FROM journey WHERE title = :journeyName LIMIT 1")
    JourneyModel getJourneyByName(String journeyName);

    @Query("SELECT * FROM side_quests WHERE journey_id = :id")
    List<SideQuestModel> getSidequestByJourneyId(int id);

    @Query("SELECT * FROM journey")
    List<JourneyModel> getAllJourneyItems();

}
