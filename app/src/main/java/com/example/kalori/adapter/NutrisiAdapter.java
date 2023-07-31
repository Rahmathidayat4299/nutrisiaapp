package com.example.kalori.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kalori.R;
import com.example.kalori.databinding.ItemNutrisiBinding;
import com.example.kalori.model.nutrisi.NutrisiDataItem;

import java.util.ArrayList;
import java.util.List;


public class NutrisiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context ctx;
    private List<NutrisiDataItem> items = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, NutrisiDataItem obj);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ItemNutrisiBinding binding;

        public ViewHolder(View v) {
            super(v);
            binding = ItemNutrisiBinding.bind(v);
        }
    }

    public NutrisiAdapter(Context ctx, List<NutrisiDataItem> items) {
        this.ctx = ctx;
        this.items = items;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<NutrisiDataItem> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        items = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nutrisi, parent, false);
        vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            final ViewHolder vItem = (ViewHolder) holder;
            final NutrisiDataItem c = items.get(position);
//            vItem.binding.tvEnergi.setText(c.getEnergi() + " Kkal");
            vItem.binding.tvJenisMakanan.setText(c.getNamaPangan());
//            vItem.binding.tvKarbo.setText(c.getKarbohidrat());
//            vItem.binding.tvTipe.setText(c.getKelompok());
//            vItem.binding.tvLemak.setText(c.getLemak());
//            vItem.binding.tvProtein.setText(c.getProtein());

            vItem.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, c);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setItems(List<NutrisiDataItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }


}