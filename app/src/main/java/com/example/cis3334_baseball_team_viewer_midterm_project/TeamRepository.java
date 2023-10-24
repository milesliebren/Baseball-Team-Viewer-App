package com.example.cis3334_baseball_team_viewer_midterm_project;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeamRepository {
    private RequestQueue requestQueue;
    private Gson gson;
    private List<Team> allTeams;

    public TeamRepository(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        gson = new Gson();
        allTeams = new ArrayList<>();
        Log.d("repository", "Repository Created");
    }

    public LiveData<List<Team>> getTeamsFromAPI() {
        String apiUrl = "https://statsapi.mlb.com/api/v1/teams";
        Log.d("repository", "Accessing Data...");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<Team> teams = new ArrayList<>();

                            // Iterate through the JSON array and create Team objects
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject teamJson = response.getJSONObject(i);

                                // Extract data from the JSON object
                                String name = teamJson.getString("name");
                                String division = teamJson.getString("division");
                                String firstYearOfPlay = teamJson.getString("firstYearOfPlay");
                                String stadiumName = teamJson.getJSONObject("venue").getString("name");
                                String webPage = teamJson.getString("link");
                                String stadiumAddress = ""; // Extract stadium address from JSON if available
                                Team.TeamLevel level = Team.TeamLevel.UNKNOWN; // Set the appropriate level based on your data

                                // Create a Team object and add it to the list
                                Team team = new Team(name, division, firstYearOfPlay, stadiumName, webPage, stadiumAddress, level);
                                teams.add(team);
                            }

                            allTeams.clear(); // Clear the existing teams
                            allTeams.addAll(teams); // Add new teams
                        } catch (JSONException e) {
                            // Handle JSON parsing error
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

        requestQueue.add(jsonArrayRequest);

        // Create LiveData for allTeams and return it
        MutableLiveData<List<Team>> liveData = new MutableLiveData<>();
        liveData.setValue(allTeams);
        return liveData;
    }

    public List<Team> getTeamsByLevel(Team.TeamLevel level) {
        List<Team> teamsByLevel = new ArrayList<>();

        for (Team team : allTeams) {
            if (team.getLevel() == level) {
                teamsByLevel.add(team);
            }
        }

        return teamsByLevel;
    }
}