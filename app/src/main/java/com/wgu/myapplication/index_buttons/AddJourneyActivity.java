package com.wgu.myapplication.index_buttons;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.wgu.myapplication.IndexActivity;
import com.wgu.myapplication.R;
import com.wgu.myapplication.database.TravelersCompendiumDatabase;
import com.wgu.myapplication.models.AchievementModel;
import com.wgu.myapplication.models.CategoryModel;
import com.wgu.myapplication.models.JourneyModel;
import com.wgu.myapplication.utilities.DateValidation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class AddJourneyActivity extends AppCompatActivity {

    private EditText journeyName;
    private TextView expValue;
    private TravelersCompendiumDatabase db;
    Button submitButton;
    private String selectedText;
    private RadioGroup categoryPicker;
    private EditText startDateEditText;
    private EditText endDateEditText;
    private long selectedStartDate;
    private long selectedEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_journey);

        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.purple_500));
        submitButton = findViewById(R.id.submit);

        SeekBar expSeekBar = findViewById(R.id.expSeekBar);
        startDateEditText = findViewById(R.id.startDateEdit);
        endDateEditText = findViewById(R.id.endDateEdit);
        categoryPicker = findViewById(R.id.categoryPicker);

        journeyName = findViewById(R.id.journeyNameEdit);
        expValue = findViewById(R.id.expValue);

        Executors.newSingleThreadExecutor().execute(() -> {
            List<CategoryModel> categories = db.categoryDao().getAllCategories();
            for (CategoryModel category : categories) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(category.getTitle());
                radioButton.setId(View.generateViewId());
                categoryPicker.addView(radioButton);
            }

        });

        db = TravelersCompendiumDatabase.getDatabase(getApplicationContext());

        startDateEditText.setOnClickListener(view -> showDatePickerDialog(true));
        endDateEditText.setOnClickListener(view -> showDatePickerDialog(false));

        expSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int exp, boolean isInput) {
                int adjustedExp = (exp / 50) * 50;
                seekBar.setProgress(adjustedExp);
                expValue.setText(String.valueOf(adjustedExp));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        submitButton.setOnClickListener(v -> submit());
    }


    private void showDatePickerDialog(boolean isStartDate) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, monthOfYear, dayOfMonth);

                    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
                    String formattedDate = dateFormat.format(selectedDate.getTime());

                    if (isStartDate) {
                        startDateEditText.setText(formattedDate);
                        selectedStartDate = selectedDate.getTimeInMillis();
                    } else {
                        endDateEditText.setText(formattedDate);
                        selectedEndDate = selectedDate.getTimeInMillis();
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }


    public void submit() {

        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        String startDateText = dateFormat.format(selectedStartDate);
        String endDateText = dateFormat.format(selectedEndDate);

        if (!DateValidation.dateCheck(selectedStartDate, selectedEndDate)) {
            Toast.makeText(this, "Start date must be before the end date!", Toast.LENGTH_SHORT).show();
            return;
        }

        String journeyNameText = journeyName.getText().toString();
        if(journeyNameText.isEmpty()){
            Toast.makeText(this, "your Journey must have a name.", Toast.LENGTH_SHORT).show();
            return;
        }

        int expValueText = Integer.parseInt(expValue.getText().toString());
        int selectedId = categoryPicker.getCheckedRadioButtonId();
        String selectedCategory;
        if (selectedId != -1) {
            selectedCategory = ((RadioButton) findViewById(selectedId)).getText().toString();
        } else {
            Toast.makeText(this, "You must pick a Category for your Journey.", Toast.LENGTH_SHORT).show();
            return;
        }


        JourneyModel journey = new JourneyModel(
                journeyNameText,
                selectedCategory,
                startDateText,
                endDateText,
                expValueText,
                "Start"
        );

        Executors.newSingleThreadExecutor().execute(() -> {
            long journeyId = db.journeyDao().insertJourney(journey);
            journey.setId((int) journeyId);

            runOnUiThread(() -> {

                Intent intent = new Intent(AddJourneyActivity.this, IndexActivity.class);
                startActivity(intent);
                finish();
            });
        });
    }


}

