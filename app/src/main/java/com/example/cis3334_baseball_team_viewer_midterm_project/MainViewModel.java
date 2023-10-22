package com.example.cis3334_baseball_team_viewer_midterm_project;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
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

        currentTeams = new MediatorLiveData<>();
        currentTeams.setValue(mlbTeams.getValue()); // Set initial data to MLB teams

        // Add data sources to the MediatorLiveData
        currentTeams.addSource(mlbTeams, currentTeams::setValue);
        currentTeams.addSource(tripleATeams, currentTeams::setValue);
        currentTeams.addSource(doubleATeams, currentTeams::setValue);
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