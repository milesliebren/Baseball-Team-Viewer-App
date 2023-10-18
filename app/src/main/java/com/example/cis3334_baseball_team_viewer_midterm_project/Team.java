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

    public enum TeamLevel {
        MLB, TRIPLE_A, DOUBLE_A
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public String getStadiumAddress() {
        return stadiumAddress;
    }

    public void setStadiumAddress(String stadiumAddress) {
        this.stadiumAddress = stadiumAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLevel(TeamLevel level) {
        this.level = level;
    }

    public String getLevelString() {
        switch (level) {
            case MLB:
                return "MLB";
            case TRIPLE_A:
                return "Triple A";
            case DOUBLE_A:
                return "Double A";
            default:
                return "Unknown";
        }
    }

    public TeamLevel getLevel()
    {
        return this.level;
    }
}
