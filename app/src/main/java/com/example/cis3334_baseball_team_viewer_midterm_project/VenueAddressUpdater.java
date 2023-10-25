package com.example.cis3334_baseball_team_viewer_midterm_project;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class VenueAddressUpdater {
    private static String OPENSTREETMAP_API_BASE;
    public VenueAddressUpdater()
    {
        OPENSTREETMAP_API_BASE = "https://nominatim.openstreetmap.org/search";
        Log.d("VAU", "VAU Created...");
    }
    public interface AddressUpdateListener
    {
        void onAddressUpdated(MLBTeams.Venue venue, String address);
        void onError(String errorMessage);
    }

    public void updateVenueAddressAsync(MLBTeams.Venue venue, String locationName, AddressUpdateListener listener) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                HttpURLConnection conn = null;
                BufferedReader reader = null;
                Log.d("VAU", "Update Address Method Called.");
                try {
                    // Construct the URL for the OpenStreetMap Nominatim API
                    String encodedLocationName = URLEncoder.encode(locationName, "UTF-8");
                    String url = OPENSTREETMAP_API_BASE + "?q=" + encodedLocationName + "&format=json";

                    URL apiUrl = new URL(url);
                    conn = (HttpURLConnection) apiUrl.openConnection();
                    conn.setRequestMethod("GET");

                    // Read the response
                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // Parse the JSON response
                    JSONArray resultsArray = new JSONArray(response.toString());

                    if (resultsArray.length() > 0) {
                        JSONObject firstResult = resultsArray.getJSONObject(0);
                        String address = firstResult.optString("display_name", "");

                        // Notify the listener with the updated address
                        listener.onAddressUpdated(venue, address);
                    } else {
                        // Handle no results found
                        listener.onError("No results found for location name: " + locationName);
                    }
                } catch (IOException | JSONException e) {
                    // Handle exceptions
                    listener.onError("Error updating venue address: " + e.getMessage());
                } finally {
                    if (conn != null) {
                        conn.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            }
        }.execute();
    }
}
