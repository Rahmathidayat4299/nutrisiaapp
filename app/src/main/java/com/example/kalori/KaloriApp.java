package com.example.kalori;

import android.app.Application;
import android.content.ContextWrapper;
import android.os.Handler;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

public class KaloriApp extends Application {
    private Handler handler;
    private Runnable runnable;
    @Override
    public void onCreate() {
        super.onCreate();

        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

//        handler = new Handler();
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                // Perform your task here
//                // This code will be executed every 4 seconds
//                Toast.makeText(getApplicationContext(), "APLIKASI DEMO", Toast.LENGTH_SHORT).show();
//
//                // Schedule the next execution after 4 seconds
//                handler.postDelayed(this, 4000);
//            }
//        };
//
//        // Start the initial execution of the task
//        handler.postDelayed(runnable, 4000);
    }

    @Override
    public void onTerminate() {
//        handler.removeCallbacks(runnable);
        super.onTerminate();
    }
}
