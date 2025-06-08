package com.example.nomo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            navController.addOnDestinationChangedListener((nav, destination, arguments) -> {
                ImageButton iconAddEntry = findViewById(R.id.iconAddEntry);
                if (destination.getId() == R.id.mainFragment) {
                    iconAddEntry.setVisibility(View.VISIBLE);
                    iconAddEntry.setOnClickListener(v -> {
                        Log.d("MainActivity", "Nav to AddEntry");
                        nav.navigate(R.id.action_main_to_add_entry);
                    });
                } else if (destination.getId() == R.id.addEntryFragment) {
                    iconAddEntry.setVisibility(View.VISIBLE);
                    iconAddEntry.setOnClickListener(v -> {
                        Log.d("MainActivity", "Nav to Main");
                        nav.navigate(R.id.action_add_entry_to_main);
                    });
                }
            });
        }
    }

    private Fragment getCurrentFragment() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        return navHostFragment != null ?
                navHostFragment.getChildFragmentManager().getPrimaryNavigationFragment() : null;
    }
}
