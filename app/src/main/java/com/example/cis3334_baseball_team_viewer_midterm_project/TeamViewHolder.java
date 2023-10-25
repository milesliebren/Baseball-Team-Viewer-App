package com.example.cis3334_baseball_team_viewer_midterm_project;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.NavigableMap;

public class TeamViewHolder extends RecyclerView.ViewHolder {
    private TextView textViewName;
    private TextView textViewDivision;
    private TextView textViewFirstYearOfPlay;
    private TextView textViewStadium;
    public Button buttonWebsite;
    public Button buttonDirections;
    public Button buttonRoster;
    public TeamRepository repository;

    public TeamViewHolder(@NonNull View itemView, MLBTeams.Team team) {
        super(itemView);
        textViewName = itemView.findViewById(R.id.textViewName);
        textViewDivision = itemView.findViewById(R.id.textViewDivision);
        textViewFirstYearOfPlay = itemView.findViewById(R.id.textViewFirstYearOfPLay);
        textViewStadium = itemView.findViewById(R.id.textViewStadium);
        buttonRoster = itemView.findViewById(R.id.buttonRoster);
        buttonWebsite = itemView.findViewById(R.id.buttonWebsite);
        buttonDirections = itemView.findViewById(R.id.buttonStadiumDirections);
        buttonWebsite = itemView.findViewById(R.id.buttonWebsite);
        repository = new TeamRepository(itemView.getContext());

        // Set click listeners for the buttons
        buttonWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage(team.link);
            }
        });

        buttonDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Request to update the venue address
                updateVenueAddressForTeam(team, new VenueAddressUpdateListener() {
                    @Override
                    public void onVenueAddressUpdated(String address) {
                        // Address is updated, open the map with the updated address
                        openMap(Uri.parse("geo:0,0?q=" + address));
                    }

                    @Override
                    public void onError(String errorMessage) {
                        // Handle the error if needed
                        Log.e("Directions", "Error updating venue address: " + errorMessage);
                    }
                });
            }
        });

        buttonRoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage(Uri.parse(team.link) + "/roster");
            }
        });
    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        Log.d("viewHolderWebPage","Opening web page: " + webpage);
        if (webIntent.resolveActivity(itemView.getContext().getPackageManager()) != null) {
            itemView.getContext().startActivity(webIntent);
        }
    }

    public void openMap(Uri location)
    {
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
        if (mapIntent.resolveActivity(itemView.getContext().getPackageManager()) != null) {
            Log.d("viewHolderMap","Opening Map: " + location);
            itemView.getContext().startActivity(mapIntent);
        }
    }

    public void bindData(MLBTeams.Team team) {
        textViewName.setText(team.name);
        textViewDivision.setText(team.division.name);
        textViewFirstYearOfPlay.setText("Est." + team.firstYearOfPlay);
        textViewStadium.setText(team.venue.name);
    }

    public void updateVenueAddressForTeam(MLBTeams.Team team, VenueAddressUpdateListener listener) {
        VenueAddressUpdater venueAddressUpdater = new VenueAddressUpdater();
        venueAddressUpdater.updateVenueAddressAsync(team.venue, team.venue.name, new VenueAddressUpdateListener() {
            @Override
            public void onVenueAddressUpdated(String address) {
                team.venue.setAddress(address);
                Log.d("repository", "Updating Address for venue: " + team.venue.name + " | " + team.venue.address);
                listener.onVenueAddressUpdated(team.venue.address);
            }

            @Override
            public void onError(String errorMessage) {
                listener.onError(errorMessage);
            }
        });
    }

}