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
import java.util.List;

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
                            Log.d("repository", "JSON Array Length: " + teamsArray.length()); //706 elements

                            for (int i = 0; i < teamsArray.length(); i++) {
                                JSONObject teamJson = teamsArray.getJSONObject(i);
                                Log.d("repository", "Parsing team at position " + i);
//
//
//                                    // Extract data from the JSON object
//                                    String name = teamJson.getString("name");
//                                    String divisionName = teamJson.getJSONObject("division").getString("name");
//
//                                    MLBTeams.Division division = new MLBTeams.Division(divisionName);
//
//                                    division.name = divisionName;
//
//                                    String webPage = "http://gdx.mlb.com" + teamJson.getString("link");

                                MLBTeams.Division division = new MLBTeams.Division("");
                                String name = "";
                                String webPage = "";
                                    String stadiumName = teamJson.getJSONObject("venue").getString("name");
                                    String stadiumAddress = teamJson.getJSONObject("venue").optString("address", "");
                                    MLBTeams.Venue venue = new MLBTeams.Venue(stadiumName);
                                    venue.address = stadiumAddress;

                                    String firstYearOfPlay = teamJson.getString("firstYearOfPlay");

                                    // Handle unknown leagues gracefully
                                    String leagueName = teamJson.getJSONObject("league").getString("name");
                                    MLBTeams.League league = getLeagueFromString(leagueName);

                                    MLBTeams.Team team = new MLBTeams.Team(name, webPage, venue, firstYearOfPlay, league, division);
                                    Log.d("repository", "Added Team: " + team.name);
                                    teams.add(team);
                            }

                            //reset allTeams list
                            allTeams.clear();
                            allTeams.addAll(teams);
                            Log.d("repository", "allTeams size: " + allTeams.size());

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

        requestQueue.add(jsonObjectRequest);

        // Create LiveData for allTeams and return it
        MutableLiveData<List<MLBTeams.Team>> liveData = new MutableLiveData<>();
        liveData.setValue(allTeams);
        return liveData;
    }

    private MLBTeams.League getLeagueFromString(String leagueName) {
        try {
            return MLBTeams.League.valueOf(leagueName.replace(" ", "_").toUpperCase());
        } catch (IllegalArgumentException e) {
            // Handle unknown league names, you can return a default value here.
            return MLBTeams.League.UNKNOWN;
        }
    }
}