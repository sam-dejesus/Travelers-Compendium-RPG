package com.wgu.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.wgu.myapplication.database.TravelersCompendiumDatabase;
import com.wgu.myapplication.models.UserModel;

import java.util.concurrent.Executors;

public class MainActivity2 extends AppCompatActivity {
    private TravelersCompendiumDatabase db;

    private int[] walkingFrames = {
            R.drawable.run1, R.drawable.run2, R.drawable.run3,
            R.drawable.run4, R.drawable.run5, R.drawable.run6,
            R.drawable.run7, R.drawable.run8
    };
    private int[] walkingFrames2 = {
            R.drawable.runk1, R.drawable.runk2, R.drawable.runk3,
            R.drawable.runk4, R.drawable.runk5, R.drawable.runk6,
            R.drawable.runk6, R.drawable.runk7
    };
    private int[] walkingFrames3 = {
            R.drawable.runw1, R.drawable.runw2, R.drawable.runw3,
            R.drawable.runw4, R.drawable.runw5, R.drawable.runw6,
            R.drawable.runw7, R.drawable.runw8
    };
    private int[] walkingFrames4 = {
            R.drawable.rune1, R.drawable.rune2, R.drawable.rune3,
            R.drawable.rune4, R.drawable.rune5, R.drawable.rune6,
            R.drawable.rune7, R.drawable.rune8
    };
    private int[] walkingFrames5 = {
            R.drawable.runa1, R.drawable.runa2, R.drawable.runa3,
            R.drawable.runa4, R.drawable.runa5, R.drawable.runa6,
            R.drawable.runa7, R.drawable.runa8
    };
    private int[] walkingFrames6 = {
            R.drawable.runm1, R.drawable.runm2, R.drawable.runm3,
            R.drawable.runm4, R.drawable.runm5, R.drawable.runm6,
            R.drawable.runm7, R.drawable.runm8
    };

    private int currentFrame = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        db = TravelersCompendiumDatabase.getDatabase(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView title = findViewById(R.id.title);
        TextView level = findViewById(R.id.level);
        TextView expBar = findViewById(R.id.expBar);
        ImageView avatar = findViewById(R.id.avatar);

        Executors.newSingleThreadExecutor().execute(() -> {
            UserModel user = db.userDao().getUserById(0);

            String avatarImage = user.getAvatar();
            int avatarResId = getResources().getIdentifier(avatarImage, "drawable", getPackageName());
            runOnUiThread(() -> {
                title.setText("Title: " + user.getTitle());
                level.setText("Level " + user.getLevel());
                expBar.setText("EXP: " + user.getExp() + "/" + user.getExpNeeded());
                if (user.isStatus()) {
                    startWalkingAnimation(avatar, avatarImage);
                } else {
                    avatar.setImageResource(avatarResId);
                }
            });
        });

        Button reportButton = findViewById(R.id.reportButton);
        reportButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity2.this, ReportActivity.class);
            startActivity(intent);
        });

        Button menuButton = findViewById(R.id.menuButton);
        menuButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, IndexActivity.class);
            startActivity(intent);
        });
    }
    private void startWalkingAnimation(ImageView avatar, String avatarImage) {
        int[] selectedWalkingFrames = getWalkingFramesForAvatar(avatarImage);

        Runnable animationRunnable = new Runnable() {
            @Override
            public void run() {
                avatar.setImageResource(selectedWalkingFrames[currentFrame]);
                currentFrame = (currentFrame + 1) % selectedWalkingFrames.length;
                handler.postDelayed(this, 100);
            }
        };


        handler.post(animationRunnable);
    }

    private int[] getWalkingFramesForAvatar(String avatarImage) {
        switch (avatarImage) {
            case "avatar1":
                return walkingFrames;
            case "avatar2":
                return walkingFrames2;
            case "avatar3":
                return walkingFrames3;
            case "avatar4":
                return walkingFrames4;
            case "avatar5":
                return walkingFrames5;
            case "avatar6":
                return walkingFrames6;
            default:
                return walkingFrames;
        }
    }
}
