package com.se3.ase.ui.appointmentcreation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.se3.ase.R;
import com.se3.ase.databinding.FragmentAppointmentCreationBinding;

public class AppointmentCreationFragment extends Fragment {

    private FragmentAppointmentCreationBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAppointmentCreationBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}