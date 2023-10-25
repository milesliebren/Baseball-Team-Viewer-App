package com.example.cis3334_baseball_team_viewer_midterm_project;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Application;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamViewHolder>
{
    List<MLBTeams.Team> mlbTeams = new ArrayList<MLBTeams.Team>();
    public TeamAdapter(Application application, MainViewModel mainViewModel)
    {
        mlbTeams = new ArrayList<>();
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent, false);
        return new TeamViewHolder(view, mlbTeams.get(viewType));
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        MLBTeams.Team team = mlbTeams.get(position);
        holder.bindData(team);
    }

    @Override
    public int getItemCount() {
        return mlbTeams.size();
    }

    public void submitList(@NonNull List<MLBTeams.Team> teams)
    {
        this.mlbTeams = teams;
    }
}
