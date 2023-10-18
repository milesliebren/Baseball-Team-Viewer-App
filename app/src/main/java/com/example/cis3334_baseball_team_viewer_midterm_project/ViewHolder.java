package com.example.cis3334_baseball_team_viewer_midterm_project;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder
{
    TextView textViewName;
    TextView textViewRecord;
    TextView textViewManager;
    TextView textViewStadium;
    ImageView imageViewLogo;
    Button buttonRoster;
    Button buttonDirections;

    public ViewHolder(@NonNull View itemView)
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
}
