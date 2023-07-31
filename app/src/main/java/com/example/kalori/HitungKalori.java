package com.example.kalori;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalori.db.AppDatabase;
import com.example.kalori.db.DBClient;
import com.example.kalori.db.LimitKalori;
import com.example.kalori.model.history.request.AddHistoryDietRequest;
import com.example.kalori.network.ApiClient;
import com.example.kalori.network.ApiInterface;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.DecimalFormat;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HitungKalori extends AppCompatActivity {
    Spinner spnAktivitas, spnDiet;
    TextView txtHasil;
    Button btnHitung, btnSimpan;
    String hasilAkhir;
    boolean isPria = true;
    double batasKalori = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hitung_kalori);

        spnAktivitas = findViewById(R.id.spnAktivitas);
        spnDiet = findViewById(R.id.spnDiet);
        txtHasil = findViewById(R.id.txtHasil);
        btnHitung = findViewById(R.id.btnHitung);
        btnSimpan = findViewById(R.id.btnSimpan);


        isPria = Prefs.getString("PREF_GENDER", "Pria").equalsIgnoreCase("Pria");

        ArrayList<AktivitasFisik> list_aktivitas = new ArrayList<>();

        list_aktivitas.add(new AktivitasFisik("Tidak aktif/sedentari", 1.2));
        list_aktivitas.add(new AktivitasFisik("Ringan", 1.3));
        list_aktivitas.add(new AktivitasFisik("Sedang", 1.5));
        list_aktivitas.add(new AktivitasFisik("Berat", 1.7));
        list_aktivitas.add(new AktivitasFisik("Sangat berat", 1.9));

        ArrayAdapter<AktivitasFisik> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list_aktivitas);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAktivitas.setAdapter(arrayAdapter);

        btnHitung.setOnClickListener((view) -> {
            if (isPria) {
                double tb, bb, umur, bobot, hasil;
                bobot = ((AktivitasFisik) spnAktivitas.getSelectedItem()).getBobot();
                tb = Double.valueOf(Prefs.getInt("PREF_TINGGI"));
                bb = Double.valueOf(Prefs.getInt("PREF_BERAT"));
                umur = Double.valueOf(Prefs.getInt("PREF_USIA"));
                hasil = (66.5 + (13.7 * bb) + (5 * tb) - (6.8 * umur)) * bobot;

                if (spnDiet.getSelectedItem().toString().equals("Ya")) {
                    hasil -= 500;
                }

                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                String hasil1 = (decimalFormat.format(hasil));
                txtHasil.setText(hasil1);
                hasilAkhir = hasil1;

                LimitKalori lk = new LimitKalori();
                lk.limit = hasil1;
                lk.user = Prefs.getString("PREF_USERNAME");
                DBClient.getInstance(getApplicationContext()).getAppDatabase().limitKaloriDao().insertLimit(lk);
                Toast.makeText(getApplicationContext(), "Limit kalori harian sudah disimpan.", Toast.LENGTH_SHORT).show();
            } else {
                double tb, bb, umur, bobot, hasil;
                bobot = ((AktivitasFisik) spnAktivitas.getSelectedItem()).getBobot();
                tb = Double.valueOf(Prefs.getInt("PREF_TINGGI"));
                bb = Double.valueOf(Prefs.getInt("PREF_BERAT"));
                umur = Double.valueOf(Prefs.getInt("PREF_USIA"));
                hasil = (655 + (9.6 * bb) + (1.8 * tb) - (4.7 * umur)) * bobot;

                if (spnDiet.getSelectedItem().toString().equals("Ya")) {
                    hasil -= 500;
                }

                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                String hasil1 = (decimalFormat.format(hasil));
                txtHasil.setText(hasil1);
                hasilAkhir = hasil1;

                LimitKalori lk = new LimitKalori();
                lk.limit = hasil1;
                lk.user = Prefs.getString("PREF_USERNAME");
                DBClient.getInstance(getApplicationContext()).getAppDatabase().limitKaloriDao().insertLimit(lk);
                Toast.makeText(getApplicationContext(), "Limit kalori harian sudah disimpan.", Toast.LENGTH_SHORT).show();
            }
        });

        btnSimpan.setOnClickListener(v -> {
            int isDiet = 0;
            if (spnDiet.getSelectedItem().toString().equals("Ya")) {
                isDiet = 1;
            }
            try {
                String aktivitas = ((AktivitasFisik) spnAktivitas.getSelectedItem()).getAktivitas();

                Prefs.edit()
                        .putString("AKTIVITAS_FISIK", aktivitas)
                        .putInt("IS_DIET", isDiet)
                        .commit();
                System.out.println(hasilAkhir);
                Prefs.edit().putString("BATAS_KALORI", hasilAkhir).commit();
                Prefs.putString("AKTIVITAS_FISIK", aktivitas);
                Prefs.getString("AKTIVITAS_FISIK", "");
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            Prefs.putInt("IS_DIET", isDiet);
            AddHistoryDietRequest request = new AddHistoryDietRequest(Float.parseFloat(hasilAkhir),
                    ((AktivitasFisik) spnAktivitas.getSelectedItem()).getAktivitas(), isDiet, Prefs.getString("PREF_USERNAME", ""));
            ApiClient.getClient().create(ApiInterface.class).addHistoryDiet(request).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(HitungKalori.this, "Berhasil menyimpan", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
            finish();
        });
    }
}