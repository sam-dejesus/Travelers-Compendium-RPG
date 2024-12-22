package com.wgu.myapplication.index_buttons;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.wgu.myapplication.R;
import com.wgu.myapplication.database.TravelersCompendiumDatabase;
import com.wgu.myapplication.models.JourneyModel;
import com.wgu.myapplication.models.SideQuestModel;
import com.wgu.myapplication.utilities.DateValidation;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;

public class SideQuestActivity extends AppCompatActivity {

    private TravelersCompendiumDatabase db;
    private EditText journeyName;
    private EditText sidequestName;
    private CalendarView startDate;
    private Button addButton;
    private TextView status;

    private long selectedStartDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidequest);

        addButton = findViewById(R.id.add);
        journeyName = findViewById(R.id.journeyName);
        sidequestName = findViewById(R.id.sidequestName);
        startDate = findViewById(R.id.startDate);
        status = findViewById(R.id.status);

        db = TravelersCompendiumDatabase.getDatabase(getApplicationContext());

        startDate.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar startCal = Calendar.getInstance();
            startCal.set(year, month, dayOfMonth);
            selectedStartDate = startCal.getTimeInMillis();
        });

    }

    public void addSidequest(View view) {
        final int sidequestLimit = 3;

        String journeyNameText = journeyName.getText().toString();
        String sidequestNameText = sidequestName.getText().toString();
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        String selectedStartDateText = dateFormat.format(selectedStartDate);

        if (!journeyNameText.isEmpty() && !sidequestNameText.isEmpty()) {
            Executors.newSingleThreadExecutor().execute(() -> {
                JourneyModel journey = db.journeyDao().getJourneyByName(journeyNameText);

                if (journey != null) {
                    try {
                        int sidequestCount = db.sidequestDao().getSidequestCount(journey.getId());
                        if (sidequestCount >= sidequestLimit) {
                            runOnUiThread(() -> status.setText("Cannot add more than " + sidequestLimit + " sidequests to this journey."));
                            return;
                        }

                        Date journeyStartDate = dateFormat.parse(journey.getStartDate());
                        Date journeyEndDate = dateFormat.parse(journey.getEndDate());
                        Date selectedDate = dateFormat.parse(selectedStartDateText);

                        if (DateValidation.isSidequestDateValid(selectedDate, journeyStartDate, journeyEndDate)) {
                            SideQuestModel sidequest = new SideQuestModel(sidequestNameText, selectedStartDateText, journey.getId());
                            db.sidequestDao().insertSidequest(sidequest);
                            runOnUiThread(() -> status.setText("Sidequest added."));



                        } else {
                            runOnUiThread(() -> status.setText("Sidequest date is before or after the journey."));
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    runOnUiThread(() -> status.setText("Journey not found."));
                }
            });
        } else {
            status.setText("No fields can be empty.");
        }
    }



}
