package com.se3.ase.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se3.ase.data.model.CategoryModel;
import com.se3.ase.R;
import com.se3.ase.viewholders.CategoryViewHolder;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder>{

    private List<CategoryModel> categories;
    private Context context;

    public void setResult(List<CategoryModel> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_category,parent,false);
        return  new CategoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryModel category = categories.get(position);
        holder.catDesc.setText(category.getDesc());
        holder.catDuration.setText("Dur:"+category.getDuration());
        holder.catTitle.setText(category.getName());
        holder.catPrice.setText("Price:"+category.getPrice());
        holder.catId.setText(category.getId());
        holder.category = category;

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
