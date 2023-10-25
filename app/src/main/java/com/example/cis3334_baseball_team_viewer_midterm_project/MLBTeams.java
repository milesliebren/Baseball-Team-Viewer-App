package com.example.cis3334_baseball_team_viewer_midterm_project;

import java.util.ArrayList;

public class MLBTeams {
    private String copyright;
    ArrayList<Team> teams = new ArrayList<>();

    public static class Team {
        public String name;
        public String link;
        public Venue venue;
        public String firstYearOfPlay;
        public League league;
        public Division division;

        public Team(String name, String link, Venue venue, String firstYearOfPlay, League league, Division division)
        {
            this.name = name;
            this.link = link;
            this.venue = venue;
            this.firstYearOfPlay = firstYearOfPlay;
            this.league = league;
            this.division = division;
        }

        public League getLeague()
        {
            return this.league;
        }
    }

    public static class Venue {
        public String address;
        public String name;
        public String id;
        public Venue(String name, String address)
        {
            this.name = name;
            this.address = address;
        }
        public void setAddress(String address)
        {
            this.address = address;
        }
        public String getAddress()
        {
            return address;
        }

    }

    public static class Division {
        public String name;
        public Division(String name)
        {
            this.name = name;
        }
    }

    public enum League {
        MLB, DOUBLE_A, TRIPLE_A, UNKNOWN;
    }
}