package com.example.kalori;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalori.db.Calorie;
import com.example.kalori.db.DBClient;
import com.example.kalori.db.LimitKalori;
import com.example.kalori.db.MenuMakanan;
import com.example.kalori.model.history.response.DataItem;
import com.example.kalori.model.history.response.DataItemV2;
import com.example.kalori.model.history.response.HistoryResponse;
import com.example.kalori.model.information.CameraSoonActivity;
import com.example.kalori.model.information.TutorialActivity;
import com.example.kalori.network.ApiClient;
import com.example.kalori.network.ApiInterface;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MakananAdapter.MakananClickListener {
    private static final String TAG = "MainActivity";
    ImageView btnHitungKalori, add_button, btn_sooncamera, btnHistory;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ImageView empty_imageview;
    TextView no_data, tvLimitKalori, tvTotalKalori;
    DBHelperData myDB;
    ArrayList<String> user_id, tgl_input, kategori_input,
            makanan1_input, makanan2_input, makanan3_input, makanan4_input, bm1input, bm2input, bm3input, bm4input,
            totalcl_input;
    MakananAdapter adapter;
    List<DataItemV2> listMakanan;
    String currentDate = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date currDate = new Date();
        currentDate = dateFormat.format(currDate);

        btnHitungKalori = findViewById(R.id.btnHitungKalori);
        btnHitungKalori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HitungKalori.class);
                startActivity(intent);
            }
        });

        listMakanan = new ArrayList<>();
        tvLimitKalori = findViewById(R.id.tvLimitKaloriHarian);
        tvTotalKalori = findViewById(R.id.tvTotalKonsumsi);
        recyclerView = findViewById(R.id.rvDaftarMakanan);
        add_button = findViewById(R.id.add_button);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);
        progressBar = findViewById(R.id.progress_circular);
        btn_sooncamera = findViewById(R.id.btn_sooncamera);
        btnHistory = findViewById(R.id.btnHistory);

        //Tombol Hitung Nutrisi Makanan
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        //Tombol Hitung Kalori Harian
        btnHitungKalori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent = new Intent(MainActivity.this, HitungKalori.class);
                startActivity(profileIntent);
            }
        });

        //Tombol Kamera
        btn_sooncamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameraSoonActivity.class);
                startActivity(intent);
            }
        });

        //Tombol History
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(profileIntent);
            }
        });

        myDB = new DBHelperData(MainActivity.this);
        user_id = new ArrayList<>();
        tgl_input = new ArrayList<>();
        kategori_input = new ArrayList<>();
        makanan1_input = new ArrayList<>();
        makanan2_input = new ArrayList<>();
        makanan3_input = new ArrayList<>();
        makanan4_input = new ArrayList<>();
        bm1input = new ArrayList<>();
        bm2input = new ArrayList<>();
        bm3input = new ArrayList<>();
        bm4input = new ArrayList<>();
        totalcl_input = new ArrayList<>();
        adapter = new MakananAdapter();
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        //Navigation
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_profile) {
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.navigation_home) {
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    void storeDataInArrays() {
        ApiClient.getClient().create(ApiInterface.class).getHistory(currentDate, Prefs.getString("PREF_USERNAME", "")).enqueue(
                new Callback<HistoryResponse>() {
                    @Override
                    public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                        if (response.isSuccessful()) {
                            listMakanan = response.body().getData();
                            System.out.println("F: " + listMakanan.size());
                        }
                    }

                    @Override
                    public void onFailure(Call<HistoryResponse> call, Throwable t) {
                        Log.d("gagal", "onFailure222: " + t.getMessage());

                    }
                }
        );

        if (listMakanan.isEmpty()) {
            System.out.println("FC: " + listMakanan.size());
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        } else {
            listMakanan.forEach((kalori) -> {
                user_id.add(kalori.username);
                tgl_input.add(currentDate);
                kategori_input.add("");
                makanan1_input.add(kalori.makanan1);
                makanan2_input.add(kalori.makanan2);
                makanan3_input.add(kalori.makanan3);
                makanan4_input.add(kalori.makanan4);
                bm1input.add(kalori.bm1);
                bm2input.add(kalori.bm2);
                bm3input.add(kalori.bm3);
                bm4input.add(kalori.bm4);
                totalcl_input.add(String.valueOf(kalori.total_kalori_kkal));
            });
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
            adapter.updateData(listMakanan);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }

    private void updateList() {
        progressBar.setVisibility(View.VISIBLE);

        List<LimitKalori> lks = DBClient.getInstance(getApplicationContext()).getAppDatabase().limitKaloriDao().getLimit(
                Prefs.getString("PREF_USERNAME"));
        LimitKalori lk = null;

        if (lks.size() == 0) {
            tvLimitKalori.setText("Limit kalori harian: 0 kkal");
        } else {
            lk = lks.get(0);
            tvLimitKalori.setText("Limit kalori harian: " + lk.limit + " kkal");
        }

        LimitKalori finalLk = lk;
        ApiClient.getClient().create(ApiInterface.class).getHistory(currentDate, Prefs.getString("PREF_USERNAME", "")).enqueue(
                new Callback<HistoryResponse>() {
                    @Override
                    public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                        if (response.isSuccessful()) {
                            listMakanan = response.body().getData();

                            if (listMakanan.isEmpty()) {
                                empty_imageview.setVisibility(View.VISIBLE);
                                no_data.setVisibility(View.VISIBLE);
                                tvTotalKalori.setText("Total konsumsi kalori saat ini: 0 kkal");
                            } else {
                                recyclerView.setVisibility(View.VISIBLE);
                                empty_imageview.setVisibility(View.GONE);
                                no_data.setVisibility(View.GONE);
                                new Handler().postDelayed(() -> {
                                }, 100);
                                double total = 0.0;
                                for (DataItemV2 m : listMakanan) {
                                    total += m.total_kalori_kkal;
                                }

                                double limitKaloriHarian = 0.0;
                                try {
                                    String newLimit = finalLk.limit.replace(",", ".");
                                    limitKaloriHarian = Double.parseDouble(newLimit);
                                } catch (Exception ignored) {

                                }

                                if (total < limitKaloriHarian) {
                                    Toast.makeText(getApplicationContext(), "Total konsumsi kalori harian masih dibawah limit.",
                                            Toast.LENGTH_SHORT).show();
                                } else if (limitKaloriHarian == 0.0) {
                                    Toast.makeText(getApplicationContext(), "Mohon isi Kalori Harian kamu.",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Total konsumsi kalori harian sudah mencukupi limit.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                                tvTotalKalori.setText("Total konsumsi kalori saat ini: " + decimalFormat.format(total) + " kkal");

                                adapter.updateData(listMakanan);
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onFailure(Call<HistoryResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                    }
                }
        );
    }

    @Override
    public void onClicked(View v, int position) {

    }
}