package com.example.kalori;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kalori.databinding.ActivityHistoryBinding;
import com.example.kalori.db.DBClient;
import com.example.kalori.db.MenuMakanan;
import com.example.kalori.model.history.response.HistoryResponse;
import com.example.kalori.network.ApiClient;
import com.example.kalori.network.ApiInterface;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    ActivityHistoryBinding uiBinding;
    Button btnTampilkan;
    ImageView imgTanggal;
    EditText edtTanggal;
    RelativeLayout rlNoData;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;
    RecyclerView rvMenuMakanan;
    String selectedDate;
    ProgressBar progressBar;
    HistoryAdapter adapter;
    private LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiBinding = ActivityHistoryBinding.inflate(getLayoutInflater());
        progressBar = uiBinding.progressCircular;
        btnTampilkan = uiBinding.btnTampilkan;
        imgTanggal = uiBinding.imgTglInput;
        edtTanggal = uiBinding.tglInputTampil;
        rlNoData = uiBinding.rlNoData;
        rvMenuMakanan = uiBinding.rvDaftarMakanan;
        setContentView(uiBinding.getRoot());
        rlNoData.setVisibility(View.GONE);
        adapter = new HistoryAdapter();
        rvMenuMakanan.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvMenuMakanan.setAdapter(adapter);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        selectedDate = "";

        imgTanggal.setOnClickListener(v -> {
            showDateDialog();
        });

        edtTanggal.setOnClickListener(v -> {
            showDateDialog();
        });

        btnTampilkan.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            if (selectedDate.equalsIgnoreCase("")) {
                Toast.makeText(this, "Pilih tanggal dulu", Toast.LENGTH_SHORT).show();
                return;
            }

            ApiClient.getClient().create(ApiInterface.class).getHistory(selectedDate, Prefs.getString("PREF_USERNAME")).enqueue(
                    new Callback<HistoryResponse>() {
                        @Override
                        public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getData().isEmpty()) {
                                    rlNoData.setVisibility(View.VISIBLE);
                                    rvMenuMakanan.setVisibility(View.GONE);
                                } else {
                                    rlNoData.setVisibility(View.GONE);
                                    rvMenuMakanan.setVisibility(View.VISIBLE);
                                    adapter.updateData(response.body().getData());
                                }
                            }
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<HistoryResponse> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
            );
        });

        chart = findViewById(R.id.chart);
        chart.setDrawGridBackground(false);

        String username = Prefs.getString("PREF_USERNAME", "");
        // Mengambil data dari API
        String apiUrl = "https://kadarnutrisimakanan.com/public/api/history?username=" + username + "&tanggal=";
        new FetchChartDataAsyncTask().execute(apiUrl);
    }

    //Kalender
    private void showDateDialog() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYEar, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYEar, dayOfMonth);

                edtTanggal.setText(dateFormat.format(newDate.getTime()));
                selectedDate = dateFormat.format(newDate.getTime());
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void refreshChart(List<ChartData> chartDataList) {
        // Mengubah data dari ChartData menjadi Entry
        List<Entry> entries = new ArrayList<>();
        List<Entry> entries2 = new ArrayList<>();
        for (int i = 0; i < chartDataList.size(); i++) {
            ChartData chartData = chartDataList.get(i);
            entries.add(new Entry((float) i, (float) chartData.getTotalKalori()));

            ChartData chartData2 = chartDataList.get(i);
            entries2.add(new Entry((float) i, (float) chartData2.getBatasKalori()));
        }

        // Mengatur label sumbu X
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new ValueFormatter() {
            private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                if (value >= 0 && value < chartDataList.size()) {
                    String date = chartDataList.get((int) value).getUpdatedAt();
                    return date;
                }
                return "";
            }
        });

        // Mengatur sumbu Y
        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setGranularity(1f);

        // Membuat dataset dan mengatur atributnya
        LineDataSet dataSet = new LineDataSet(entries, "Total Kalori Harian");
        dataSet.setColors(ColorTemplate.rgb("#FF5A00"));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(10f);

        // Membuat dataset dan mengatur atributnya
        LineDataSet dataSet2 = new LineDataSet(entries2, "Batas Kalori Harian");
        dataSet.setColors(ColorTemplate.rgb("#004489"));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(10f);

        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(dataSet);
        sets.add(dataSet2);

        // Menggabungkan dataset ke dalam LineData
        LineData lineData = new LineData(sets);
