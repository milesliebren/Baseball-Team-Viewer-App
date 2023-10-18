package com.example.cis3334_baseball_team_viewer_midterm_project;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class TeamAdapter extends RecyclerView.Adapter<ViewHolder>
{
    ArrayList<Team> mlbTeams = new ArrayList<Team>();
    public TeamAdapter(Application application, MainViewModel mainViewModel)
    {
        mlbTeams = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
