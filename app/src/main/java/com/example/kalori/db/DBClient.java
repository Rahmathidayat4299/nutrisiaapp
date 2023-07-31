package com.example.kalori.db;

import android.content.Context;

import androidx.room.Room;

public class DBClient {
    private Context mCtxt;
    private static DBClient mInstance;
    private AppDatabase appDB;
    private final String DB_NAME = "my_calorie_db";

    private DBClient(Context ctxt){
        mCtxt = ctxt;
        appDB = Room.databaseBuilder(mCtxt,AppDatabase.class, DB_NAME )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public static synchronized DBClient getInstance(Context ctxt){
        if (mInstance == null) mInstance = new DBClient(ctxt);
        return mInstance;
    }

    public AppDatabase getAppDatabase(){
        return appDB;
    }
}
