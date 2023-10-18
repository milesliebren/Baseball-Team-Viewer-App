package com.example.cis3334_baseball_team_viewer_midterm_project;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public abstract interface TeamDAO {
    @Insert
    void insert(Team team);
    @Delete
    void delete(Team team);
    @Query("SELECT * FROM Team WHERE level = 'MLB'")
    LiveData<List<Team>> getMLB();
    @Query("SELECT * FROM Team WHERE level = 'Triple A'")
    LiveData<List<Team>> getTripleA();
    @Query("SELECT * FROM Team WHERE level = 'Double A'")
    LiveData<List<Team>> getDoubleA();
    @Query("SELECT * FROM Team")
    LiveData<List<Team>> getAllTeams();
}
