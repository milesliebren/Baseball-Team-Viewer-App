package com.example.cis3334_baseball_team_viewer_midterm_project;
import android.app.Application;

import androidx.lifecycle.LiveData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TeamRepository {
    private TeamDAO teamDAO;
    TeamDatabase database;

    public TeamRepository(Application application) {
        database = TeamDatabase.getDatabase(application);
        teamDAO = database.teamDAO();
    }

    public void fetchAndInsertDataFromAPI() {
        try {
            String apiUrl = "https://statsapi.mlb.com/api/v1/teams";
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder jsonResponse = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonResponse.append(line);
                }

                // Parse the JSON response
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(jsonResponse.toString());

                JsonNode teams = root.get("teams");
                for (JsonNode teamNode : teams) {
                    String teamName = teamNode.get("name").asText();
                    String manager = teamNode.get("managers").get(0).get("name").asText();
                    String stadiumName = teamNode.get("venue").get("name").asText();
                    String webPage = teamNode.get("officialSite").asText();
                    String stadiumAddress = teamNode.get("venue").get("address").asText();
                    String levelString = teamNode.get("teamCode").asText();

                    // Map the levelString to a TeamLevel enum
                    Team.TeamLevel level = Team.TeamLevel.UNKNOWN;

                    if ("MLB".equals(levelString)) {
                        level = Team.TeamLevel.MLB;
                    } else if ("AAA".equals(levelString)) {
                        level = Team.TeamLevel.TRIPLE_A;
                    } else if ("AA".equals(levelString)) {
                        level = Team.TeamLevel.DOUBLE_A;
                    }

                    // Create a Team object with the extracted data
                    Team team = new Team(teamName, manager, "", stadiumName, webPage, stadiumAddress, level);

                    // Insert the Team object into the database
                    database.databaseWriteExecutor.execute(() -> {
                        teamDAO.insert(team);
                    });
                }
            }
        } catch (Exception e) {
            // Handle exceptions, such as network errors or parsing errors
            e.printStackTrace();
        }
    }

    public void insertTeam(Team team) {
        TeamDatabase.databaseWriteExecutor.execute(() -> {
            teamDAO.insert(team);
        });
    }

    public LiveData<List<Team>> getMLBTeams() {
        return teamDAO.getMLB();
    }

    public LiveData<List<Team>> getTripleATeams() {
        return teamDAO.getTripleA();
    }

    public LiveData<List<Team>> getDoubleATeams() {
        return teamDAO.getDoubleA();
    }
}
