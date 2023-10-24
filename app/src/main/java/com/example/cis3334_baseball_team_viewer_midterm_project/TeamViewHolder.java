package com.example.cis3334_baseball_team_viewer_midterm_project;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TeamViewHolder extends RecyclerView.ViewHolder
{
    TextView textViewName;
    TextView textViewDivision;
    TextView textViewFirstYearOfPlay;
    TextView textViewStadium;
    ImageView imageViewLogo;
    Button buttonRoster;
    Button buttonDirections;


    public TeamViewHolder(@NonNull View itemView)
    {
        super(itemView);

        textViewName = itemView.findViewById(R.id.textViewName);
        textViewDivision = itemView.findViewById(R.id.textViewDivision);
        textViewFirstYearOfPlay = itemView.findViewById(R.id.textViewFirstYearOfPLay);
        textViewStadium = itemView.findViewById(R.id.textViewStadium);
        imageViewLogo = itemView.findViewById(R.id.imageViewLogo);
        buttonRoster = itemView.findViewById(R.id.buttonRoster);
        buttonDirections = itemView.findViewById(R.id.buttonStadiumDirections);
    }

    public void bindData(MLBTeams.Team team)
    {
        // Set data from the 'team' object to the respective views
        textViewName.setText(team.name);
        textViewStadium.setText(team.venue.address);

        // You can set the 'imageViewLogo' here with an image based on 'team' data
        // For example: imageViewLogo.setImageResource(R.drawable.some_image);

        // Set click listeners for buttons or perform other actions if needed
        buttonRoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle button click
            }
        });

        buttonDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle button click
            }
        });
    }
}

