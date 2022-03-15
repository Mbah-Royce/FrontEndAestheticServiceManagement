package com.se3.ase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.se3.ase.databinding.ActivityAuthHomeBinding;
import com.se3.ase.ui.appointments.AppointmentsFragment;
import com.se3.ase.ui.notifications.NotificationsFragment;
import com.se3.ase.ui.services.ServicesFragment;
import com.se3.ase.ui.servicesAuth.ServiceAuthFragment;

public class AuthHomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private ActivityAuthHomeBinding binding;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAuthHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        BottomNavigationView navView = binding.navView;

        navView.setOnNavigationItemSelectedListener(this);
        navView.setSelectedItemId(R.id.navigation_services);

        TextView logout = binding.accountRedirect;
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("token", null);
                myEdit.putString("userid", null);
                myEdit.putString("name", null);
                myEdit.putString("email", null);
                myEdit.commit();
                Intent intent = new Intent(AuthHomeActivity.this,AuthActivity.class);
                startActivity(intent);
                finish();
            }
        });
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