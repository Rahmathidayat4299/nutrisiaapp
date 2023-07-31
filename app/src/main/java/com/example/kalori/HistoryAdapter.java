package com.example.kalori;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kalori.databinding.ItemHistoryBinding;
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

import kotlin.Pair;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<DataItemV2> data;
    private List<Pair<View, Integer>> heights = new ArrayList<>();
    private MakananClickListener listener;
    public HistoryAdapter(){
        data = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHistoryBinding binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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
        holder.uiBinding.namaMakanan1Input.setText(menu.makanan1);
        holder.uiBinding.namaMakanan2Input.setText(menu.makanan2);
        holder.uiBinding.namaMakanan3Input.setText(menu.makanan3);
        holder.uiBinding.namaMakanan4Input.setText(menu.makanan4);
        holder.uiBinding.beratMakanan1Input.setText(menu.bm1);
        holder.uiBinding.beratMakanan2Input.setText(menu.bm2);
        holder.uiBinding.beratMakanan3Input.setText(menu.bm3);
        holder.uiBinding.beratMakanan4Input.setText(menu.bm4);
        holder.uiBinding.txtHasilKkal1.setText(menu.kkal1);
        holder.uiBinding.txtHasilKkal2.setText(menu.kkal2);
        holder.uiBinding.txtHasilKkal3.setText(menu.kkal3);
        holder.uiBinding.txtHasilKkal4.setText(menu.kkal4);
        holder.uiBinding.txtHasilPro1.setText(menu.protein1);
        holder.uiBinding.txtHasilPro2.setText(menu.protein2);
        holder.uiBinding.txtHasilPro3.setText(menu.protein3);
        holder.uiBinding.txtHasilPro4.setText(menu.protein4);
        holder.uiBinding.txtHasilLemak1.setText(menu.lemak1);
        holder.uiBinding.txtHasilLemak2.setText(menu.lemak2);
        holder.uiBinding.txtHasilLemak3.setText(menu.lemak3);
        holder.uiBinding.txtHasilLemak4.setText(menu.lemak4);
        holder.uiBinding.txtHasilKarbo1.setText(menu.karbo1);
        holder.uiBinding.txtHasilKarbo2.setText(menu.karbo2);
        holder.uiBinding.txtHasilKarbo3.setText(menu.karbo3);
        holder.uiBinding.txtHasilKarbo4.setText(menu.karbo4);
        holder.uiBinding.expandableLayout.setVisibility(View.GONE);
        holder.uiBinding.btnDropdown.setImageResource(R.drawable.baseline_keyboard_arrow_left_24);
        holder.uiBinding.getRoot().setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(holder.uiBinding.layout, new AutoTransition());
            if (holder.uiBinding.expandableLayout.getVisibility() == View.GONE) {
                holder.uiBinding.expandableLayout.setVisibility(View.VISIBLE);
                holder.uiBinding.btnDropdown.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);
                holder.uiBinding.namaMakanan1Input.invalidate();
                holder.uiBinding.namaMakanan2Input.invalidate();
                holder.uiBinding.namaMakanan3Input.invalidate();
                holder.uiBinding.namaMakanan4Input.invalidate();
            } else if (holder.uiBinding.expandableLayout.getHeight()==0) {
                animateHeightFromZero(holder.uiBinding.expandableLayout, holder.uiBinding.getRoot());
                holder.uiBinding.btnDropdown.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);
            } else {
                shrinkViewToZero(holder.uiBinding.expandableLayout, holder.uiBinding.getRoot());
                holder.uiBinding.btnDropdown.setImageResource(R.drawable.baseline_keyboard_arrow_left_24);
            }
        });
    }

    private void shrinkViewToZero(View view, View root) {
        root.setEnabled(false);
        final int initialHeight = view.getHeight();
        heights.add(new Pair<>(view, initialHeight));

        // Create a ValueAnimator to animate the height from initialHeight to 0
        ValueAnimator animator = ValueAnimator.ofInt(initialHeight, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // Update the view's height during the animation
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = animatedValue;
                view.setLayoutParams(layoutParams);

                if(animatedValue==0) root.setEnabled(true);
            }
        });

        // Set the animation duration
        animator.setDuration(500);

        // Start the animation
        animator.start();
    }

    public void animateHeightFromZero(View view, View root) {
        root.setEnabled(false);
        int height = 0;
        for (int i=0; i<heights.size();i++) {
            if (heights.get(i).getFirst()==view) height = heights.get(i).getSecond();
        }
        if (height==0) height = view.getHeight();

        // Create a ValueAnimator to animate the height from 0 to targetHeight
        ValueAnimator animator = ValueAnimator.ofInt(0, height);
        int finalHeight = height;
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // Update the view's height during the animation
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = animatedValue;
                view.setLayoutParams(layoutParams);

                if(animatedValue== finalHeight) root.setEnabled(true);
            }
        });

        // Set the animation duration
        animator.setDuration(500);

        // Start the animation
        animator.start();
    }


    @Override
    public int getItemCount() {
        if (data == null) return 0;
        else return data.size();
    }

    public void updateData(List<DataItemV2> newData){
        this.data.clear();
        this.data.addAll(newData);
        for (int i = 0; i <heights.size() ; i++) {
            View view = heights.get(i).getFirst();
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = heights.get(i).getSecond();
            view.setLayoutParams(layoutParams);
        }
        heights.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ItemHistoryBinding uiBinding;

        public ViewHolder(@NonNull ItemHistoryBinding uiBinding) {
            super(uiBinding.getRoot());
            this.uiBinding = uiBinding;
        }
    }

    public interface MakananClickListener {
        void onClicked(View v, int position);
    }
}
