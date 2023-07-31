package com.example.kalori.model.information;

import static com.example.kalori.R.id.tutorclose;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.kalori.R;

public class TutorialActivity extends AppCompatActivity {

    TextView tutor_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        tutor_close = findViewById(R.id.tutorclose);

        tutor_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}