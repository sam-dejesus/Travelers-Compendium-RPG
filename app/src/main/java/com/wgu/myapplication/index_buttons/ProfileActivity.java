package com.wgu.myapplication.index_buttons;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.wgu.myapplication.R;
import com.wgu.myapplication.controllers.CategoryDao;
import com.wgu.myapplication.database.TravelersCompendiumDatabase;
import com.wgu.myapplication.models.AchievementModel;
import com.wgu.myapplication.models.CategoryModel;
import com.wgu.myapplication.models.UserModel;

import java.util.concurrent.Executors;

public class ProfileActivity extends AppCompatActivity {

    private TravelersCompendiumDatabase db;
    private ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = TravelersCompendiumDatabase.getDatabase(getApplicationContext());

        setContentView(R.layout.activity_profile);

        TextView name = findViewById(R.id.namePlaceholder);
        TextView title = findViewById(R.id.titlePlaceholder);
        TextView level = findViewById(R.id.levelPlaceholder);
        avatar = findViewById(R.id.avatar);
        Button changeAvatarButton = findViewById(R.id.changeAvatarButton);
        TextView localCount = findViewById(R.id.localCount);
        TextView natureCount = findViewById(R.id.natureCount);
        TextView outOfTownCount = findViewById(R.id.outOfTownCount);
        TextView activityCount = findViewById(R.id.activityCount);
        TextView experienceCount = findViewById(R.id.experienceCount);
        CategoryDao stats = db.categoryDao();
        CategoryModel local = stats.getCategoryName("Local");
        CategoryModel nature = stats.getCategoryName("Nature");
        CategoryModel outOfTown = stats.getCategoryName("Out_of_town");
        CategoryModel activity = stats.getCategoryName("Activities");
        CategoryModel experience = stats.getCategoryName("Experiences");

        changeAvatarButton.setOnClickListener(v -> showAvatarSelectionDialog());

        Executors.newSingleThreadExecutor().execute(() -> {
            UserModel user = db.userDao().getUserById(0);

            String avatarImage = user.getAvatar();
            int avatarResId = getResources().getIdentifier(avatarImage, "drawable", getPackageName());
            runOnUiThread(() -> {
                name.setText( user.getName());
                title.setText( user.getTitle());
                level.setText(String.valueOf(user.getLevel()));
                avatar.setImageResource(avatarResId);
                localCount.setText(String.valueOf(local.getCounter()));
                natureCount.setText(String.valueOf(nature.getCounter()));
                outOfTownCount.setText(String.valueOf(outOfTown.getCounter()));
                activityCount.setText(String.valueOf(activity.getCounter()));
                experienceCount.setText(String.valueOf(experience.getCounter()));

            });
        });


    }


    private void showAvatarSelectionDialog() {
        Executors.newSingleThreadExecutor().execute(() -> {
            UserModel user = db.userDao().getUserById(0);

            String[] avatarOptions;
            String[] avatarImageNames;
            int[] avatarResIds;

            if (user.getLevel() < 3) {
                avatarOptions = new String[]{"Avatar 1", "Avatar 2"};
                avatarImageNames = new String[]{"avatar1", "avatar2"};
                avatarResIds = new int[]{R.drawable.avatar1, R.drawable.avatar2};
            } else if (user.getLevel() < 7) {
                avatarOptions = new String[]{"Avatar 1", "Avatar 2", "Avatar 3", "Avatar 4"};
                avatarImageNames = new String[]{"avatar1", "avatar2", "avatar3", "avatar4"};
                avatarResIds = new int[]{R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3, R.drawable.avatar4};
            } else {
                avatarOptions = new String[]{"Avatar 1", "Avatar 2", "Avatar 3", "Avatar 4", "Avatar 5", "Avatar 6"};
                avatarImageNames = new String[]{"avatar1", "avatar2", "avatar3", "avatar4", "avatar5", "avatar6"};
                avatarResIds = new int[]{R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3, R.drawable.avatar4, R.drawable.avatar5, R.drawable.avatar6};
            }

            runOnUiThread(() -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Select an Avatar");

                builder.setItems(avatarOptions, (dialog, which) -> {
                    avatar.setImageResource(avatarResIds[which]);
                    user.setAvatar(avatarImageNames[which]);

                    Executors.newSingleThreadExecutor().execute(() -> db.userDao().updateUser(user));
                });

                builder.show();
            });
        });
        AchievementModel achievement = db.achievementDao().getAchievementByName("Metamorphosis");
        int newCount = achievement.getCounter() + 1;
        achievement.setCounter(newCount);
        db.achievementDao().updateAchievement(achievement);
    }


}
