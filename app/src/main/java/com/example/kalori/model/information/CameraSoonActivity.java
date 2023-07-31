package com.example.kalori.model.information;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.kalori.R;

public class CameraSoonActivity extends AppCompatActivity {

    TextView csclose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camerasoon);

        csclose = findViewById(R.id.cs_close);

        csclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}