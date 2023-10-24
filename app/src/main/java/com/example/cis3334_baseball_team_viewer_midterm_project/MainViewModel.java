package com.example.cis3334_baseball_team_viewer_midterm_project;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private TeamRepository teamRepository;
    private LiveData<List<MLBTeams.Team>> allTeams;
    private MutableLiveData<List<MLBTeams.Team>> mlbTeamsLiveData;

    public MainViewModel(Application application)
    {
        super(application);
        teamRepository = new TeamRepository(application);

        // Initialize MediatorLiveData
        mlbTeamsLiveData = new MediatorLiveData<>();

        // Fetch data from the repository and set it as the initial value of currentTeams
        allTeams = teamRepository.getTeamsFromAPI();

    }

    public LiveData<List<MLBTeams.Team>> getMLBTeams() {
        List<MLBTeams.Team> mlbTeams = new ArrayList<>();

        for (MLBTeams.Team team : allTeams.getValue()) {
            if (team.getLeague().name().equals("MLB")) {
                mlbTeams.add(team);
            }
        }

        mlbTeamsLiveData.setValue(mlbTeams);

        return mlbTeamsLiveData;
    }

    public void switchToLevel(MLBTeams.League league)
    {
        // Filter the teams based on the selected level and update currentTeams
        List<MLBTeams.Team> teamsByLevel = teamRepository.getTeamsFromAPI().getValue();
        mlbTeamsLiveData.setValue(teamsByLevel);
    }
}


