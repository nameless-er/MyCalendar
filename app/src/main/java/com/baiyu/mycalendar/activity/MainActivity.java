package com.baiyu.mycalendar.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.baiyu.mycalendar.R;
import com.baiyu.mycalendar.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setNavigationOnClickListener(v -> {
            binding.drawerLayout.openDrawer(GravityCompat.START);
        });

        /*binding.toolbar.setOnMenuItemClickListener(item -> {
            // Do something
            return item.getItemId() == R.id.account;
        })*/

        //cast to NavHostFragment
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment);
        //set NavController
        assert navHostFragment != null;
        navController =
                navHostFragment.getNavController();
        //set navigation item select listener
        binding.navigationView.setNavigationItemSelectedListener(item -> {

            boolean handled = NavigationUI
                    .onNavDestinationSelected(
                            item,
                            navController
                    );
            //closer drawer
            if (handled) {
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }

            return handled;
        });
    }
}