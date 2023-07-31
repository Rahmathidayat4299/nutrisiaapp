package com.example.kalori;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalori.db.DBClient;
import com.example.kalori.db.MenuMakanan;
import com.example.kalori.model.nutrisi.NutrisiDataItem;
import com.example.kalori.model.nutrisi.NutrisiResponse;
import com.example.kalori.network.ApiClient;
import com.example.kalori.network.ApiInterface;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {

    EditText makanan1_input, makanan2_input, makanan3_input, makanan4_input;
    TextView txtHasilKkal1, txtHasilKkal2, txtHasilKkal3, txtHasilKkal4, txtTotal, txtHasilpro1, txtHasilpro2, txtHasilpro3, txtHasilpro4, txtHasilLemak1, txtHasilLemak2,txtHasilLemak3,txtHasilLemak4, txtHasilKarbo1,txtHasilKarbo2,txtHasilKarbo3,txtHasilKarbo4;
    Button update_button, delete_button, btnKalkulasi;
    String id, tgl, kategori, totalcl, makanan1, makanan2, makanan3, makanan4, bm1, bm2, bm3, bm4, jam;
    EditText bm1input, bm2input, bm3input, bm4input, tgl_input_tampil;
    private SimpleDateFormat dateFormat;
    NutrisiDataItem nutrisiDataItem1, nutrisiDataItem2, nutrisiDataItem3, nutrisiDataItem4;


    private void showDateDialog() {
        // Get the current hour and minute
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a TimePickerDialog and set the selected time when the user picks a time
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute1) -> {
                    // Set the selected time to the EditText
                    String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute1);
                    tgl_input_tampil.setText(selectedTime);
                },
                hour,
                minute,
                DateFormat.is24HourFormat(this)
        );
        // Show the time picker dialog
        timePickerDialog.show();
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
        setContentView(R.layout.activity_update);

        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        tgl_input_tampil = findViewById(R.id.tgl_input_tampil);
        makanan1_input = findViewById(R.id.nama_makanan1_input2);
        makanan2_input = findViewById(R.id.nama_makanan2_input2);
        makanan3_input = findViewById(R.id.nama_makanan3_input2);
        makanan4_input = findViewById(R.id.nama_makanan4_input2);
        bm1input = findViewById(R.id.berat_makanan1_input2);
        bm2input = findViewById(R.id.berat_makanan2_input2);
        bm3input = findViewById(R.id.berat_makanan3_input2);
        bm4input = findViewById(R.id.berat_makanan4_input2);
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
        update_button = findViewById(R.id.update_button);
