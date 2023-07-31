package com.example.kalori.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LimitKaloriDao {
    @Query("SELECT * FROM LimitKalori WHERE user = :user")
    List<LimitKalori> getLimit(String user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLimit(LimitKalori lk);
}
