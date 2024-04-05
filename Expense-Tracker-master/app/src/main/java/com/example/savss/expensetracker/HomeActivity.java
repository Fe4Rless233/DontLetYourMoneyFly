package com.example.savss.expensetracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {

    private int selectedID = -1;

    // Listener for bottom navigation items selection
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_add_transaction:
                    // Replace the current fragment with AddTransactionFragment
                    return setFragment(new AddTransactionFragment(), R.id.navigation_add_transaction);
                case R.id.navigation_dashboard:
                    // Replace the current fragment with DashboardFragment
                    return setFragment(new DashboardFragment(), R.id.navigation_dashboard);
                case R.id.navigation_settings:
                    // Replace the current fragment with SettingsFragment
                    return setFragment(new SettingsFragment(), R.id.navigation_settings);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize the local database helper and user data
        LocalDatabaseHelper localDatabaseHelper = new LocalDatabaseHelper(this, null, null, 1);
        localDatabaseHelper.initializeUserData(UserData.userID);

        // Set up bottom navigation view
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Set the initial fragment to be displayed (DashboardFragment)
        setFragment(new DashboardFragment(), R.id.navigation_dashboard);
    }

    // Method to set the fragment in the frame layout
    private boolean setFragment(Fragment fragment, int id) {
        // Check if the selected navigation item is already selected
        if (selectedID == id) {
            return false;
        }
        // Update the selected navigation item ID
        selectedID = id;
        // Begin the fragment transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace the current fragment with the new one
        transaction.replace(R.id.frame_layout, fragment);
        // Commit the transaction
        transaction.commit();
        return true;
    }

    // Override onBackPressed to move the task to the back stack
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
