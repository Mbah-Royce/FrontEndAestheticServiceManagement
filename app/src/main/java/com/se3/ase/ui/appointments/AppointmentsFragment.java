package com.se3.ase.ui.appointments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.se3.ase.adapters.AppointmentAdapter;
import com.se3.ase.data.model.AppointmentModel;
import com.se3.ase.databinding.FragmentAppointmentsBinding;
import com.se3.ase.viewholders.AppointmentViewHolder;

import java.util.List;


public class AppointmentsFragment extends Fragment {

    private FragmentAppointmentsBinding binding;
    private AppointmentAdapter appointmentAdapter;
    private AppointmentViewModel appointmentViewModel;
    RecyclerView recyclerView;
    public AppointmentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAppointmentsBinding.inflate(inflater, container, false);
        appointmentAdapter = new AppointmentAdapter();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = binding.appointmentRecycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        appointmentViewModel = new ViewModelProvider(this).get(AppointmentViewModel.class);
        appointmentViewModel.init(getContext());
        appointmentViewModel.getAppointments();

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading data.....");
        progressDialog.show();

        appointmentViewModel.getAppointmentResult().observe(getViewLifecycleOwner(), new Observer<AppointmentResult>() {
            @Override
            public void onChanged(AppointmentResult appointmentResult) {
                progressDialog.dismiss();
                if(appointmentResult.getError() != null){
                    showFailed(appointmentResult.getError());
                }
                if(appointmentResult.getSuccess() != null){
                    updateUiWithAppointments(appointmentResult.getSuccess());
                }
            }
        });
    }

    public void showFailed(@StringRes Integer errorString){
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(
                    getContext().getApplicationContext(),
                    errorString,
                    Toast.LENGTH_LONG).show();
        }
    }

    public void updateUiWithAppointments(List<AppointmentModel> appointmentModelList){
        appointmentAdapter.setResult(appointmentModelList,getContext());
        recyclerView.setAdapter(appointmentAdapter);
    }
}