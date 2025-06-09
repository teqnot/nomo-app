package com.example.nomo;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.nomo.ui.friends.FriendsFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View topbar = findViewById(R.id.include_topbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            navController.addOnDestinationChangedListener((nav, destination, arguments) -> {
                ImageButton iconFriends = topbar.findViewById(R.id.iconFriends);
                ImageButton iconAddEntry = findViewById(R.id.iconAddEntry);

                if (destination.getId() == R.id.mainFragment) {
                    iconFriends.setVisibility(View.VISIBLE);
                    iconFriends.setOnClickListener(v -> {
                        nav.navigate(R.id.action_main_to_friends);
                    });

                    iconAddEntry.setVisibility(View.VISIBLE);
                    iconAddEntry.setOnClickListener(v -> {
                        Log.d("MainActivity", "Nav to AddEntry");
                        nav.navigate(R.id.action_main_to_add_entry);
                    });
                } else if (destination.getId() == R.id.addEntryFragment) {
                    iconFriends.setVisibility(View.VISIBLE);
                    iconFriends.setOnClickListener(v -> {
                        nav.navigate(R.id.action_add_entry_to_friends);
                    });

                    iconAddEntry.setVisibility(View.VISIBLE);
                    iconAddEntry.setOnClickListener(v -> {
                        Log.d("MainActivity", "Nav to Main");
                        nav.navigate(R.id.action_add_entry_to_main);
                    });
                } else if (destination.getId() == R.id.friendsFragment) {
                    iconFriends.setVisibility(View.VISIBLE);
                    iconFriends.setOnClickListener(v -> {
                        Log.d("NAV_TEST", "Friends button clicked!");
                        nav.navigate(R.id.action_friends_to_main);
                    });

                    iconAddEntry.setVisibility(View.VISIBLE);
                    iconAddEntry.setOnClickListener(v -> {
                        Log.d("MainActivity", "Nav to Main");
                        nav.navigate(R.id.action_friends_to_add_entry);
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
