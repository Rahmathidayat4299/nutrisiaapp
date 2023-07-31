package com.example.kalori.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"user"}, unique = true)})
public class LimitKalori {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "limit")
    public String limit;

    @ColumnInfo(name = "user")
    public String user;
}
