package com.example.kalori.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addUser(User user);

    @Query("SELECT * FROM User WHERE nama = :name AND password = :password")
    User findUser(String name, String password);

    @Query("SELECT * FROM User WHERE uid = :uid")
    User finderUser(int uid);

    @Update
    void updateUser(User user);
}
