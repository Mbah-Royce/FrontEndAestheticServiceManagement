package com.se3.ase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.se3.ase.databinding.ActivityVisitorHomeBinding;
import com.se3.ase.ui.aboutus.AboutUsFragment;
import com.se3.ase.ui.services.ServicesFragment;
import com.se3.ase.ui.visitorservices.ServiceVisitorFragment;

public class VisitorHomeActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityVisitorHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityVisitorHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        swipeAdapter vpAdapter = new swipeAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        TabLayout tabLayout = binding.visitorHomeTablayout;
        ViewPager viewPager = binding.visitorHomeViewpager;
        final TextView loginRegisterTextView = binding.accountRedirect;
        loginRegisterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VisitorHomeActivity.this,AuthActivity.class);
                startActivity(intent);
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        vpAdapter.addFragment(new ServiceVisitorFragment(), "Services");
        vpAdapter.addFragment(new AboutUsFragment(), "About Us");
        vpAdapter.addFragment(new AboutUsFragment(), "Popular");
        viewPager.setAdapter(vpAdapter);    }
}