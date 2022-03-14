package com.se3.ase.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.se3.ase.R;
import com.se3.ase.data.model.AppointmentModel;
import com.se3.ase.viewholders.AppointmentViewHolder;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentViewHolder> {
    private List<AppointmentModel> appointments;
    private Context context;

    public void setResult(List<AppointmentModel> appointments, Context context){
        this.appointments = appointments;
        this.context = context;
    }
    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_item,parent,false);
        return new AppointmentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        AppointmentModel app = appointments.get(position);
        holder.date.setText(app.getAppointmentData());
        holder.service.setText(app.getServieName());
        holder.category.setText(app.getCategoryName());
        holder.session.setText(app.getSessionName());
        holder.status.setText(app.getAppointmentStatus());
        holder.createdOn.setText(app.getAppointmentCreatedAt());
        holder.updatedOn.setText(app.getAppointmentUpdataAt());

    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }
}
