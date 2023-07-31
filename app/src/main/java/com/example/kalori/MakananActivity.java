package com.example.kalori;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.kalori.adapter.NutrisiAdapter;
import com.example.kalori.databinding.ActivityMakananBinding;
import com.example.kalori.model.nutrisi.NutrisiDataItem;
import com.example.kalori.model.nutrisi.NutrisiResponse;
import com.example.kalori.network.ApiClient;
import com.example.kalori.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakananActivity extends AppCompatActivity {

    ActivityMakananBinding binding;
    NutrisiAdapter adapter;
    List<NutrisiDataItem> dataItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMakananBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new NutrisiAdapter(this, dataItems);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.listMakanan.setLayoutManager(layoutManager);
        binding.listMakanan.setNestedScrollingEnabled(false);
        binding.listMakanan.setAdapter(adapter);

        adapter.setOnItemClickListener((view, obj) -> {
            Intent i = new Intent();
            i.putExtra("nutrisi", obj);
            setResult(RESULT_OK, i);
            finish();
        });

        ApiClient.getClient().create(ApiInterface.class).getNutrisi().enqueue(new Callback<NutrisiResponse>() {
            @Override
            public void onResponse(Call<NutrisiResponse> call, Response<NutrisiResponse> response) {
                if (response.isSuccessful()){
                    if (!response.body().getData().isEmpty()){
                        dataItems = response.body().getData();
                        adapter.setItems(response.body().getData());
                    }
                }
            }

            @Override
            public void onFailure(Call<NutrisiResponse> call, Throwable t) {

            }
        });

        binding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!binding.inputSearch.getText().toString().isEmpty()){
                    filter(binding.inputSearch.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<NutrisiDataItem> filteredlist = new ArrayList<NutrisiDataItem>();

        // running a for loop to compare elements.
        for (NutrisiDataItem item : dataItems) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getNamaPangan().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist);
        }
    }
}