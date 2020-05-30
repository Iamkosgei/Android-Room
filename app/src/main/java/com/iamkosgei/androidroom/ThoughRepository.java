package com.iamkosgei.androidroom;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.iamkosgei.androidroom.model.Thought;

import java.util.List;

public class ThoughRepository {
    private ThoughtDao thoughtDao;
    private LiveData<List<Thought>> allThoughts;

    public ThoughRepository(Application application){
        ThoughtDatabase thoughtDatabase = ThoughtDatabase.getInstance(application);
        thoughtDao = thoughtDatabase.thoughtDao();
        allThoughts = thoughtDao.getAllThoughts();
    }

    public void insert(Thought thought){
        ThoughtDatabase.databaseWriteExecutor.execute(()-> {
            thoughtDao.insert(thought);
        });

    }

    public  LiveData<List<Thought>> getAllThoughts(){
        return allThoughts;
    }

    public void  deleteAllThoughts(){
        ThoughtDatabase.databaseWriteExecutor.execute(()-> {
            thoughtDao.deleteAll();
        });
    }
    public void delete(Thought thought){
        ThoughtDatabase.databaseWriteExecutor.execute(()-> {
            thoughtDao.delete(thought);
        });
    }

    public void update(Thought thought){
        ThoughtDatabase.databaseWriteExecutor.execute(()-> {
            thoughtDao.edit(thought);
        });
    }
}
