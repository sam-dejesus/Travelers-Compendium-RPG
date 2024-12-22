package com.wgu.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.wgu.myapplication.index_buttons.AchievementsActivity;
import com.wgu.myapplication.index_buttons.AddJourneyActivity;
import com.wgu.myapplication.index_buttons.ChaptersActivity;
import com.wgu.myapplication.index_buttons.ProfileActivity;

public class IndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_index);
        Button home = findViewById(R.id.home);
        Button newJourney = findViewById(R.id.newJourney);
        Button chapters = findViewById(R.id.chapters);
        Button achievements = findViewById(R.id.achievements);
        Button profile = findViewById(R.id.profile);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.purple_500));

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        newJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this, AddJourneyActivity.class);
                startActivity(intent);
            }
        });

        chapters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this, ChaptersActivity.class);
                startActivity(intent);
            }
        });

        achievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this, AchievementsActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

    }
}
