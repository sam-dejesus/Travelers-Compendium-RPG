package com.wgu.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.wgu.myapplication.database.TravelersCompendiumDatabase;




public class MainActivity extends AppCompatActivity {
    private TravelersCompendiumDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = TravelersCompendiumDatabase.getDatabase(getApplicationContext());

        Button startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(v -> {

            if (db.userDao().getUserById(0) == null) {
                Intent intent = new Intent(this, SetupActivity.class);
                startActivity(intent);
                finish();

            } else {
                Intent intent = new Intent(this, MainActivity2.class);
                startActivity(intent);
                finish();

            }

        });




    }


}
