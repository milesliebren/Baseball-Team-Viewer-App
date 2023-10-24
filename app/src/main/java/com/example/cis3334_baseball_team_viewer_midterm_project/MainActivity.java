package com.example.cis3334_baseball_team_viewer_midterm_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity
{
    TabItem tabMLB;
    TabItem tabTripleA;
    TabItem tabDoubleA;
    RecyclerView recyclerView;
    TextView textViewStatus;
    MainViewModel viewModel;
    TeamAdapter teamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new MainViewModel(this.getApplication());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        teamAdapter = new TeamAdapter(this.getApplication(), viewModel);

        recyclerView = findViewById(R.id.recyclerView);
        textViewStatus = findViewById(R.id.textViewStatus);
        recyclerView.setAdapter(teamAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setUpTabListeners();
        observeData();
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
            public void onChanged(List<Team> teams)
            {
                teamAdapter.submitList(teams);
            }
        });
    }

    private void getStudentAPI() {
        // ======================= Student must add code here to get JSON data from an API =======================
        // Define URL to use. Using Cat Facts API here. Note number of facts set to 1
        // ---- Remember to add the following permission to the AndroidManifest.xml file
        //      <uses-permission android:name="android.permission.INTERNET" />
        String url = "https://statsapi.mlb.com/api/v1/teams";
        Log.d("CIS 3334", "url = "+url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        textViewStatus.setText("MLB Received Response");
                        String jsonFact= response.toString();
                        Gson gson = new Gson();
                        MLBTeams MLBdata = gson.fromJson(jsonFact, MLBTeams.class);
                        textViewStatus.setText("Number of teams received = "+MLBdata.teams.size());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("CIS 3334", "In MLB -- onErrorResponse = "+error);

                    }
                });

        // Create a RequestQueue used to send web requests using Volley
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);

    }
}