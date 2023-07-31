package com.example.kalori;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kalori.db.DBClient;
import com.example.kalori.db.User;
import com.example.kalori.model.register.RegisterRequest;
import com.example.kalori.network.ApiClient;
import com.example.kalori.network.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    DBHelperLogReg db;
    Button login, register;
    EditText username, password, passwordConf, edtTinggi, edtBerat, edtTempatLahir, edtTglLahir;
    AppCompatSpinner spnGender;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DBHelperLogReg(this);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        username = (EditText)findViewById(R.id.edt_usernameReg);
        password = (EditText)findViewById(R.id.edt_passwordReg);
        passwordConf = (EditText)findViewById(R.id.edt_passwordRegCon);
        login = (Button)findViewById(R.id.btn_loginReg);
        register = (Button)findViewById(R.id.btn_registerReg);
        edtTinggi = (EditText) findViewById(R.id.edt_Tinggi);
        edtBerat = (EditText) findViewById(R.id.edt_Berat);
        edtTempatLahir = (EditText) findViewById(R.id.edt_TempatLahir);
        edtTglLahir = (EditText) findViewById(R.id.edt_TanggalLahir);
        spnGender = (AppCompatSpinner) findViewById(R.id.spnGender);

        edtTglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {showDateDialog();
            }
        });

        //login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });

        //register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUsername = username.getText().toString();
                String strPassword = password.getText().toString();
                String strPasswordConf = passwordConf.getText().toString();
                int tinggi = Integer.parseInt(edtTinggi.getText().toString());
                int berat = Integer.parseInt(edtBerat.getText().toString());
                String tempatLahir = edtTempatLahir.getText().toString();
                String tglLahir = edtTglLahir.getText().toString();
                String gender = spnGender.getSelectedItem().toString();


                if (strUsername.isEmpty()){
                    username.setError("Masukkan Username");
                } else if (strPassword.isEmpty()) {
                    password.setError("Masukkan Password");
                } else if (strPasswordConf.isEmpty()) {
                    passwordConf.setError("Masukkan Konfirmasi Password");
                }
                else {
                    if (strPassword.equals(strPasswordConf)) {
                        User theUser = new User();
                        theUser.setNama(strUsername);
                        theUser.setPassword(strPassword);
                        theUser.setTinggi(tinggi);
                        theUser.setBerat(berat);
                        theUser.setGender(gender);
                        theUser.setTanggalLahir(tglLahir);
                        theUser.setTempatLahir(tempatLahir);

                        RegisterRequest request = new RegisterRequest(theUser.getBerat(), theUser.getPassword(),
                                theUser.getTempatLahir(), theUser.getTinggi(), theUser.getGender(), theUser.getTanggalLahir(),
                                theUser.getNama());

                        ApiClient.getClient().create(ApiInterface.class).doRegister(request).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, "Register berhasil", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(RegisterActivity.this, "Register gagal", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(RegisterActivity.this, "Register gagal", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        Toast.makeText(getApplicationContext(), "Password Tidak Cocok!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYEar, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYEar, dayOfMonth);

                edtTglLahir.setText(dateFormat.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}