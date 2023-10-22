package com.example.cis3334_baseball_team_viewer_midterm_project;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Team.class}, version = 1, exportSchema = false)
public abstract class TeamDatabase extends RoomDatabase {

    public abstract TeamDAO teamDAO();

    private static volatile TeamDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static TeamDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TeamDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    TeamDatabase.class, "team_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
