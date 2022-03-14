package com.se3.ase.ui.servicesAuth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.se3.ase.R;
import com.se3.ase.databinding.FragmentRegistrationBinding;
import com.se3.ase.databinding.FragmentServiceAuthBinding;

public class ServiceAuthFragment extends Fragment {
    private FragmentServiceAuthBinding binding;
    public ServiceAuthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentServiceAuthBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }
}