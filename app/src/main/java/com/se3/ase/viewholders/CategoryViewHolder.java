package com.se3.ase.viewholders;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se3.ase.AppointmentCreationActivity;
import com.se3.ase.data.model.CategoryModel;
import com.se3.ase.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder{
    public TextView catTitle;
    public TextView catPrice;
    public TextView catDesc;
    public TextView catDuration;
    public TextView catId;
    public Context context;
    public CategoryModel category;
    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        catTitle = (TextView) itemView.findViewById(R.id.cat_title);
        catPrice = (TextView) itemView.findViewById(R.id.cat_price);
        catDesc = (TextView) itemView.findViewById(R.id.cat_desc);
        catDuration = (TextView) itemView.findViewById(R.id.cat_duration);
        catId = (TextView) itemView.findViewById(R.id.cat_id);
        context = itemView.getContext();
        itemView.findViewById(R.id.cat_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AppointmentCreationActivity.class);
                intent.putExtra("category_id",category.getId());
                context.startActivity(intent);
                Log.d("demo","Book button clicked for service redirect to the other activity"+ category.getId());
            }
        });
    }
}
