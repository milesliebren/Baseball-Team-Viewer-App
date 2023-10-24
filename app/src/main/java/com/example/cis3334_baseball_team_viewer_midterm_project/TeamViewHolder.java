package com.example.cis3334_baseball_team_viewer_midterm_project;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TeamViewHolder extends RecyclerView.ViewHolder {
    private TextView textViewName;
    private TextView textViewDivision;
    private TextView textViewFirstYearOfPlay;
    private TextView textViewStadium;
    private ImageView imageViewLogo;
    private Button buttonRoster;
    private Button buttonDirections;

    public TeamViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewName = itemView.findViewById(R.id.textViewName);
        textViewDivision = itemView.findViewById(R.id.textViewDivision);
        textViewFirstYearOfPlay = itemView.findViewById(R.id.textViewFirstYearOfPLay);
        textViewStadium = itemView.findViewById(R.id.textViewStadium);
        imageViewLogo = itemView.findViewById(R.id.imageViewLogo);
        buttonRoster = itemView.findViewById(R.id.buttonRoster);
        buttonDirections = itemView.findViewById(R.id.buttonStadiumDirections);
    }

    public void bindData(MLBTeams.Team team) {
        textViewName.setText(team.name);
        textViewDivision.setText(team.division.name);
        textViewFirstYearOfPlay.setText("Established In: " + team.firstYearOfPlay);
        textViewStadium.setText(team.venue.name);
        imageViewLogo.setImageResource(R.drawable.download);
    }
}