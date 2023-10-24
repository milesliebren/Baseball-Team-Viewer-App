package com.example.cis3334_baseball_team_viewer_midterm_project;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private TeamRepository teamRepository;
    private LiveData<List<MLBTeams.Team>> allTeams;
    private MutableLiveData<List<MLBTeams.Team>> mlbTeamsLiveData;

    public MainViewModel(Application application) {
        super(application);
        teamRepository = new TeamRepository(application);
        allTeams = teamRepository.getTeamsFromAPI();

        mlbTeamsLiveData = new MutableLiveData<>();
    }

    public LiveData<List<MLBTeams.Team>> getLiveMLBTeams() {
        return mlbTeamsLiveData;
    }

    public void switchToLevel(MLBTeams.League league) {
        List<MLBTeams.Team> filteredTeams = new ArrayList<>();

        for (MLBTeams.Team team : allTeams.getValue()) {
            if (team.getLeague() == league) {
                filteredTeams.add(team);
            }
        }
        Log.d("MainViewModel", "Number of filtered teams: " + filteredTeams.size());

        mlbTeamsLiveData.setValue(filteredTeams);
    }
}
