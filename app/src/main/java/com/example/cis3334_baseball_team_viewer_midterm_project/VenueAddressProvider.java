package com.example.cis3334_baseball_team_viewer_midterm_project;

import android.content.Context;
import android.util.Log;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import java.util.Arrays;
import java.util.List;

public class VenueAddressProvider {

    private final PlacesClient placesClient;

    public VenueAddressProvider(Context context) {
        // Initialize Places SDK
        Places.initialize(context, context.getString(R.string.google_maps_key));
        Log.d("VAP", "VAP Initialized");
        placesClient = Places.createClient(context);
    }

    public void getVenueAddressAndUpdateMLBTeamAddress(final MLBTeams.Team team) {
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ADDRESS);

        // Create a FetchPlaceRequest
        FetchPlaceRequest request = FetchPlaceRequest.newInstance(team.venue.id, placeFields);

        // Perform the place details request
        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
            Place place = response.getPlace();
            String address = place.getAddress();

            // Update the MLBTeams.Team's venue address
            team.venue.address = address;
            Log.d("VAP", "Added address: " + address);
        }).addOnFailureListener((exception) -> {
            // Handle errors, e.g., the address couldn't be fetched
            Log.e("VAP", "Failed to Update Address " + team.venue);
        });
    }
}



