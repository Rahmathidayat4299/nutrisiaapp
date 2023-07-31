package com.example.kalori.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Calorie.class, User.class, MenuMakanan.class, LimitKalori.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CalorieDao calorieDao();
    public abstract UserDao userDao();
    public abstract MenuMakananDao menuMakananDao();
    public abstract LimitKaloriDao limitKaloriDao();
}
