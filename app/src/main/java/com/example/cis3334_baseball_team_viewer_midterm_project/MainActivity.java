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
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity
{
    private TabItem tabMLB;
    private TabItem tabTripleA;
    private TabItem tabDoubleA;

    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        tabMLB = findViewById(R.id.tabMLB);
        tabTripleA = findViewById(R.id.tabTripleA);
        tabDoubleA = findViewById(R.id.tabDoubleA);
        setUpTabListeners();
    }

    private void setUpTabListeners()
    {
        tabMLB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.switchToLevel(Team.TeamLevel.MLB);
            }
        });

        tabDoubleA.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                viewModel.switchToLevel(Team.TeamLevel.DOUBLE_A);
            }
        });

        tabTripleA.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                viewModel.switchToLevel(Team.TeamLevel.TRIPLE_A);
            }
        });
    }
}