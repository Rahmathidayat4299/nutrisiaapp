package com.example.kalori;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kalori.model.information.InfromationActivity;
import com.example.kalori.model.information.TutorialActivity;
import com.example.kalori.model.login.Data;
import com.example.kalori.model.login.LoginResponse;
import com.example.kalori.network.ApiClient;
import com.example.kalori.network.ApiInterface;
import com.pixplicity.easyprefs.library.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.time.LocalDate;
import java.time.Period;

public class LoginActivity extends AppCompatActivity {

    DBHelperLogReg db;
    Button login, register;
    EditText username, password;
    ImageButton information, quest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DBHelperLogReg(this);

        username = (EditText)findViewById(R.id.edt_username);
        password = (EditText)findViewById(R.id.edt_password);
        login = (Button)findViewById(R.id.btn_login);
        register = (Button)findViewById(R.id.btn_register);
        information = (ImageButton) findViewById(R.id.info_button);
        quest = findViewById(R.id.quest_button);

        //register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        //login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUsername = username.getText().toString();
                String strPassword = password.getText().toString();

                if (strUsername.isEmpty()){
                    username.setError("Masukkan Username");
                } else if (strPassword.isEmpty()) {
                    password.setError("Masukkan Password");
                } else {
                    ApiClient.getClient().create(ApiInterface.class).doLogin(strUsername, strPassword).enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful()){
                                Data user = response.body().getData();
                                String splitted[] = user.getTanggalLahir().split("-");
                                int year = Integer.parseInt(splitted[0]);
                                int month = Integer.parseInt(splitted[1]);
                                int day = Integer.parseInt(splitted[2]);
                                Log.d("TAG", "Tahun: " + year + " Bulan: " + month + " Day: " + day);

                                int calculatedAge = getAge(year, month, day);
                                Log.d("TAG", "umur: " + calculatedAge);

                                Prefs.putInt("PREF_UID", user.getId());
                                Prefs.putString("PREF_USERNAME", strUsername);
                                Prefs.putString("PREF_PASSWORD", strPassword);
                                Prefs.putString("PREF_GENDER", user.getJenisKelamin());
                                Prefs.putInt("PREF_TINGGI", Integer.parseInt(user.getTinggiBadanCm()));
                                Prefs.putInt("PREF_BERAT", Integer.parseInt(user.getBeratBadanKg()));
                                Prefs.putInt("PREF_USIA", calculatedAge);

                                Toast.makeText(getApplicationContext(), "Berhasil Masuk", Toast.LENGTH_SHORT).show();
                                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(mainIntent);
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this, "Username dan Password Salah / Tidak Sesuai", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Log.d("gagal", "onFailure: " + t.getMessage());
                            Toast.makeText(LoginActivity.this, "Gagal Masuk "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent informationIntent = new Intent(LoginActivity.this, InfromationActivity.class);
                startActivity(informationIntent);
            }
        });

        quest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, TutorialActivity.class);
                startActivity(intent);
            }
        });
    }

    public int getAge(int year, int month, int day) {
        LocalDate currentDate = LocalDate.now();
        LocalDate userDate= LocalDate.of(year, month, day);
        Period age = Period.between(userDate, currentDate);
        return age.getYears();

    }
}