package com.wgu.myapplication.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.wgu.myapplication.controllers.AchievementDao;
import com.wgu.myapplication.controllers.CategoryDao;
import com.wgu.myapplication.controllers.JourneyDao;
import com.wgu.myapplication.controllers.SidequestDao;
import com.wgu.myapplication.controllers.UserDao;

import com.wgu.myapplication.models.JourneyModel;
import com.wgu.myapplication.models.UserModel;
import com.wgu.myapplication.models.SideQuestModel;
import com.wgu.myapplication.models.CategoryModel;
import com.wgu.myapplication.models.AchievementModel;


@Database(entities = {UserModel.class, JourneyModel.class, CategoryModel.class, SideQuestModel.class, AchievementModel.class}, version = 1)
public abstract class TravelersCompendiumDatabase extends RoomDatabase{

    public abstract UserDao userDao();
    public abstract JourneyDao journeyDao();
    public abstract SidequestDao sidequestDao();

    public abstract CategoryDao categoryDao();

    public abstract AchievementDao achievementDao();

    private static volatile TravelersCompendiumDatabase INSTANCE;

    public static TravelersCompendiumDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TravelersCompendiumDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TravelersCompendiumDatabase.class, "travelers_compendium_database")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
