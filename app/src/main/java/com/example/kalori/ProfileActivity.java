package com.example.kalori;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.kalori.db.DBClient;
import com.example.kalori.db.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pixplicity.easyprefs.library.Prefs;

public class ProfileActivity extends AppCompatActivity {

    EditText edtBerat, edtTinggi, edtuser;
    Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        edtBerat = (EditText) findViewById(R.id.bb_input);
        edtTinggi = (EditText) findViewById(R.id.tb_input);
        btnSimpan = (Button) findViewById(R.id.btnSimpanProfile);

        edtBerat.setText(""+ Prefs.getInt("PREF_BERAT"));
        edtTinggi.setText(""+ Prefs.getInt("PREF_TINGGI"));


        btnSimpan.setOnClickListener(v -> {
            User currentUser = DBClient.getInstance(ProfileActivity.this).getAppDatabase().userDao().findUser(Prefs.getString("PREF_USERNAME"),
                    Prefs.getString("PREF_PASSWORD"));
            currentUser.setTinggi(Integer.parseInt(edtTinggi.getText().toString()));
            currentUser.setBerat(Integer.parseInt(edtBerat.getText().toString()));
            currentUser.setNama(edtuser.getText().toString());

            DBClient.getInstance(ProfileActivity.this).getAppDatabase().userDao().updateUser(currentUser);

            Prefs.putInt("PREF_BERAT", Integer.parseInt(edtBerat.getText().toString()));
            Prefs.putInt("PREF_TINGGI", Integer.parseInt(edtTinggi.getText().toString()));

            finish();
        });

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_profile) {
                    return true;
                } else if (itemId == R.id.navigation_home) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });
    }
}