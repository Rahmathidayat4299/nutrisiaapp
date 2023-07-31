package com.example.kalori.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.kalori.DateUtil;

import java.util.List;

@Dao
public interface CalorieDao {
    @Query("SELECT * FROM calorie WHERE user = :user AND tgl = :tgl")
    List<Calorie> getAll(String user, String tgl);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCalorie(Calorie cl);
}
