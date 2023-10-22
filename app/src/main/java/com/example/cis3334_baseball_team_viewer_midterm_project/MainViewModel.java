package com.example.cis3334_baseball_team_viewer_midterm_project;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private TeamRepository teamRepository;
    private LiveData<List<Team>> allTeams;
    private LiveData<List<Team>> mlbTeams;
    private LiveData<List<Team>> tripleATeams;
    private LiveData<List<Team>> doubleATeams;
    private MediatorLiveData<List<Team>> currentTeams;

    public MainViewModel(Application application) {
        super(application);
        teamRepository = new TeamRepository(application);

        mlbTeams = teamRepository.getMLBTeams();
        tripleATeams = teamRepository.getTripleATeams();
        doubleATeams = teamRepository.getDoubleATeams();


        // Initialize MediatorLiveData and set the initial value
        currentTeams = new MediatorLiveData<>();
        currentTeams.setValue(mlbTeams.getValue()); // Set initial data to MLB teams

        // Add data sources to the MediatorLiveData if they are not already added
        if (!currentTeams.hasActiveObservers()) {
            currentTeams.addSource(mlbTeams, newData -> currentTeams.setValue(newData));
            currentTeams.addSource(tripleATeams, newData -> currentTeams.setValue(newData));
            currentTeams.addSource(doubleATeams, newData -> currentTeams.setValue(newData));
        }

        // Observe the LiveData objects
        mlbTeams.observeForever(new Observer<List<Team>>() {
            @Override
            public void onChanged(List<Team> teams) {
                // Handle the updated MLB teams data here
                Log.d("MainViewModel", "MLB teams updated: " + teams);
            }
        });
    }

    public LiveData<List<Team>> getTeams() {
        return currentTeams;
    }

    public void switchToLevel(Team.TeamLevel level) {
        switch (level) {
            case MLB:
                currentTeams.setValue(mlbTeams.getValue());
                break;
            case TRIPLE_A:
                currentTeams.setValue(tripleATeams.getValue());
                break;
            case DOUBLE_A:
                currentTeams.setValue(doubleATeams.getValue());
                break;
        }
    }
}