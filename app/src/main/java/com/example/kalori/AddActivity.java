package com.example.kalori;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalori.model.history.request.AddHistoryRequest;
import com.example.kalori.model.history.request.AddHistoryBreakfastRequest;
import com.example.kalori.model.history.request.AddHistoryDinnerRequest;
import com.example.kalori.model.history.request.AddHistoryLunchRequest;
import com.example.kalori.model.information.CameraSoonActivity;
import com.example.kalori.model.nutrisi.NutrisiDataItem;
import com.example.kalori.network.ApiClient;
import com.example.kalori.network.ApiInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity {
    String aktivitas = Prefs.getString("AKTIVITAS_FISIK", "");
    double bm1_input, bm2_input, bm3_input, bm4_input, hasil1, hasil2, hasil3, hasil4, hasilTotal, jumlahKalori1, jumlahKalori2, jumlahKalori3, jumlahKalori4, lemak1, lemak2,lemak3,lemak4,protein1,protein2,protein3,protein4,karbo1,karbo2,karbo3,karbo4 = 0;
    EditText bm1input, bm2input, bm3input, bm4input;
    EditText makanan1_input, makanan2_input, makanan3_input, makanan4_input;
    TextView txtHasilKkal1, txtHasilKkal2, txtHasilKkal3, txtHasilKkal4, txtTotal, txtHasilpro1, txtHasilpro2, txtHasilpro3, txtHasilpro4, txtHasilLemak1, txtHasilLemak2,txtHasilLemak3,txtHasilLemak4, txtHasilKarbo1,txtHasilKarbo2,txtHasilKarbo3,txtHasilKarbo4;
    Button btnKalkulasi, btnAdd;
    private SimpleDateFormat dateFormat;
    NutrisiDataItem nutrisiDataItem1, nutrisiDataItem2, nutrisiDataItem3, nutrisiDataItem4;
    String hasilTampil1, hasilTampil2, hasilTampil3, hasilTampil4, hasilTampilTotal;
    String hasilTampillemak1, hasilTampillemak2, hasilTampillemak3, hasilTampillemak4;
    String hasilTampilProtein1, hasilTampilProtein2, hasilTampilProtein3, hasilTampilProtein4;
    String hasilTampilKarbo1, hasilTampilKarbo2, hasilTampilKarbo3, hasilTampilKarbo4;

    private void showDateDialog(){
        // Get the current hour and minute
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
    }

    ActivityResultLauncher<Intent> nutrisiLauncher1 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // Here, no request code
                    Intent data = result.getData();
                    nutrisiDataItem1 = (NutrisiDataItem) data.getSerializableExtra("nutrisi");
                    makanan1_input.setText(nutrisiDataItem1.getNamaPangan());
                }
            });

    ActivityResultLauncher<Intent> nutrisiLauncher2 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // Here, no request code
                    Intent data = result.getData();
                    nutrisiDataItem2 = (NutrisiDataItem) data.getSerializableExtra("nutrisi");
                    makanan2_input.setText(nutrisiDataItem2.getNamaPangan());
                }
            });

    ActivityResultLauncher<Intent> nutrisiLauncher3 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // Here, no request code
                    Intent data = result.getData();
                    nutrisiDataItem3 = (NutrisiDataItem) data.getSerializableExtra("nutrisi");
                    makanan3_input.setText(nutrisiDataItem3.getNamaPangan());

                }
            });

    ActivityResultLauncher<Intent> nutrisiLauncher4 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // Here, no request code
                    Intent data = result.getData();
                    nutrisiDataItem4 = (NutrisiDataItem) data.getSerializableExtra("nutrisi");
                    makanan4_input.setText(nutrisiDataItem4.getNamaPangan());

                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Timbangan Nutrisi");

        ArrayList<Double> dataFirebase = new ArrayList<Double>();
        final int[] i = {0};

        dataFirebase.add(0, 0.0);
        dataFirebase.add(1, 0.0);
        dataFirebase.add(2, 0.0);
        dataFirebase.add(3, 0.0);

        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        bm1input = findViewById(R.id.berat_makanan1_input);
        bm2input = findViewById(R.id.berat_makanan2_input);
        bm3input = findViewById(R.id.berat_makanan3_input);
        bm4input = findViewById(R.id.berat_makanan4_input);
        makanan1_input = findViewById(R.id.nama_makanan1_input);
        makanan2_input = findViewById(R.id.nama_makanan2_input);
        makanan3_input = findViewById(R.id.nama_makanan3_input);
        makanan4_input = findViewById(R.id.nama_makanan4_input);
        txtHasilKkal1 = findViewById(R.id.txtHasilKkal1);
        txtHasilKkal2 = findViewById(R.id.txtHasilKkal2);
        txtHasilKkal3 = findViewById(R.id.txtHasilKkal3);
        txtHasilKkal4 = findViewById(R.id.txtHasilKkal4);
        txtHasilpro1 = findViewById(R.id.txtHasilPro1);
        txtHasilpro2 = findViewById(R.id.txtHasilPro2);
        txtHasilpro3 = findViewById(R.id.txtHasilPro3);
        txtHasilpro4 = findViewById(R.id.txtHasilPro4);
        txtHasilLemak1 = findViewById(R.id.txtHasilLemak1);
        txtHasilLemak2 = findViewById(R.id.txtHasilLemak2);
        txtHasilLemak3 = findViewById(R.id.txtHasilLemak3);
        txtHasilLemak4 = findViewById(R.id.txtHasilLemak4);
        txtHasilKarbo1 = findViewById(R.id.txtHasilKarbo1);
        txtHasilKarbo2 = findViewById(R.id.txtHasilKarbo2);
        txtHasilKarbo3 = findViewById(R.id.txtHasilKarbo3);
        txtHasilKarbo4 = findViewById(R.id.txtHasilKarbo4);
        txtTotal = findViewById(R.id.txtTotal);
        btnKalkulasi = findViewById(R.id.btnKalkulasi);
        btnAdd = findViewById(R.id.btnAdd);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    dataFirebase.set(i[0], ds.child("Reading").getValue(Double.class));
                    Log.d("TAG", "Value is: " + dataFirebase.get(i[0]));
                    i[0]++;
                }
                i[0] = 0;
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        makanan1_input.setOnClickListener(v->{
            nutrisiLauncher1.launch(new Intent(this, MakananActivity.class));
        });

        makanan2_input.setOnClickListener(v->{
            nutrisiLauncher2.launch(new Intent(this, MakananActivity.class));
        });

        makanan3_input.setOnClickListener(v->{
            nutrisiLauncher3.launch(new Intent(this, MakananActivity.class));
        });

        makanan4_input.setOnClickListener(v->{
            nutrisiLauncher4.launch(new Intent(this, MakananActivity.class));
        });


        //Tombol Kalkulasi
        btnKalkulasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nutrisiDataItem1 == null){
                    nutrisiDataItem1 = new NutrisiDataItem(0, "-", "0", "-", "0", "-", "-", "-", "0", "0");;
                }
                if(nutrisiDataItem2 == null){
                    nutrisiDataItem2 = new NutrisiDataItem(0, "-", "0", "-", "0", "-", "-", "-", "0", "0");;
                }
                if(nutrisiDataItem3 == null){
                    nutrisiDataItem3 = new NutrisiDataItem(0, "-", "0", "-", "0", "-", "-", "-", "0", "0");;
                }
                if(nutrisiDataItem4 == null){
                    nutrisiDataItem4 = new NutrisiDataItem(0, "-", "0", "-", "0", "-", "-", "-", "0", "0");;
                }

                jumlahKalori1 = Double.parseDouble(nutrisiDataItem1.getEnergi());
                jumlahKalori2 = Double.parseDouble(nutrisiDataItem2.getEnergi());
                jumlahKalori3 = Double.parseDouble(nutrisiDataItem3.getEnergi());
                jumlahKalori4 = Double.parseDouble(nutrisiDataItem4.getEnergi());

                if(dataFirebase.get(0) == 0.0){
                    if(bm1input.getText().toString().trim().isEmpty()){
                        bm1_input = 0;
                    }else bm1_input = Double.parseDouble(bm1input.getText().toString().trim());
                }else {
                    bm1_input = dataFirebase.get(0);
                    bm1input.setText(Double.toString(bm1_input));
                }

                if(dataFirebase.get(1) == 0.0){
                    if(bm2input.getText().toString().trim().isEmpty()){
                        bm2_input = 0;
                    }else bm2_input = Double.parseDouble(bm2input.getText().toString().trim());
                }else {
                    bm2_input = dataFirebase.get(1);
                    bm2input.setText(Double.toString(bm2_input));
                }

                if(dataFirebase.get(2) == 0.0){
                    if(bm3input.getText().toString().trim().isEmpty()){
                        bm3_input = 0;
                    }else bm3_input = Double.parseDouble(bm3input.getText().toString().trim());
                }else {
                    bm3_input = dataFirebase.get(2);
                    bm3input.setText(Double.toString(bm3_input));
                }

                if(dataFirebase.get(3) == 0.0){
                    if(bm4input.getText().toString().trim().isEmpty()){
                        bm4_input = 0;
                    }else bm4_input = Double.parseDouble(bm4input.getText().toString().trim());
                }else {
                    bm4_input = dataFirebase.get(3);
                    bm4input.setText(Double.toString(bm4_input));
                }

                lemak1 = bm1input.getText().toString().trim().isEmpty() ? 0 :(bm1_input/100)*Double.parseDouble(nutrisiDataItem1.getLemak());
                lemak2 = bm2input.getText().toString().trim().isEmpty() ? 0 :(bm1_input/100)*Double.parseDouble(nutrisiDataItem2.getLemak());
                lemak3 = bm3input.getText().toString().trim().isEmpty() ? 0 :(bm1_input/100)*Double.parseDouble(nutrisiDataItem3.getLemak());
                lemak4 = bm4input.getText().toString().trim().isEmpty() ? 0 :(bm1_input/100)*Double.parseDouble(nutrisiDataItem4.getLemak());
                protein1 = bm1input.getText().toString().trim().isEmpty() ? 0 :(bm1_input/100)*Double.parseDouble(nutrisiDataItem1.getProtein());
                protein2 = bm2input.getText().toString().trim().isEmpty() ? 0 :(bm1_input/100)*Double.parseDouble(nutrisiDataItem2.getProtein());
                protein3 = bm3input.getText().toString().trim().isEmpty() ? 0 :(bm1_input/100)*Double.parseDouble(nutrisiDataItem3.getProtein());
                protein4 = bm4input.getText().toString().trim().isEmpty() ? 0 :(bm1_input/100)*Double.parseDouble(nutrisiDataItem4.getProtein());
                karbo1 = bm1input.getText().toString().trim().isEmpty() ? 0 :(bm1_input /100)*Double.parseDouble(nutrisiDataItem1.getKarbohidrat());
                karbo2 = bm2input.getText().toString().trim().isEmpty() ? 0 :(bm1_input/100)*Double.parseDouble(nutrisiDataItem2.getKarbohidrat());
                karbo3 = bm3input.getText().toString().trim().isEmpty() ? 0 :(bm1_input/100)*Double.parseDouble(nutrisiDataItem3.getKarbohidrat());
                karbo4 = bm4input.getText().toString().trim().isEmpty() ? 0 :(bm1_input/100)*Double.parseDouble(nutrisiDataItem4.getKarbohidrat());
                hasil1 = bm1input.getText().toString().trim().isEmpty() ? 0 :(bm1_input/100)*jumlahKalori1;
                hasil2 = bm2input.getText().toString().trim().isEmpty() ? 0 :(bm2_input/100)*jumlahKalori2;
                hasil3 = bm3input.getText().toString().trim().isEmpty() ? 0 :(bm3_input/100)*jumlahKalori3;
                hasil4 = bm4input.getText().toString().trim().isEmpty() ? 0 :(bm4_input/100)*jumlahKalori4;
                hasilTotal = hasil1+hasil2+hasil3+hasil4;
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                hasilTampil1 = decimalFormat.format(hasil1);
                hasilTampil2 = decimalFormat.format(hasil2);
                hasilTampil3 = decimalFormat.format(hasil3);
                hasilTampil4 = decimalFormat.format(hasil4);
                hasilTampilTotal = decimalFormat.format(hasilTotal);
                hasilTampillemak1 = decimalFormat.format(lemak1);
                hasilTampillemak2 = decimalFormat.format(lemak2);
                hasilTampillemak3 = decimalFormat.format(lemak3);
                hasilTampillemak4 = decimalFormat.format(lemak4);
                hasilTampilProtein1 = decimalFormat.format(protein1);
                hasilTampilProtein2 = decimalFormat.format(protein2);
                hasilTampilProtein3 = decimalFormat.format(protein3);
                hasilTampilProtein4 = decimalFormat.format(protein4);
                hasilTampilKarbo1 = decimalFormat.format(karbo1);
                hasilTampilKarbo2 = decimalFormat.format(karbo2);
                hasilTampilKarbo3 = decimalFormat.format(karbo3);
                hasilTampilKarbo4 = decimalFormat.format(karbo4);
                txtHasilpro1.setText(hasilTampilProtein1);
                txtHasilpro2.setText(hasilTampilProtein2);
                txtHasilpro3.setText(hasilTampilProtein3);
                txtHasilpro4.setText(hasilTampilProtein4);
                txtHasilKkal1.setText(hasilTampil1);
                txtHasilKkal2.setText(hasilTampil2);
                txtHasilKkal3.setText(hasilTampil3);
                txtHasilKkal4.setText(hasilTampil4);
                txtHasilLemak1.setText(hasilTampillemak1);
                txtHasilLemak2.setText(hasilTampillemak2);
                txtHasilLemak3.setText(hasilTampillemak3);
                txtHasilLemak4.setText(hasilTampillemak4);
                txtHasilKarbo1.setText(hasilTampilKarbo1);
                txtHasilKarbo2.setText(hasilTampilKarbo2);
                txtHasilKarbo3.setText(hasilTampilKarbo3);
                txtHasilKarbo4.setText(hasilTampilKarbo4);
                txtTotal.setText(hasilTampilTotal);

            }
        });

        //Tombol Simpan
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Loading", Toast.LENGTH_SHORT).show();
                ApiClient.getClient().create(ApiInterface.class).addHistory(
                        new AddHistoryRequest(
                                aktivitas,
                                Prefs.getInt("IS_DIET", 1),
                                sumEnergi(hasil1, hasil2, hasil3, hasil4),
                                Prefs.getString("BATAS_KALORI", "0"),
                                sumProtein(protein1, protein2, protein3, protein4),
                                sumLemak(lemak1, lemak2, lemak3, lemak4),
                                sumKarbo(karbo1, karbo2, karbo3, karbo4),
                                Prefs.getString("PREF_USERNAME", ""),
                                makanan1_input.getText().toString(),
                                makanan2_input.getText().toString(),
                                makanan3_input.getText().toString(),
                                makanan4_input.getText().toString(),
                                String.valueOf(bm1_input),
                                String.valueOf(bm2_input),
                                String.valueOf(bm3_input),
                                String.valueOf(bm4_input),
                                hasilTampil1,
                                hasilTampil2,
                                hasilTampil3,
                                hasilTampil4,
                                hasilTampillemak1,
                                hasilTampillemak2,
                                hasilTampillemak3,
                                hasilTampillemak4,
                                hasilTampilProtein1,
                                hasilTampilProtein2,
                                hasilTampilProtein3,
                                hasilTampilProtein4,
                                hasilTampilKarbo1,
                                hasilTampilKarbo2,
                                hasilTampilKarbo3,
                                hasilTampilKarbo4
                        )
                ).enqueue(
                        new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(AddActivity.this, "Data Ditambahkan", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(AddActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                                    Log.d("ERRORRR", "onResponse: " + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(AddActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
    }

    public float sumLemak(double lemak1, double lemak2, double lemak3,double lemak4) {
        return (float)(lemak1+lemak2+lemak3+lemak4);
    }

    public float sumProtein(double protein1, double protein2, double protein3, double protein4){
        return (float)(protein1+protein2+protein3+protein4);
    }

    public float sumKarbo(double karbo1, double karbo2, double karbo3, double karbo4){
        return (float)(karbo1+karbo2+karbo3+karbo4);
    }

    public float sumEnergi(double jumlahKalori1, double jumlahKalori2, double jumlahKalori3, double jumlahKalori4){
        return (float)(jumlahKalori1+jumlahKalori2+jumlahKalori3+jumlahKalori4);
    }
    });
    }
}