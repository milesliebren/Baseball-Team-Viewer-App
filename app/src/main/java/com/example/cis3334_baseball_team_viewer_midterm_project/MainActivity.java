package com.example.cis3334_baseball_team_viewer_midterm_project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TabItem tabMLB;
    TabItem tabTripleA;
    TabItem tabDoubleA;
    RecyclerView recyclerView;
    TextView textViewStatus;
    MainViewModel viewModel;
    TeamAdapter teamAdapter;
    MLBTeams.League selectedLeague = MLBTeams.League.MLB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        teamAdapter = new TeamAdapter(this.getApplication(), viewModel);

        recyclerView = findViewById(R.id.recyclerView);
        textViewStatus = findViewById(R.id.textViewStatus);
        recyclerView.setAdapter(teamAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setUpTabListeners();
        observeData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove the observer when the activity is destroyed
        viewModel.getTeams().removeObservers(this);
    }

    private void setUpTabListeners() {
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabMLB = findViewById(R.id.tabMLB);
        tabTripleA = findViewById(R.id.tabTripleA);
        tabDoubleA = findViewById(R.id.tabDoubleA);
        TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Handle the tab selection event here
                int position = tab.getPosition();

                switch (position) {
                    case 0:
                        selectedLeague = MLBTeams.League.MLB;
                        break;
                    case 1:
                        selectedLeague = MLBTeams.League.TRIPLE_A;
                        break;
                    case 2:
                        selectedLeague = MLBTeams.League.DOUBLE_A;
                        break;
                }

                // Update the teams based on the selected league
                viewModel.switchToLevel(selectedLeague);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Handle unselected tab if needed
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Handle reselected tab if needed
            }
        };

        tabLayout.addOnTabSelectedListener(tabSelectedListener);
    }

    public void observeData() {
        viewModel.getTeams().observe(this, new Observer<List<MLBTeams.Team>>() {
            @Override
            public void onChanged(List<MLBTeams.Team> teams) {
                // Filter and display teams based on the selected league
                List<MLBTeams.Team> filteredTeams = filterTeamsByLeague(teams, selectedLeague);
                teamAdapter.submitList(filteredTeams);
            }
        });
    }

    private List<MLBTeams.Team> filterTeamsByLeague(List<MLBTeams.Team> teams, MLBTeams.League league) {
        List<MLBTeams.Team> filteredTeams = new ArrayList<>();
        for (MLBTeams.Team team : teams) {
            if (team.getLeague() == league) {
                filteredTeams.add(team);
            }
        }
        return filteredTeams;
    }
}