//        LineData lineData2 = new LineData(dataSet2);

        // Menampilkan chart
        chart.setData(lineData);
        chart.getAxisLeft().setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return (int) Math.ceil(Float.parseFloat(super.getAxisLabel(value, axis))) + " kkal";
            }
        });

        chart.animate();
        chart.getDescription().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
        chart.animateX(1000, Easing.EaseInSine);
        chart.invalidate();
    }

    private class FetchChartDataAsyncTask extends AsyncTask<String, Void, List<ChartData>> {

        @Override
        protected List<ChartData> doInBackground(String... urls) {
            List<ChartData> chartDataList = new ArrayList<>();
            String apiUrl = urls[0];
            String response = "";

            try {
                LocalDate currentDate = LocalDate.now();
                int currentYear = currentDate.getYear();
                int currentMonth = currentDate.getMonthValue();
                int currentDateOfMonths = currentDate.getDayOfMonth();

                // Get and print the dates for the previous seven days
                for (int i = 6; i >= 0; i--) {
                    int currentDateOfMonth = currentDateOfMonths - i;

                    if (currentDateOfMonth == 0) {
                        currentMonth--;
                        if (currentMonth == 0) {
                            currentYear--;
                            currentMonth = 12;
                        }
                        YearMonth yearMonth = YearMonth.of(currentYear, currentMonth);
                        currentDateOfMonth = yearMonth.lengthOfMonth();
                    }
                    String monthDate = (currentMonth < 10) ? "0" + currentMonth : String.valueOf(currentMonth);
                    String dayDate = (currentDateOfMonth < 10) ? "0" + currentDateOfMonth : String.valueOf(currentDateOfMonth);


                    String urlDate = apiUrl + currentYear + "-" + monthDate + "-" + dayDate;

                    System.out.println(urlDate);

                    URL url = new URL(urlDate);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    int responseCode = conn.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line;
                        response = "";
                        while ((line = in.readLine()) != null) {
                            response += line;
                        }
                        in.close();
                        System.out.println("...........     " + response);
                        // Parsing data dari response JSON
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray data = jsonObject.getJSONArray("data");
                        double total_kal = 0;
                        double batas_kal = 0;
                        for (int j = 0; j < data.length(); j++) {
                            JSONObject entry = data.getJSONObject(j);
                            total_kal += entry.getDouble("total_kalori_kkal");
                            double batas = Double.parseDouble(entry.getString("batas_kalori_harian"));
                            if (batas > 0) batas_kal = batas;
                        }
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate date = LocalDate.parse(currentYear + "-" + monthDate + "-" + dayDate, formatter);
                        DayOfWeek dayOfWeek = date.getDayOfWeek();
//                        String dayName = dayOfWeek.name();
//                        String capitalizedDayName = dayName.substring(0, 1).toUpperCase() + dayName.substring(1).toLowerCase();
                        String firstThreeLetters = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US);


                        // Membuat objek ChartData
//                        ChartData chartData = new ChartData(monthDate + "/" + dayDate, total_kal, batas_kal);
                        ChartData chartData = new ChartData(firstThreeLetters, total_kal, batas_kal);

                        chartDataList.add(chartData);
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return chartDataList;
        }

        @Override
        protected void onPostExecute(List<ChartData> chartDataList) {
            refreshChart(chartDataList);
        }
    }

    private static class ChartData {
        private String updatedAt;
        private double totalKalori;
        private double batasKalori;

        public ChartData(String updatedAt, double totalKalori, double batasKalori) {
            this.updatedAt = updatedAt;
            this.totalKalori = totalKalori;
            this.batasKalori = batasKalori;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public double getTotalKalori() {
            return totalKalori;
        }

        public double getBatasKalori() {
            return batasKalori;
        }
    }
}