package com.iamkosgei.androidroom;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.iamkosgei.androidroom.model.Thought;

import java.util.List;

@Dao
public interface ThoughtDao {
    @Insert
    void insert(Thought thought);

    @Delete
    void delete(Thought thought);

    @Query("DELETE FROM thought_table")
    void deleteAll();

    @Query("SELECT * FROM THOUGHT_TABLE")
    LiveData<List<Thought>> getAllThoughts();

    @Update
    void edit(Thought thought);
}