//        delete_button = findViewById(R.id.delete_button);
        btnKalkulasi = findViewById(R.id.btnKalkulasi);

        ArrayAdapter<KaloriMakanan> arrayAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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


        btnKalkulasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double bm1_input, bm2_input, bm3_input, bm4_input, hasil1, hasil2, hasil3, hasil4, hasilTotal, lemak1, lemak2, lemak3, lemak4, protein1, protein2, protein3, protein4, karbo1, karbo2, karbo3, karbo4;
                if (nutrisiDataItem1 == null) {
                    nutrisiDataItem1 = new NutrisiDataItem(0, "-", "0", "-", "0", "-", "-", "-", "0", "0");;
                }
                if (nutrisiDataItem2 == null) {
                    nutrisiDataItem2 = new NutrisiDataItem(0, "-", "0", "-", "0", "-", "-", "-", "0", "0");;
                }
                if (nutrisiDataItem3 == null) {
                    nutrisiDataItem3 = new NutrisiDataItem(0, "-", "0", "-", "0", "-", "-", "-", "0", "0");;
                }
                if (nutrisiDataItem4 == null) {
                    nutrisiDataItem4 = new NutrisiDataItem(0, "-", "0", "-", "0", "-", "-", "-", "0", "0");;
                }

                bm1_input = bm1input.getText().toString().trim().isEmpty() ? 0 : Double.valueOf(bm1input.getText().toString().trim());
                bm2_input = bm2input.getText().toString().trim().isEmpty() ? 0 : Double.valueOf(bm2input.getText().toString().trim());
                bm3_input = bm3input.getText().toString().trim().isEmpty() ? 0 : Double.valueOf(bm3input.getText().toString().trim());
                bm4_input = bm4input.getText().toString().trim().isEmpty() ? 0 : Double.valueOf(bm4input.getText().toString().trim());
                lemak1 = bm1input.getText().toString().trim().isEmpty() ? 0 : (bm1_input / 100) * Double.parseDouble(nutrisiDataItem1.getLemak());
                lemak2 = bm2input.getText().toString().trim().isEmpty() ? 0 : (bm1_input / 100) * Double.parseDouble(nutrisiDataItem2.getLemak());
                lemak3 = bm3input.getText().toString().trim().isEmpty() ? 0 : (bm1_input / 100) * Double.parseDouble(nutrisiDataItem3.getLemak());
                lemak4 = bm4input.getText().toString().trim().isEmpty() ? 0 : (bm1_input / 100) * Double.parseDouble(nutrisiDataItem4.getLemak());
                protein1 = bm1input.getText().toString().trim().isEmpty() ? 0 : (bm1_input / 100) * Double.parseDouble(nutrisiDataItem1.getProtein());
                protein2 = bm2input.getText().toString().trim().isEmpty() ? 0 : (bm1_input / 100) * Double.parseDouble(nutrisiDataItem2.getProtein());
                protein3 = bm3input.getText().toString().trim().isEmpty() ? 0 : (bm1_input / 100) * Double.parseDouble(nutrisiDataItem3.getProtein());
                protein4 = bm4input.getText().toString().trim().isEmpty() ? 0 : (bm1_input / 100) * Double.parseDouble(nutrisiDataItem4.getProtein());
                karbo1 = bm1input.getText().toString().trim().isEmpty() ? 0 : (bm1_input / 100) * Double.parseDouble(nutrisiDataItem1.getKarbohidrat());
                karbo2 = bm2input.getText().toString().trim().isEmpty() ? 0 : (bm1_input / 100) * Double.parseDouble(nutrisiDataItem2.getKarbohidrat());
                karbo3 = bm3input.getText().toString().trim().isEmpty() ? 0 : (bm1_input / 100) * Double.parseDouble(nutrisiDataItem3.getKarbohidrat());
                karbo4 = bm4input.getText().toString().trim().isEmpty() ? 0 : (bm1_input / 100) * Double.parseDouble(nutrisiDataItem4.getKarbohidrat());
                hasil1 = (bm1_input / 100) * Double.parseDouble(nutrisiDataItem1.getEnergi());
                hasil2 = (bm2_input / 100) * Double.parseDouble(nutrisiDataItem2.getEnergi());
                hasil3 = (bm3_input / 100) * Double.parseDouble(nutrisiDataItem3.getEnergi());
                hasil4 = (bm4_input / 100) * Double.parseDouble(nutrisiDataItem4.getEnergi());
                hasilTotal = hasil1 + hasil2 + hasil3 + hasil4;
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                String hasilTampil1 = (decimalFormat.format(hasil1));
                String hasilTampil2 = (decimalFormat.format(hasil2));
                String hasilTampil3 = (decimalFormat.format(hasil3));
                String hasilTampil4 = (decimalFormat.format(hasil4));
                String hasilTampillemak1 = (decimalFormat.format(lemak1));
                String hasilTampillemak2 = (decimalFormat.format(lemak2));
                String hasilTampillemak3 = (decimalFormat.format(lemak3));
                String hasilTampillemak4 = (decimalFormat.format(lemak4));
                String hasilTampilProtein1 = (decimalFormat.format(protein1));
                String hasilTampilProtein2 = (decimalFormat.format(protein2));
                String hasilTampilProtein3 = (decimalFormat.format(protein3));
                String hasilTampilProtein4 = (decimalFormat.format(protein4));
                String hasilTampilKarbo1 = (decimalFormat.format(karbo1));
                String hasilTampilKarbo2 = (decimalFormat.format(karbo2));
                String hasilTampilKarbo3 = (decimalFormat.format(karbo3));
                String hasilTampilKarbo4 = (decimalFormat.format(karbo4));
                String hasilTampilTotal = (decimalFormat.format(hasilTotal));
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
        tgl_input_tampil.setOnClickListener(view -> showDateDialog());

        getAndSetIntentData();
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(kategori);
        }
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MenuMakanan updatedMenu = new MenuMakanan();
                updatedMenu.uid = Integer.parseInt(id);
                updatedMenu.tanggal = tgl;
                updatedMenu.jam = jam;
                updatedMenu.jenis = kategori;
                updatedMenu.makanan1 = makanan1_input.getText().toString().trim();
                updatedMenu.makanan2 = makanan2_input.getText().toString().trim();
                updatedMenu.makanan3 = makanan3_input.getText().toString().trim();
                updatedMenu.makanan4 = makanan4_input.getText().toString().trim();
                updatedMenu.bm1 = bm1input.getText().toString();
                updatedMenu.bm2 = bm2input.getText().toString();
                updatedMenu.bm3 = bm3input.getText().toString();
                updatedMenu.bm4 = bm4input.getText().toString();
                updatedMenu.totalcl = txtTotal.getText().toString();
                updatedMenu.user = Prefs.getString("PREF_USERNAME", "");
                DBClient.getInstance(getApplicationContext()).getAppDatabase().menuMakananDao().updateMakanan(updatedMenu);

                Toast.makeText(getApplicationContext(), "Makanan berhasil diupdate.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

        ApiClient.getClient().create(ApiInterface.class).getNutrisi().enqueue(new Callback<NutrisiResponse>() {
            @Override
            public void onResponse(Call<NutrisiResponse> call, Response<NutrisiResponse> response) {
                Toast.makeText(UpdateActivity.this, "HIT", Toast.LENGTH_SHORT).show();

                if (response.isSuccessful()){
                    if (!response.body().getData().isEmpty()){
                        response.body().getData().forEach(nutrisi -> {
                            if(makanan1.equals(nutrisi.getNamaPangan())) {
                                  Toast.makeText(UpdateActivity.this, "HIT", Toast.LENGTH_SHORT).show();
                                  nutrisiDataItem1 = nutrisi;
                              }
                                if(makanan2.equals(nutrisi.getNamaPangan())) {
                                    Toast.makeText(UpdateActivity.this, "HIT", Toast.LENGTH_SHORT).show();
                                    nutrisiDataItem2 = nutrisi;
                                }

                                if(makanan3.equals(nutrisi.getNamaPangan())) {
                                    Toast.makeText(UpdateActivity.this, "HIT", Toast.LENGTH_SHORT).show();
                                    nutrisiDataItem3 = nutrisi;
                                }

                                if(makanan4.equals(nutrisi.getNamaPangan())) {
                                    Toast.makeText(UpdateActivity.this, "HIT", Toast.LENGTH_SHORT).show();
                                    nutrisiDataItem4 = nutrisi;
                                }

                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<NutrisiResponse> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, "GAGAL", Toast.LENGTH_SHORT).show();
            }
        });
        btnKalkulasi.performClick();
    }


    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("tgl") &&
                getIntent().hasExtra("kategori") && getIntent().hasExtra("totalcl")){
            //getting data from intent
            id = getIntent().getStringExtra("id");
            tgl = getIntent().getStringExtra("tgl");
            kategori = getIntent().getStringExtra("kategori");
            makanan1 = getIntent().getStringExtra("makanan1");
            makanan2 = getIntent().getStringExtra("makanan2");
            makanan3 = getIntent().getStringExtra("makanan3");
            makanan4 = getIntent().getStringExtra("makanan4");
            bm1 = getIntent().getStringExtra("bm1");
            bm2 = getIntent().getStringExtra("bm2");
            bm3 = getIntent().getStringExtra("bm3");
            bm4 = getIntent().getStringExtra("bm4");
            totalcl = getIntent().getStringExtra("totalcl");
            jam = getIntent().getStringExtra("jam");

            //setting intent data
            tgl_input_tampil.setText(jam);
            makanan1_input.setText(makanan1);
            makanan2_input.setText(makanan2);
            makanan3_input.setText(makanan3);
            makanan4_input.setText(makanan4);
            bm1input.setText(bm1);
            bm2input.setText(bm2);
            bm3input.setText(bm3);
            bm4input.setText(bm4);
            txtTotal.setText(totalcl);
        }else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hapus " + kategori + " ?");
        builder.setMessage("Anda yakin ingin menghapus " + kategori + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int deleted_id = Integer.parseInt(id);
                DBClient.getInstance(getApplicationContext()).getAppDatabase().menuMakananDao().deleteMakanan(deleted_id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}