package com.baiyu.mycalendar.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.baiyu.mycalendar.R;
import com.baiyu.mycalendar.databinding.ActivityMainBinding;
import com.baiyu.mycalendar.databinding.NavHeaderBinding;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;
    private NavHeaderBinding navHeaderBinding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        navHeaderBinding = NavHeaderBinding.bind(mainBinding.navigationView.getHeaderView(0));
        //open drawer
        mainBinding.toolbar.setNavigationOnClickListener(v -> {
            mainBinding.drawerLayout.openDrawer(GravityCompat.START);
        });

        /*binding.toolbar.setOnMenuItemClickListener(item -> {
            // Do something
            return item.getItemId() == R.id.account;
        })*/

        //set appBar padding
        ViewCompat.setOnApplyWindowInsetsListener(mainBinding.appBarLayout, (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            view.setPadding(
                    view.getPaddingLeft(),
                    systemBars.top,
                    view.getPaddingRight(),
                    view.getPaddingBottom()
            );

            return insets;
        });
        //cast to NavHostFragment
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment);
        //set NavController
        assert navHostFragment != null;
        navController =
                navHostFragment.getNavController();
        //set navigation item select listener
        mainBinding.navigationView.setNavigationItemSelectedListener(item -> {

            boolean handled = NavigationUI
                    .onNavDestinationSelected(
                            item,
                            navController
                    );
            //closer drawer
            if (handled) {
                mainBinding.drawerLayout.closeDrawer(GravityCompat.START);
            }

            return handled;
        });
        //set settings bottom onclick listener
        navHeaderBinding.settingsBtn.setOnClickListener(button -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }
}