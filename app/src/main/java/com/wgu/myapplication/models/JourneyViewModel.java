package com.wgu.myapplication.models;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.wgu.myapplication.database.TravelersCompendiumDatabase;

import java.util.List;

public class JourneyViewModel extends AndroidViewModel {
    private final LiveData<List<JourneyModel>> allJourneys;

    public JourneyViewModel(@NonNull Application application) {
        super(application);
        TravelersCompendiumDatabase db = TravelersCompendiumDatabase.getDatabase(application);
        allJourneys = db.journeyDao().getAllJourneys();
    }

    public LiveData<List<JourneyModel>> getAllJourneys() {
        return allJourneys;
    }
}
