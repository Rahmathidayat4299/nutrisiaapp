package com.example.kalori.db;

import android.view.Menu;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MenuMakananDao {
    @Query("SELECT * FROM MenuMakanan WHERE tanggal = :tanggal AND user = :user")
    List<MenuMakanan> getAllMenuMakanan(String tanggal, String user);

    @Insert
    long insertMakanan(MenuMakanan m);

    @Update
    void updateMakanan(MenuMakanan m);

    @Query("DELETE FROM MenuMakanan WHERE uid = :uid")
    void deleteMakanan(int uid);
}
