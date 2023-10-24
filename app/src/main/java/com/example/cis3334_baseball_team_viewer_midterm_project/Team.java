package com.example.cis3334_baseball_team_viewer_midterm_project;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Team
{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String division;
    private String firstYearOfPlay; // Change to match the JSON field
    private String stadiumName; // Change to match the JSON field
    private String webPage; // Change to match the JSON field
    private String stadiumAddress; // Change to match the JSON field
    private TeamLevel level;

    public Team(String name, String division, String firstYearOfPlay, String stadiumName, String webPage, String stadiumAddress, TeamLevel level) {
        this.name = name;
        this.division = division;
        this.firstYearOfPlay = firstYearOfPlay;
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

    public String getDivision() {
        return division;
    }

    public String getFirstYearOfPlay() {
        return firstYearOfPlay;
    }

    public String getWebPage() {
        return webPage;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public TeamLevel getLevel()
    {
        return this.level;
    }
}
