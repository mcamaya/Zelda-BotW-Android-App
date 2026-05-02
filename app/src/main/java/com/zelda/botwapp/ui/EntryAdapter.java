package com.zelda.botwapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import coil.Coil;
import coil.request.ImageRequest;
import com.zelda.botwapp.R;
import com.zelda.botwapp.model.CompendiumEntry;
import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int id);
    }

    private final List<CompendiumEntry> entries;
    private final OnItemClickListener listener;

    public EntryAdapter(List<CompendiumEntry> entries, OnItemClickListener listener) {
        this.entries = entries;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CompendiumEntry entry = entries.get(position);
        holder.txtName.setText(entry.getName());

        ImageRequest request = new ImageRequest.Builder(holder.itemView.getContext())
                .data(entry.getImage())
                .target(holder.imgEntry)
                .build();
        Coil.imageLoader(holder.itemView.getContext()).enqueue(request);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(entry.getId()));
    }

    @Override
    public int getItemCount() { return entries.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgEntry;
        TextView txtName;

        ViewHolder(View itemView) {
            super(itemView);
            imgEntry = itemView.findViewById(R.id.imgEntry);
            txtName = itemView.findViewById(R.id.txtName);
        }
    }
}