package com.se3.ase.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se3.ase.R;
import com.se3.ase.ServicesItem;
import com.se3.ase.viewholders.ServicesViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ServicesAdpater extends RecyclerView.Adapter<ServicesViewHolder> {

    private List<ServicesItem> services;
    private Context context;

    @NonNull
    @Override
    public ServicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.services_card_item,parent,false);
        return new ServicesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesViewHolder holder, int position) {
        ServicesItem service = services.get(position);

        holder.title.setText(service.getTitle());
        holder.desc.setText(service.getDesc());
        Picasso.get().load(service.getImageurl()).into(holder.image);
        holder.service = service;
    }

    @Override
    public int getItemCount() { return services.size(); }

    public void setResults(List<ServicesItem> services, Context context) {
        this.services = services;
        this.context = context;
    }
}
