package com.example.kalori.model.information;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.kalori.R;

public class InfromationActivity extends AppCompatActivity {

    TextView info_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infromation);

        getSupportActionBar().hide();

        info_close = findViewById(R.id.infoclose);

        info_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}