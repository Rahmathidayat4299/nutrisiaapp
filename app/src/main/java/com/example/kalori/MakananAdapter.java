package com.example.kalori;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kalori.databinding.ItemMakananBinding;
import com.example.kalori.db.MenuMakanan;
import com.example.kalori.model.history.response.DataItemV2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MakananAdapter extends RecyclerView.Adapter<MakananAdapter.ViewHolder> {

    private List<DataItemV2> data;
    private MakananClickListener listener;
    public MakananAdapter(){
        data = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMakananBinding binding = ItemMakananBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    public void setListener(MakananClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItemV2 menu = data.get(position);

        String dateTimeString = menu.created_at;

        // Parse the date and time string
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault());
        Date dateTime = null;
        try {
            dateTime = inputFormat.parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String formattedDate = "";
        String formattedTime = "";
        if (dateTime != null) {
            // Format the date and time
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            formattedDate = dateFormat.format(dateTime);
            formattedTime = timeFormat.format(dateTime);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime time = LocalTime.parse(formattedTime, formatter);
            LocalTime newTime = time.plusHours(8);
            formattedTime = newTime.format(formatter);
        }

        holder.uiBinding.tvKalori.setText(menu.total_kalori_kkal + " kkal");
        holder.uiBinding.tvTanggal.setText(formattedDate);
        holder.uiBinding.tvJam.setText(formattedTime);
        holder.uiBinding.tvProtein.setText("Protein: " + menu.total_protein_kkal + " gr");
        holder.uiBinding.tvLemak.setText("Lemak: " + menu.total_lemak_kkal + " gr");
        holder.uiBinding.tvKarbo.setText("Karbo: " + menu.total_karbo_kkal + " gr");
        holder.uiBinding.getRoot().setOnClickListener(v -> {
            listener.onClicked(v, position);
        });
    }

    @Override
    public int getItemCount() {
        if (data == null) return 0;
        else return data.size();
    }

    public void updateData(List<DataItemV2> newData){
        this.data.clear();
        this.data.addAll(newData);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ItemMakananBinding uiBinding;

        public ViewHolder(@NonNull ItemMakananBinding uiBinding) {
            super(uiBinding.getRoot());
            this.uiBinding = uiBinding;
        }
    }

    public interface MakananClickListener {
        void onClicked(View v, int position);
    }
}
