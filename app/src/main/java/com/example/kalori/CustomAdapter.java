package com.example.kalori;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class  CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    Activity activity;
    private ArrayList user_id, tgl_input, kategori_input,
            makanan1_input, makanan2_input, makanan3_input, makanan4_input,
            bm1, bm2, bm3, bm4,
            totalcl_input;

    CustomAdapter(Activity activity, Context context,
                  ArrayList user_id,
                  ArrayList tgl_input,
                  ArrayList kategori_input,
                  ArrayList makanan1_input,
                  ArrayList makanan2_input,
                  ArrayList makanan3_input,
                  ArrayList makanan4_input,
                  ArrayList bm1,
                  ArrayList bm2,
                  ArrayList bm3,
                  ArrayList bm4,
                  ArrayList totalcl_input){
        this.activity = activity;
        this.context = context;
        this.user_id = user_id;
        this.tgl_input = tgl_input;
        this.kategori_input = kategori_input;
        this.makanan1_input = makanan1_input;
        this.makanan2_input = makanan2_input;
        this.makanan3_input = makanan3_input;
        this.makanan4_input = makanan4_input;
        this.bm1 = bm1;
        this.bm2 = bm2;
        this.bm3 = bm3;
        this.bm4 = bm4;
        this.totalcl_input = totalcl_input;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, final int position) {
        holder.tgl_txt.setText(String.valueOf(tgl_input.get(position)));
        holder.kategori_txt.setText(String.valueOf(kategori_input.get(position)));
        holder.totalcl_txt.setText(String.valueOf(totalcl_input.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(user_id.get(position)));
                intent.putExtra("tgl", String.valueOf(tgl_input.get(position)));
                intent.putExtra("kategori", String.valueOf(kategori_input.get(position)));
                intent.putExtra("makanan1", String.valueOf(makanan1_input.get(position)));
                intent.putExtra("makanan2", String.valueOf(makanan2_input.get(position)));
                intent.putExtra("makanan3", String.valueOf(makanan3_input.get(position)));
                intent.putExtra("makanan4", String.valueOf(makanan4_input.get(position)));
                intent.putExtra("totalcl", String.valueOf(totalcl_input.get(position)));
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return user_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView user_id_txt, tgl_txt, kategori_txt, totalcl_txt;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tgl_txt = itemView.findViewById(R.id.tgl_txt);
            kategori_txt = itemView.findViewById(R.id.kategori_txt);
            totalcl_txt = itemView.findViewById(R.id.totalcl_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
