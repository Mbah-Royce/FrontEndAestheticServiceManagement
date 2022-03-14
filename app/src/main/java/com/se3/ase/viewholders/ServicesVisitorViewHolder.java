package com.se3.ase.viewholders;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.se3.ase.AuthActivity;
import com.se3.ase.R;
import com.se3.ase.ServicesItem;

public class ServicesVisitorViewHolder extends RecyclerView.ViewHolder {
    SharedPreferences sharedPreferences;
    public TextView title;
    public TextView desc;
    public View view;
    public ImageView image;
    public ServicesItem service;
    public Context context;

    public ServicesVisitorViewHolder(@NonNull View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        desc = (TextView) itemView.findViewById(R.id.desc);
        image = (ImageView) itemView.findViewById(R.id.image_view);
        view = itemView;
        context = itemView.getContext();
        sharedPreferences = context.getSharedPreferences("MySharedPref",MODE_PRIVATE);
        itemView.findViewById(R.id.serviceLearnBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(context, AuthActivity.class);
                    context.startActivity(intent);
            }
        });
    }
}
