package com.se3.ase.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se3.ase.R;

public class AppointmentViewHolder extends RecyclerView.ViewHolder {
    public TextView date;
    public TextView category;
    public TextView session;
    public TextView service;
    public TextView createdOn;
    public TextView updatedOn;
    public TextView status;
    public AppointmentViewHolder(@NonNull View itemView) {
        super(itemView);
        date = (TextView) itemView.findViewById(R.id.appDate);
        category = (TextView) itemView.findViewById(R.id.appCategory);
        session = (TextView) itemView.findViewById(R.id.appSession);
        service = (TextView) itemView.findViewById(R.id.appService);
        createdOn = (TextView) itemView.findViewById(R.id.appCreatedOn);
        updatedOn = (TextView) itemView.findViewById(R.id.appUpdatedOn);
        status = (TextView) itemView.findViewById(R.id.appStatus);

    }
}
