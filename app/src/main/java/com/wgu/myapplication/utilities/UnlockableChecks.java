package com.wgu.myapplication.utilities;

import android.content.Context;
import android.util.Log;

import com.wgu.myapplication.database.TravelersCompendiumDatabase;
import com.wgu.myapplication.models.AchievementModel;
import com.wgu.myapplication.models.CategoryModel;
import com.wgu.myapplication.models.JourneyModel;
import com.wgu.myapplication.models.UserModel;

import java.util.concurrent.Executors;

public class UnlockableChecks {

    private Context context;

    public UnlockableChecks(Context context) {
        this.context = context;
    }




    public void startJourney(JourneyModel journey){
        TravelersCompendiumDatabase db = TravelersCompendiumDatabase.getDatabase(context);

        Executors.newSingleThreadExecutor().execute(() -> {
            UserModel user = db.userDao().getUserById(0);
            user.setStatus(true);
            user.setExp(user.getExp() + journey.getExp());

            if(user.getExp() >= user.getExpNeeded()){
                user.setLevel(user.getLevel() + 1);
                int level = user.getLevel();

                AchievementModel achievement1 = db.achievementDao().getAchievementByName("Avatar Master");
                AchievementModel achievement2 = db.achievementDao().getAchievementByName("Globetrotter");
                AchievementModel achievement3 = db.achievementDao().getAchievementByName("You are wise!");

                int newCount1 = achievement1.getCounter() + 1;
                int newCount2 = achievement2.getCounter() + 1;
                int newCount3 = achievement3.getCounter() + 1;

                achievement1.setCounter(newCount1);
                achievement2.setCounter(newCount2);
                achievement3.setCounter(newCount3);

                db.achievementDao().updateAchievement(achievement1);
                db.achievementDao().updateAchievement(achievement2);
                db.achievementDao().updateAchievement(achievement3);

                switch (level) {
                    case 3:
                        user.setTitle("Traveler");
                        break;
                    case 7:
                        user.setTitle("Voyager");
                        break;
                    case 10:
                        user.setTitle("Globetrotter");
                        break;
                    default:
                        break;
                }

                user.setExpNeeded((int) (user.getExpNeeded() * 1.7 + 300));

            }
            db.userDao().updateUser(user);


            String category = journey.getCategory();
            CategoryModel categories;
            switch (category) {
                case "Local":
                case "Nature":
                case "Activities":
                case "Experiences":
                    categories = db.categoryDao().getCategoryName(category);
                    break;
                default:
                    categories = db.categoryDao().getCategoryName("Out_of_town");
                    break;
            }

            categories.setCounter(categories.getCounter() + 1);

            db.categoryDao().updateCategory(categories);

        });
    }

    public void endJourney(JourneyModel journey) {
        TravelersCompendiumDatabase db = TravelersCompendiumDatabase.getDatabase(context);
        Executors.newSingleThreadExecutor().execute(() -> {
            UserModel user = db.userDao().getUserById(0);
            user.setStatus(false);
            journey.setStatus("Completed");

            db.userDao().updateUser(user);
            db.journeyDao().updateJourney(journey);

        });
    }
}
