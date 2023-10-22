package com.example.cis3334_baseball_team_viewer_midterm_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TabHost.TabSpec;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private TabItem tabMLB;
    private TabItem tabTripleA;
    private TabItem tabDoubleA;
    private RecyclerView recyclerView;
    MainViewModel viewModel;
    private TeamAdapter teamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        teamAdapter = new TeamAdapter(this.getApplication(), viewModel);
        tabMLB = findViewById(R.id.tabMLB);
        tabTripleA = findViewById(R.id.tabTripleA);
        tabDoubleA = findViewById(R.id.tabDoubleA);
        recyclerView = findViewById(R.id.recyclerView);

        setUpTabListeners();
        observeData();
    }

    private void setUpTabListeners() {
        TabLayout tabLayout = findViewById(R.id.tabLayout); // Use the correct ID for your TabLayout
        TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Handle the tab selection event here
                int position = tab.getPosition();

                switch (position) {
                    case 0:
                        viewModel.switchToLevel(Team.TeamLevel.MLB);
                        break;
                    case 1:
                        viewModel.switchToLevel(Team.TeamLevel.TRIPLE_A);
                        break;
                    case 2:
                        viewModel.switchToLevel(Team.TeamLevel.DOUBLE_A);
                        break;
                }
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

    public void observeData()
    {
        viewModel.getTeams().observe(this, new Observer<List<Team>>() {
            @Override
            public void onChanged(List<Team> teams) {

                teamAdapter.submitList(teams);

            }
        });
    }
}