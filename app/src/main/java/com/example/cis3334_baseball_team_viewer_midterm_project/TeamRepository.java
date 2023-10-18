package com.example.cis3334_baseball_team_viewer_midterm_project;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TeamRepository {
    private TeamDAO teamDAO;
    private TeamDatabase teamDatabase;

    private LiveData<List<Team>> allTeams;
    private LiveData<List<Team>> mlbTeams;

    private LiveData<List<Team>> tripleATeams;
    private LiveData<List<Team>> doubleATeams;

    public TeamRepository(Application application) {
        TeamDatabase database = TeamDatabase.getDatabase(application);
        teamDAO = database.teamDAO();
        allTeams = teamDAO.getAllTeams();
        mlbTeams = teamDAO.getMLB();
        tripleATeams = teamDAO.getTripleA();
        doubleATeams = teamDAO.getDoubleA();
    }

    public LiveData<List<Team>> getAllTeams() {
        return allTeams;
    }

    public LiveData<List<Team>> getMLBTeams() {
        return mlbTeams;
    }

    public LiveData<List<Team>> getTripleATeams() {
        return tripleATeams;
    }

    public LiveData<List<Team>> getDoubleATeams() {
        return doubleATeams;
    }

    // You can also add methods to insert, update, or delete teams here
    public void insertTeam(Team team) {
        TeamDatabase.databaseWriteExecutor.execute(() -> {
            teamDAO.insert(team);
        });
    }
}
