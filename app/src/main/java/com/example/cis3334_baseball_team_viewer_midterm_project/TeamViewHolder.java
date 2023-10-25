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

import com.squareup.picasso.Picasso;

public class TeamViewHolder extends RecyclerView.ViewHolder {
    private TextView textViewName;
    private TextView textViewDivision;
    private TextView textViewFirstYearOfPlay;
    private TextView textViewStadium;
    //private ImageView imageViewLogo;
    public Button buttonWebsite;
    public Button buttonDirections;

    public TeamViewHolder(@NonNull View itemView, MLBTeams.Team team) {
        super(itemView);
        textViewName = itemView.findViewById(R.id.textViewName);
        textViewDivision = itemView.findViewById(R.id.textViewDivision);
        textViewFirstYearOfPlay = itemView.findViewById(R.id.textViewFirstYearOfPLay);
        textViewStadium = itemView.findViewById(R.id.textViewStadium);
        //imageViewLogo = itemView.findViewById(R.id.imageViewLogo);
        buttonWebsite = itemView.findViewById(R.id.buttonWebsite);
        buttonDirections = itemView.findViewById(R.id.buttonStadiumDirections);
        buttonWebsite = itemView.findViewById(R.id.buttonWebsite);


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
                openMap(Uri.parse(team.venue.address));
            }
        });
    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        Log.d("viewHolder","Opening web page: " + webpage);
        if (webIntent.resolveActivity(itemView.getContext().getPackageManager()) != null) {
            itemView.getContext().startActivity(webIntent);
        }
    }

    public void openMap(Uri location) {
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
        Log.d("viewHolder","Opening map: " + location);
        if (mapIntent.resolveActivity(itemView.getContext().getPackageManager()) != null) {
            itemView.getContext().startActivity(mapIntent);
        }
    }

    public void bindData(MLBTeams.Team team) {
        textViewName.setText(team.name);
        textViewDivision.setText(team.division.name);
        textViewFirstYearOfPlay.setText("Established In: " + team.firstYearOfPlay);
        textViewStadium.setText(team.venue.name);

        buttonWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage(team.link);
            }
        });

        buttonDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap(Uri.parse(team.venue.address));
            }
        });
    }
}