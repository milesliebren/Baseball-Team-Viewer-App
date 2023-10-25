package com.example.cis3334_baseball_team_viewer_midterm_project;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TeamRepository {
    private RequestQueue requestQueue;
    private Gson gson;
    private List<MLBTeams.Team> allTeams;

    public TeamRepository(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        gson = new Gson();
        allTeams = new ArrayList<>();
        Log.d("repository", "Repository Created");
    }

    public LiveData<List<MLBTeams.Team>> getTeamsFromAPI() {
        String apiUrl = "https://statsapi.mlb.com/api/v1/teams";
        Log.d("repository", "Accessing Data...");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray teamsArray = response.getJSONArray("teams");
                            List<MLBTeams.Team> teams = new ArrayList<>();
                            Log.d("repository", "JSON Array Length: " + teamsArray.length());

                            for (int i = 0; i < teamsArray.length(); i++) {
                                JSONObject teamJson = teamsArray.getJSONObject(i);

                                String name = teamJson.getString("name");

                                String firstYearOfPlay = teamJson.optString("firstYearOfPlay", "");


                                // Check if "division" field exists before retrieving its value
                                JSONObject division = teamJson.optJSONObject("division");
                                String divisionName = (division != null) ? division.optString("name", "") : "";

                                JSONObject league = teamJson.getJSONObject("league");
                                String leagueName = league.optString("name", "");

                                MLBTeams.Division divisionObject = new MLBTeams.Division(divisionName);

                                JSONObject venue = teamJson.getJSONObject("venue");
                                String stadiumName = venue.optString("name", "");
                                String stadiumAddress = venue.optString("address", "");
                                MLBTeams.Venue venueObject = new MLBTeams.Venue(stadiumName, stadiumAddress);
                                venueObject.setAddress(stadiumAddress);

                                //Log.d("repository", "Added Venue: " + venueObject.name + venueObject.getAddress());

                                MLBTeams.League leagueObject = getLeagueFromString(leagueName);

                                if (!leagueObject.equals(MLBTeams.League.UNKNOWN)) {
                                    String webPage = getLink(name, leagueObject);
                                    MLBTeams.Team team = new MLBTeams.Team(name, webPage, venueObject, firstYearOfPlay, leagueObject, divisionObject);
                                    teams.add(team);
                                }
                            }

                            // Reset allTeams list
                            allTeams.clear();
                            allTeams.addAll(teams);
                            //Log.d("repository", "allTeams size: " + allTeams.size());
                        } catch (JSONException e) {
                            // Handle JSON parsing error
                            Log.e("repository", "JSON Parsing Error: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("repository", "Data Access Error: " + error.getMessage());
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);

        // Create LiveData for allTeams and return it
        MutableLiveData<List<MLBTeams.Team>> liveData = new MutableLiveData<>();
        updateVenueAddressesForTeams(allTeams);
        liveData.setValue(allTeams);
        return liveData;
    }

    private String getLink(String name, MLBTeams.League league) {
        if (league.equals(MLBTeams.League.MLB)) {
            String[] nameStringArray = name.toLowerCase().replace(" ", "-").split("-");
            String regName = nameStringArray[nameStringArray.length - 1];

            //get special cases
            if (name.equalsIgnoreCase("Boston Red Sox"))
                regName = "redsox";
            else if (name.equalsIgnoreCase("Chicago White Sox"))
                regName = "whitesox";
            else if (regName.equalsIgnoreCase("jays"))
                regName = "bluejays";

            //Log.d("repository", "Binding Page: \"https://www.mlb.com/" + regName);
            return "https://www.mlb.com/" + regName; //get last word in the name (i.e. Cincinnati Reds => Reds)
        } else {
            //Log.d("repository","Binding page: " + "https://www.milb.com/" + name.toLowerCase().replace(" ", "-"));
            return "https://www.milb.com/" + name.toLowerCase().replace(" ", "-");
        }
    }

    private MLBTeams.League getLeagueFromString(String leagueName) {
        String normalizedLeagueName = leagueName.toUpperCase();

        // Define league-level mappings
        Map<String, MLBTeams.League> leagueLevelMap = new HashMap<>();
        leagueLevelMap.put("AMERICAN LEAGUE", MLBTeams.League.MLB);
        leagueLevelMap.put("NATIONAL LEAGUE", MLBTeams.League.MLB);
        leagueLevelMap.put("PACIFIC COAST LEAGUE", MLBTeams.League.TRIPLE_A);
        leagueLevelMap.put("INTERNATIONAL LEAGUE", MLBTeams.League.TRIPLE_A);
        leagueLevelMap.put("MEXICAN LEAGUE", MLBTeams.League.TRIPLE_A);
        leagueLevelMap.put("EASTERN LEAGUE", MLBTeams.League.DOUBLE_A);
        leagueLevelMap.put("SOUTHERN LEAGUE", MLBTeams.League.DOUBLE_A);
        leagueLevelMap.put("TEXAS LEAGUE", MLBTeams.League.DOUBLE_A);

        // Check if the leagueName exists in the map, and return the corresponding enum
        if (leagueLevelMap.containsKey(normalizedLeagueName)) {
            return leagueLevelMap.get(normalizedLeagueName);
        }

        // Handle unknown league names, you can return a default value here.
        return MLBTeams.League.UNKNOWN;
    }
    private void updateVenueAddressesForTeams(List<MLBTeams.Team> teams) {
        for (MLBTeams.Team team : teams) {
            String locationName = team.venue.name; // or you may use another field to specify the location name
            if (locationName != null && !locationName.isEmpty()) {
                // Use VenueAddressUpdater to get the address and update the venue
                if (VenueAddressUpdater.updateVenueAddress(team.venue, locationName)) {
                    // Log success or handle errors
                    Log.d("TeamRepository", "Updated address for venue: " + team.venue.address);
                } else {
                    // Handle errors, e.g., address not found for the location name
                }
            }
        }
    }
}