package com.example.nomo;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Получаем NavHostFragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
    }

    // Метод для перехода на AddEntryFragment
    public void navigateToAddEntry() {
        if (navController != null && navController.getCurrentDestination() != null) {
            navController.navigate(R.id.addEntryFragment);
        } else {
            Toast.makeText(this, "NavController не готов", Toast.LENGTH_SHORT).show();
        }
    }
}
