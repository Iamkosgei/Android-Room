package com.iamkosgei.androidroom;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iamkosgei.androidroom.model.Thought;

import java.util.List;

public class ThoughViewModel extends AndroidViewModel {
    private ThoughRepository thoughRepository;
    private LiveData<List<Thought>> allThoughts;

    public ThoughViewModel(Application application) {
        super(application);
        thoughRepository = new ThoughRepository(application);
        allThoughts = thoughRepository.getAllThoughts();
    }
    public void insert(Thought thought){
        thoughRepository.insert(thought);
    }

    public LiveData<List<Thought>> getAllThoughts(){
        return  allThoughts;
    }
    public void deleteAllThoughts(){
        thoughRepository.deleteAllThoughts();
    }

    public void delete(Thought thought){
        thoughRepository.delete(thought);
    }

    public void edit(Thought thought){
        thoughRepository.update(thought);
    }

}
