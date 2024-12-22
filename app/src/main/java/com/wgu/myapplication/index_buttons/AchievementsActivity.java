package com.wgu.myapplication.index_buttons;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wgu.myapplication.R;
import com.wgu.myapplication.database.TravelersCompendiumDatabase;
import com.wgu.myapplication.models.AchievementModel;
import com.wgu.myapplication.models.CategoryModel;

import java.util.List;
import java.util.concurrent.Executors;

public class AchievementsActivity extends AppCompatActivity {
    TravelersCompendiumDatabase db;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);
        db = TravelersCompendiumDatabase.getDatabase(getApplicationContext());

        LinearLayout achievementsContainer = findViewById(R.id.achievements_container);

        Executors.newSingleThreadExecutor().execute(() -> {
            List<AchievementModel> achievements = db.achievementDao().getAllAchievements();

            runOnUiThread(() -> {
                for (AchievementModel achievement : achievements) {
                    LinearLayout achievementLayout = new LinearLayout(this);
                    achievementLayout.setOrientation(LinearLayout.HORIZONTAL);
                    achievementLayout.setPadding(0, 0, 0, 16);
                    achievementLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));



                    ImageView imageView = new ImageView(this);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(60, 60));
                    imageView.setImageResource(unlockAchievement(achievement.getTitle()));


                    LinearLayout textContainer = new LinearLayout(this);
                    textContainer.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams textContainerParams = new LinearLayout.LayoutParams(
                            0, LinearLayout.LayoutParams.WRAP_CONTENT, 1
                    );
                    textContainerParams.setMargins(16, 0, 0, 0);
                    textContainer.setLayoutParams(textContainerParams);

                    TextView titleView = new TextView(this);
                    titleView.setText(achievement.getTitle());
                    titleView.setTextSize(16);
                    titleView.setTypeface(null, Typeface.BOLD);
                    int icon = unlockAchievement(achievement.getTitle());
                    if (icon == R.drawable.baseline_auto_awesome_24) {
                        titleView.setTextColor(Color.parseColor("#998503"));
                    }

                    textContainer.addView(titleView);

                    TextView descriptionView = new TextView(this);
                    descriptionView.setText(achievement.getDescription());
                    descriptionView.setTextSize(14);
                    descriptionView.setPadding(0, 4, 0, 0);
                    textContainer.addView(descriptionView);

                    achievementLayout.addView(imageView);
                    achievementLayout.addView(textContainer);

                    achievementsContainer.addView(achievementLayout);
                }
            });
        });
    }

    private int unlockAchievement(String title) {
        AchievementModel achievement = db.achievementDao().getAchievementByName(title);

        CategoryModel local = db.categoryDao().getCategoryName("Local");
        CategoryModel nature = db.categoryDao().getCategoryName("Nature");
        CategoryModel out_of_town = db.categoryDao().getCategoryName("Out_of_town");
        CategoryModel experiences = db.categoryDao().getCategoryName("Experiences");
        CategoryModel activities = db.categoryDao().getCategoryName("Activities");

        int total = local.getCounter() + nature.getCounter() + out_of_town.getCounter() + experiences.getCounter() + activities.getCounter();

        if (title.equals("Testing the waters")) {
            if (local.getCounter() > 0 &&
                    nature.getCounter() > 0 &&
                    out_of_town.getCounter() > 0 &&
                    experiences.getCounter() > 0 &&
                    activities.getCounter() > 0)
            {

                return R.drawable.baseline_auto_awesome_24;

            }
        } else if (title.equals("Getting used to this") && total >= 25) {

            return R.drawable.baseline_auto_awesome_24;

        } else if (title.equals("Jack of all adventures") &&
                local.getCounter() > 25 &&
                nature.getCounter() > 25 &&
                out_of_town.getCounter() > 25 &&
                experiences.getCounter() > 25 &&
                activities.getCounter() > 25)
        {
            return R.drawable.baseline_auto_awesome_24;

        } else if (title.equals("Centurion") && total >= 100) {

            return R.drawable.baseline_auto_awesome_24;

        } else if (title.equals("Metamorphosis") || title.equals("Spread the word")) {

            if (achievement.getCounter() > 0) {return R.drawable.baseline_auto_awesome_24;}

        } else if (title.equals("Avatar Master") && achievement.getCounter() >= 7) {
            return R.drawable.baseline_auto_awesome_24;
        } else if (title.equals("Globetrotter") && achievement.getCounter() >= 10){
            return R.drawable.baseline_auto_awesome_24;
        }else if (title.equals("Your name the wise") && achievement.getCounter() >= 20){
            return R.drawable.baseline_auto_awesome_24;
        }

        return R.drawable.baseline_lock_24;
    }




}
