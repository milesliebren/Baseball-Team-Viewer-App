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
    TextView textViewRecord;
    TextView textViewManager;
    TextView textViewStadium;
    ImageView imageViewLogo;
    Button buttonRoster;
    Button buttonDirections;


    public TeamViewHolder(@NonNull View itemView)
    {
        super(itemView);

        textViewName = itemView.findViewById(R.id.textViewName);
        textViewRecord = itemView.findViewById(R.id.textViewRecord);
        textViewManager = itemView.findViewById(R.id.textViewManager);
        textViewStadium = itemView.findViewById(R.id.textViewStadium);
        imageViewLogo = itemView.findViewById(R.id.imageViewLogo);
        buttonRoster = itemView.findViewById(R.id.buttonRoster);
        buttonDirections = itemView.findViewById(R.id.buttonStadiumDirections);
    }

    public void bindData(Team team)
    {
        // Set data from the 'team' object to the respective views
        textViewName.setText(team.getName());
        textViewRecord.setText(team.getRecord());
        textViewManager.setText(team.getManager());
        textViewStadium.setText(team.getStadiumName());

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

