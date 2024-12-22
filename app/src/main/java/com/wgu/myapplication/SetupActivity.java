package com.wgu.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wgu.myapplication.database.TravelersCompendiumDatabase;
import com.wgu.myapplication.models.AchievementModel;
import com.wgu.myapplication.models.CategoryModel;
import com.wgu.myapplication.models.UserModel;

import java.util.concurrent.Executors;

public class SetupActivity extends AppCompatActivity {

    private TravelersCompendiumDatabase db;
    private ImageView selectedCharacter = null;
    private String selectedCharacterId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        db = TravelersCompendiumDatabase.getDatabase(getApplicationContext());

        ImageView character1 = findViewById(R.id.character1);
        ImageView character2 = findViewById(R.id.character2);

        character1.setTag("avatar1");
        character2.setTag("avatar2");

        EditText userName = findViewById(R.id.user_name);

        character1.setOnClickListener(v -> selectCharacter(character1));
        character2.setOnClickListener(v -> selectCharacter(character2));

        findViewById(R.id.complete_setup_button).setOnClickListener(view -> {
            String userNameText = userName.getText().toString();

            if(userNameText.isEmpty()){
                userName.setError("Please write your name");
                return;
            }

            if (selectedCharacterId == null) {
                Toast.makeText(this, "Please select a character!", Toast.LENGTH_SHORT).show();
                return;
            }

            UserModel user = new UserModel(userNameText, selectedCharacterId,"Novice Traveller", 1,0, 600,false);

            CategoryModel localSpot = new CategoryModel("Local", 0);
            CategoryModel nature = new CategoryModel("Nature", 0);
            CategoryModel outOfTown = new CategoryModel("Out_of_town", 0);
            CategoryModel experiences = new CategoryModel("Experiences", 0);
            CategoryModel activities = new CategoryModel("Activities", 0);


            AchievementModel achievement1 = new AchievementModel("Testing the waters", "Go on every type of adventure.", 0);
            AchievementModel achievement2 = new AchievementModel("Metamorphosis", "Change to a new avatar.", 0);
            AchievementModel achievement3 = new AchievementModel("Spread the word", "Share your adventure.", 0);
            AchievementModel achievement4 = new AchievementModel("Getting used to this", "go on 25 adventures", 0);
            AchievementModel achievement5 = new AchievementModel("Jack of all adventures", "Go on every type of adventure 10 times.", 0);

            AchievementModel achievement6 = new AchievementModel("Avatar Master", "Unlock all the avatars", 0);
            AchievementModel achievement7 = new AchievementModel("Globetrotter", "Unlock the last title", 0);
            AchievementModel achievement8 = new AchievementModel("You are wise!", "Reach level 20.", 0);

            AchievementModel achievement9 = new AchievementModel("Centurion", "Go on 100 adventures", 0);

            Executors.newSingleThreadExecutor().execute(() -> {
                db.userDao().insertUser(user);
                db.categoryDao().insertCategory(localSpot);
                db.categoryDao().insertCategory(nature);
                db.categoryDao().insertCategory(outOfTown);
                db.categoryDao().insertCategory(experiences);
                db.categoryDao().insertCategory(activities);

                db.achievementDao().insertAchievement(achievement1);
                db.achievementDao().insertAchievement(achievement2);
                db.achievementDao().insertAchievement(achievement3);
                db.achievementDao().insertAchievement(achievement4);
                db.achievementDao().insertAchievement(achievement5);
                db.achievementDao().insertAchievement(achievement6);
                db.achievementDao().insertAchievement(achievement7);
                db.achievementDao().insertAchievement(achievement8);
                db.achievementDao().insertAchievement(achievement9);

            });

            Intent intent = new Intent(SetupActivity.this, MainActivity2.class);
            startActivity(intent);
            finish();
        });
    }

    private void selectCharacter(ImageView selectedImage) {
        if (selectedCharacter != null) {
            selectedCharacter.setAlpha(1f);
        }

        selectedImage.setAlpha(0.5f);
        selectedCharacter = selectedImage;
        selectedCharacterId = (String) selectedImage.getTag();
    }
}
