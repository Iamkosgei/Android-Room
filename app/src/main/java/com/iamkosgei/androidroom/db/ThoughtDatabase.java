package com.iamkosgei.androidroom.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.iamkosgei.androidroom.model.Thought;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = Thought.class, version =1)
public abstract class ThoughtDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static ThoughtDatabase instance;

    public abstract ThoughtDao thoughtDao();

    public static synchronized  ThoughtDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ThoughtDatabase.class,
                    "thought_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return  instance;
    }
}
