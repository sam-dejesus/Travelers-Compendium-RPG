package com.wgu.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.wgu.myapplication.database.TravelersCompendiumDatabase;
import com.wgu.myapplication.models.JourneyModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ReportActivity extends AppCompatActivity {
    private TravelersCompendiumDatabase db;
    private LinearLayout reportContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        reportContainer = findViewById(R.id.report_container);
        db = TravelersCompendiumDatabase.getDatabase(getApplicationContext());

        List<JourneyModel> allJourneys = db.journeyDao().getAllJourneyItems();

        String currentDate = getCurrentDate();
        String date30DaysFromNow = getDate30DaysFromNow();
        List<JourneyModel> filteredJourneys = filterJourneysByDate(allJourneys, currentDate, date30DaysFromNow);


        if (!filteredJourneys.isEmpty()) {
            for (JourneyModel journey : filteredJourneys) {
                TextView result = new TextView(this);
                result.setText(formatJourneyDetails(journey));
                result.setPadding(16, 16, 16, 16);
                result.setTextColor(0xFF000000);
                reportContainer.addView(result);
            }
        } else {
            TextView result = new TextView(this);
            result.setText("No upcoming journeys in the next 30 days.");
            result.setPadding(16, 16, 16, 16);
            result.setTextSize(30);
            result.setTextColor(0xFF000000);
            reportContainer.addView(result);
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        return format.format(new Date());
    }

    private String getDate30DaysFromNow() {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 30);
        return format.format(calendar.getTime());
    }

    private List<JourneyModel> filterJourneysByDate(List<JourneyModel> allJourneys, String startDate, String endDate) {
        List<JourneyModel> filteredJourneys = new ArrayList<>();

        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());

        for (JourneyModel journey : allJourneys) {
            String journeyStartDate = journey.getStartDate();
            String journeyStatus = journey.getStatus();
            try {
                Date parsedJourneyDate = format.parse(journeyStartDate);
                Date parsedStartDate = format.parse(startDate);
                Date parsedEndDate = format.parse(endDate);

               
                if (parsedJourneyDate != null && parsedJourneyDate.after(parsedStartDate) && parsedJourneyDate.before(parsedEndDate) && Objects.equals(journeyStatus, "Start")) {
                    filteredJourneys.add(journey);
                }
            } catch (Exception e) {
                Log.e("Date Parsing Error", "Error parsing dates", e);
            }
        }
        return filteredJourneys;
    }

    private String formatJourneyDetails(JourneyModel journey) {
        return "Journey Name: " + journey.getTitle() + "\n" +
                "Start Date: " + journey.getStartDate() + "\n" +
                "End Date: " + journey.getEndDate() + "\n" ;
    }
}
