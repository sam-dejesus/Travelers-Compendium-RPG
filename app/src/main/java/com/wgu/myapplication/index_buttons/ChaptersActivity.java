package com.wgu.myapplication.index_buttons;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wgu.myapplication.R;
import com.wgu.myapplication.controllers.JourneyDao;
import com.wgu.myapplication.database.TravelersCompendiumDatabase;
import com.wgu.myapplication.models.JourneyModel;
import com.wgu.myapplication.models.JourneyViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChaptersActivity extends AppCompatActivity {

    private ChaptersAdapter chaptersAdapter;
    private List<JourneyModel> allJourneys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Button sideQuestButton = findViewById(R.id.sideQuestButton);
        SearchView searchView = findViewById(R.id.searchView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TravelersCompendiumDatabase db = TravelersCompendiumDatabase.getDatabase(this);
        JourneyDao journeyDao = db.journeyDao();

        chaptersAdapter = new ChaptersAdapter(this, new ArrayList<>(), journeyDao);
        recyclerView.setAdapter(chaptersAdapter);

        JourneyViewModel journeyViewModel = new ViewModelProvider(this).get(JourneyViewModel.class);

        journeyViewModel.getAllJourneys().observe(this, journeys -> {
            allJourneys.clear();
            allJourneys.addAll(journeys);
            chaptersAdapter.setJourneys(journeys);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterJourneys(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterJourneys(newText);
                return true;
            }
        });

        sideQuestButton.setOnClickListener(v -> {
            Intent intent = new Intent(ChaptersActivity.this, SideQuestActivity.class);
            startActivity(intent);
        });
    }

    private void filterJourneys(String query) {
        List<JourneyModel> filteredList;

        if (query == null || query.isEmpty()) {
            filteredList = allJourneys;
        } else {
            filteredList = allJourneys.stream()
                    .filter(journey -> journey.getTitle().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }

        chaptersAdapter.setJourneys(filteredList);
    }
}
