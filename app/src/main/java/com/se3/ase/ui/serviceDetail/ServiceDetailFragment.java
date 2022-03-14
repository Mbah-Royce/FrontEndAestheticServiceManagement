package com.se3.ase.ui.serviceDetail;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.se3.ase.R;
import com.se3.ase.adapters.CategoryAdapter;
import com.se3.ase.data.model.CategoryModel;
import com.se3.ase.databinding.FragmentServiceDetailBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.OkHttpClient;


public class ServiceDetailFragment extends Fragment {
    private FragmentServiceDetailBinding binding;
    private ServiceDataViewModel serviceDataViewModel;
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private List<CategoryModel> categories;
    OkHttpClient client;
    ImageView image;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentServiceDetailBinding.inflate(inflater, container, false);
        categoryAdapter = new CategoryAdapter();
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = binding.catRecy;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        serviceDataViewModel= new ViewModelProvider(this).get(ServiceDataViewModel.class);
        serviceDataViewModel.init(getContext());
        image = view.findViewById(R.id.image);
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading data.....");
        progressDialog.show();

        serviceDataViewModel.getServiceDetails(getArguments().getString("serviceId"));

        serviceDataViewModel.getServicesDetailResult().observe(getViewLifecycleOwner(), new Observer<ServiceDetailResult>() {
            @Override
            public void onChanged(ServiceDetailResult serviceDetailResult) {
                progressDialog.dismiss();
                if(serviceDetailResult.getError() != null){
                    showFailed(serviceDetailResult.getError());
                }
                if(serviceDetailResult.getSuccess() != null){
                    updateUiWithServices(serviceDetailResult.getSuccess());
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

    public void updateUiWithServices(ServiceDetaileView data){
//        binding.title.setText(data.getName());
        binding.desc.setText(data.getDesciption());
        Picasso.get().load(data.getImage()).into(image);
        categoryAdapter.setResult(data.getCategories(),getContext());
        recyclerView.setAdapter(categoryAdapter);
    }
}