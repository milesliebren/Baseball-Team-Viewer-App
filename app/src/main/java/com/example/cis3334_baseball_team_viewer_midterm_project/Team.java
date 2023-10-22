package com.example.cis3334_baseball_team_viewer_midterm_project;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Team
{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String manager;
    private String record;
    private String stadiumName;
    private String webPage;
    private String stadiumAddress;
    private TeamLevel level;

    public Team(String name, String manager, String record, String stadiumName, String webPage, String stadiumAddress, TeamLevel level) {
        this.name = name;
        this.manager = manager;
        this.record = record;
        this.stadiumName = stadiumName;
        this.webPage = webPage;
        this.stadiumAddress = stadiumAddress;
        this.level = level;
    }

    public enum TeamLevel
    {
        MLB, TRIPLE_A, DOUBLE_A, UNKNOWN
    }

    public String getName() {
        return name;
    }

    public String getManager() {
        return manager;
    }

    public String getRecord() {
        return record;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public TeamLevel getLevel()
    {
        return this.level;
    }
}
