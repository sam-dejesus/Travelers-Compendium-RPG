package com.wgu.myapplication.index_buttons;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.wgu.myapplication.R;
import com.wgu.myapplication.controllers.JourneyDao;
import com.wgu.myapplication.database.TravelersCompendiumDatabase;
import com.wgu.myapplication.models.JourneyModel;
import com.wgu.myapplication.models.SideQuestModel;
import com.wgu.myapplication.utilities.DateValidation;
import com.wgu.myapplication.utilities.UnlockableChecks;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executors;

public class ChaptersAdapter extends RecyclerView.Adapter<ChaptersAdapter.JourneyViewHolder> {

    private TravelersCompendiumDatabase db;

    private List<JourneyModel> journeys;
    private JourneyDao journeyDao;
    private Context context;


    private UnlockableChecks unlock;

    public ChaptersAdapter(Context context, List<JourneyModel> journeys, JourneyDao journeyDao) {
        this.journeys = journeys;
        this.journeyDao = journeyDao;
        this.context = context;
        this.unlock = new UnlockableChecks(context);
    }

    public void setJourneys(List<JourneyModel> newJourneys) {
        this.journeys = newJourneys;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JourneyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.journey_item, parent, false);
        return new JourneyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JourneyViewHolder holder, int position) {
        JourneyModel journey = journeys.get(position);
        holder.journeyNameText.setText("Journey Name: " + journey.getTitle());
        holder.startDateText.setText("Start Date: " + journey.getStartDate());
        holder.endDateText.setText("End Date: " + journey.getEndDate());
        holder.category.setText("Category: " + journey.getCategory());
        holder.exp.setText("Experience: " + journey.getExp());

        AsyncTask.execute(() -> {
            List<SideQuestModel> sidequests = journeyDao.getSidequestByJourneyId(journey.getId());
            holder.itemView.post(() -> {
                StringBuilder sidequestTitles = new StringBuilder();
                for (SideQuestModel sidequest : sidequests) {
                    sidequestTitles.append("•Name: ").append(sidequest.getTitle())
                            .append("\n")
                            .append("•Date: ").append(sidequest.getDate()).append("\n").append("\n");
                }
                holder.sidequestText.setText(sidequestTitles.toString().trim());
            });
        });

        holder.journeyNameEdit.setText(journey.getTitle());
        holder.startDateEdit.setText(journey.getStartDate());
        holder.endDateEdit.setText(journey.getEndDate());

        if (Objects.equals(journey.getStatus(), "Start")){

            holder.statusButton.setText("Start");
        }else if(Objects.equals(journey.getStatus(), "Active")){

            holder.statusButton.setText("Finish");
            holder.statusOfTripText.setText("Active");
        } else if (Objects.equals(journey.getStatus(), "Completed")) {

            holder.statusButton.setVisibility(View.GONE);
            holder.statusOfTripText.setText("Completed");

        }else{
            holder.statusButton.setText("null");
            holder.statusOfTripText.setText("null");
        }

        holder.updateButton.setOnClickListener(v -> {
            boolean isEditing = holder.journeyNameEdit.getVisibility() == View.VISIBLE;
            if (isEditing) {

                holder.journeyNameText.setVisibility(View.VISIBLE);
                holder.startDateText.setVisibility(View.VISIBLE);
                holder.endDateText.setVisibility(View.VISIBLE);

                holder.journeyNameEdit.setVisibility(View.GONE);
                holder.startDateEdit.setVisibility(View.GONE);
                holder.endDateEdit.setVisibility(View.GONE);

                journey.setTitle(holder.journeyNameEdit.getText().toString());
                journey.setStartDate(holder.startDateEdit.getText().toString());
                journey.setEndDate(holder.endDateEdit.getText().toString());

                updateJourneyDatabase(journey);
                notifyItemChanged(position);
            } else {
                holder.journeyNameText.setVisibility(View.GONE);
                holder.startDateText.setVisibility(View.GONE);
                holder.endDateText.setVisibility(View.GONE);
                holder.journeyNameEdit.setVisibility(View.VISIBLE);
                holder.startDateEdit.setVisibility(View.VISIBLE);
                holder.endDateEdit.setVisibility(View.VISIBLE);
            }
        });

        holder.deleteButton.setOnClickListener(v -> deletionDialog(journey));
        holder.shareButton.setOnClickListener(v -> {
            String vacationDetails = "Here's some information about my trip"+ "\n" +
                    "Title: " + journey.getTitle() + "\n" +
                    "Start Date: " + journey.getStartDate() + "\n" +
                    "End Date: " + journey.getEndDate()+ "\n" +
                    "Category: "+ journey.getCategory()+ "\n" +
                    "Let me Know what you think!";
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, vacationDetails);
            context.startActivity(Intent.createChooser(shareIntent, "Share Adventure Details via"));

        });
        holder.statusButton.setOnClickListener(view -> {
            TravelersCompendiumDatabase db = TravelersCompendiumDatabase.getDatabase(context);
            if (Objects.equals(journey.getStatus(), "Start")) {
                journey.setStatus("Active");
                unlock.startJourney(journey);

                Executors.newSingleThreadExecutor().execute(() -> {
                    db.journeyDao().updateJourney(journey);
                    new Handler(Looper.getMainLooper()).post(() -> {
                        notifyItemChanged(position);
                    });
                });
            }else{
                journey.setStatus("Completed");
                unlock.endJourney(journey);
                new Handler(Looper.getMainLooper()).post(() -> {
                    notifyItemChanged(position);
                });
            }
        });

    }

    private void updateJourneyDatabase(JourneyModel journey) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
            long startTime = dateFormat.parse(journey.getStartDate()).getTime();
            long endTime = dateFormat.parse(journey.getEndDate()).getTime();
            if (!DateValidation.dateCheck(startTime, endTime)) {
                Toast.makeText(context, "Start date must be before the end date!", Toast.LENGTH_LONG).show();
            } else {
                AsyncTask.execute(() -> journeyDao.updateJourney(journey));

            }
        } catch (ParseException e) {
            Toast.makeText(context, "Invalid date format. Please use MM-dd-yyyy.", Toast.LENGTH_SHORT).show();
        }
    }



    private void deletionDialog(JourneyModel journey) {
        db = TravelersCompendiumDatabase.getDatabase(context);
        new android.os.Handler(context.getMainLooper()).post(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Confirmation");
            builder.setMessage("Are you sure you want to proceed?");

            builder.setPositiveButton("Yes Delete", (dialog, which) -> {
                Toast.makeText(context, "this journey has been deleted", Toast.LENGTH_SHORT).show();
                db.journeyDao().deleteJourney(journey);
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> {
            });

            builder.show();
        });
    }

    @Override
    public int getItemCount() {
        return journeys.size();
    }

    static class JourneyViewHolder extends RecyclerView.ViewHolder {
        TextView journeyNameText, startDateText, endDateText, exp, category, sidequestText, statusOfTripText;
        EditText journeyNameEdit, startDateEdit, endDateEdit;
        ImageButton updateButton, shareButton, deleteButton;

        Button statusButton;

        public JourneyViewHolder(@NonNull View itemView) {
            super(itemView);
            journeyNameText = itemView.findViewById(R.id.journeyNameTextView);
            statusOfTripText = itemView.findViewById(R.id.statusOfTripText);
            startDateText = itemView.findViewById(R.id.startDateTextView);
            endDateText = itemView.findViewById(R.id.endDateTextView);
            exp = itemView.findViewById(R.id.exp);
            category = itemView.findViewById(R.id.category);
            sidequestText = itemView.findViewById(R.id.sideQuestName);

            journeyNameEdit = itemView.findViewById(R.id.journeyNameEdit);
            startDateEdit = itemView.findViewById(R.id.startDateEdit);
            endDateEdit = itemView.findViewById(R.id.endDateEdit);

            updateButton = itemView.findViewById(R.id.updateButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            shareButton = itemView.findViewById(R.id.shareButton);
            statusButton = itemView.findViewById(R.id.statusButton);
        }
    }
}
