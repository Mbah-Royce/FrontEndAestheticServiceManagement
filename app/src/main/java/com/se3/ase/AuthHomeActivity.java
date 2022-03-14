package com.se3.ase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.se3.ase.databinding.ActivityAuthHomeBinding;
import com.se3.ase.ui.appointments.AppointmentsFragment;
import com.se3.ase.ui.notifications.NotificationsFragment;
import com.se3.ase.ui.services.ServicesFragment;
import com.se3.ase.ui.servicesAuth.ServiceAuthFragment;

public class AuthHomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private ActivityAuthHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAuthHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        BottomNavigationView navView = binding.navView;

        navView.setOnNavigationItemSelectedListener(this);
        navView.setSelectedItemId(R.id.navigation_services);



//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_services, R.id.navigation_appointments, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_auth_home);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);
    }
    ServiceAuthFragment firstFragment = new ServiceAuthFragment();
    AppointmentsFragment secondFragment = new AppointmentsFragment();
    NotificationsFragment thirdFragment = new NotificationsFragment();
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_services:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, firstFragment).commit();
                return true;

            case R.id.navigation_appointments:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, secondFragment).commit();
                return true;

            case R.id.navigation_notifications:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, thirdFragment).commit();
                return true;
        }
        return false;
    }
}