package com.example.cis3334_baseball_team_viewer_midterm_project;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel
{
    private TeamRepository teamRepository;
    private LiveData<List<Team>> allTeams;

    public MainViewModel (Application application) {
        super(application);
        teamRepository = new TeamRepository(application);
        allTeams = teamRepository.getAllTeams();
    }

    public void insert(Team team)
    {
        teamRepository.insertTeam(team);
    }

    // Method to get LiveData for MLB teams
    public LiveData<List<Team>> getMLBTeams() {
        return teamRepository.getMLBTeams(); // Replace with your repository method.
    }

    // Method to get LiveData for Triple A teams
    public LiveData<List<Team>> getTripleATeams() {
        return teamRepository.getTripleATeams(); // Replace with your repository method.
    }

    // Method to get LiveData for Double A teams
    public LiveData<List<Team>> getDoubleATeams() {
        return teamRepository.getDoubleATeams(); // Replace with your repository method.
    }

    public void switchToLevel(Team.TeamLevel level) {
        // Update your LiveData to the appropriate level based on the selected tab.
        if (level == Team.TeamLevel.MLB) {
            allTeams = getMLBTeams();
        } else if (level == Team.TeamLevel.TRIPLE_A) {
            allTeams = getTripleATeams();
        } else if (level == Team.TeamLevel.DOUBLE_A) {
            allTeams = getDoubleATeams();
        }
    }

}
