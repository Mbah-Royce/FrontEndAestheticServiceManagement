package com.se3.ase.ui.visitorservices;

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

import com.se3.ase.R;
import com.se3.ase.ServicesItem;
import com.se3.ase.adapters.ServicesAdpater;
import com.se3.ase.adapters.ServicesVisitorAdapter;
import com.se3.ase.databinding.FragmentServiceVisitorBinding;
import com.se3.ase.databinding.FragmentServicesBinding;
import com.se3.ase.ui.services.ServicesResult;
import com.se3.ase.ui.services.ServicesViewModel;

import java.util.List;

public class ServiceVisitorFragment extends Fragment {

    private FragmentServiceVisitorBinding binding;
    private ServicesVisitorAdapter servicesAdpater;
    private ServicesViewModel servicesViewModel;
    RecyclerView recyclerView;
    boolean visitor = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentServiceVisitorBinding.inflate(inflater, container, false);
        servicesAdpater = new ServicesVisitorAdapter();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = binding.servicesRecycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        servicesViewModel = new ViewModelProvider(this).get(ServicesViewModel.class);
        servicesViewModel.init(getContext());
        servicesViewModel.getServices();

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading data.....");
        progressDialog.show();

        servicesViewModel.getServicesResult().observe(getViewLifecycleOwner(), new Observer<ServicesResult>() {
            @Override
            public void onChanged(ServicesResult servicesResult) {
                progressDialog.dismiss();
                if(servicesResult.getError() != null){
                    showRegisterFailed(servicesResult.getError());
                }
                if(servicesResult.getSuccess() != null){
                    updateUiWithServices(servicesResult.getSuccess());
                }
            }
        });

    }
    public void showRegisterFailed(@StringRes Integer errorString){
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(
                    getContext().getApplicationContext(),
                    errorString,
                    Toast.LENGTH_LONG).show();
        }
    }

    public void updateUiWithServices(List<ServicesItem> services){
        servicesAdpater.setResults(services,getContext());
        recyclerView.setAdapter(servicesAdpater);
    }
}