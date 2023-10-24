package com.example.cis3334_baseball_team_viewer_midterm_project;

import java.util.ArrayList;
public class MLBTeams {
    private String copyright;
    ArrayList <Team> teams = new ArrayList <Team>();

    public class MyTeam{
        public String allStarStatus;
        public int id;
        public String name;
        public String link;
        public int season;
        public Venue venue;
        public String teamCode;
        public String fileCode;
        public String abbreviation;
        public String teamName;
        public String locationName;
        public String firstYearOfPlay;
        public League league;
        public Division division;
        public Sport sport;
        public String shortName;
        public String parentOrgName;
        public int parentOrgId;
        public String franchiseName;
        public String clubName;
        public boolean active;
        public SpringLeague springLeague;
        public SpringVenue springVenue;
    }
    public class Venue{
        public int id;
        public String name;
        public String link;
    }
    public class Division{
        public int id;
        public String name;
        public String link;
    }
    public class League{
        public int id;
        public String name;
        public String link;
    }
    public class Root{
        public String copyright;
        public ArrayList<Team> teams;
    }
    public class Sport{
        public int id;
        public String link;
        public String name;
    }
    public class SpringLeague{
        public int id;
        public String name;
        public String link;
        public String abbreviation;
    }
    public class SpringVenue{
        public int id;
        public String link;
    }
}
