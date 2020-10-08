package com.example.coronatracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.coronatracker.fragments.CountFragment;
import com.example.coronatracker.fragments.HomeFragment;
import com.example.coronatracker.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private CoordinatorLayout coordinatorLayout;
    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
//        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
//        progressBar_main = (ProgressBar) findViewById(R.id.progress_bar_mainAct);
        bottomNavigationView.setItemIconTintList(null);


        final HomeFragment homeFragment = new HomeFragment();
        final SearchFragment searchFragment = new SearchFragment();
        final CountFragment countFragment = new CountFragment();

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, homeFragment).commit();
        }

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

//                     fragment = null;
                    switch (item.getItemId()) {

                        case R.id.home:
                            fragment = homeFragment;
                            break;

                        case R.id.search:
                            fragment = searchFragment;
                            break;

                        case R.id.countries:
                            fragment = countFragment;
                            break;

                    }

//                    assert fragment != null;
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
                    return true;
                }

            });
        }

    @Override
    public void onBackPressed() {

        BottomNavigationView  bottomNavigationView1 = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        int selectedItemId = bottomNavigationView1.getSelectedItemId();

        if (R.id.home != selectedItemId){

            setHomeItem(MainActivity.this);
        }
        else {
        super.onBackPressed();  }
    }

    public static void setHomeItem(Activity activity){

        BottomNavigationView bottomNavigationView1 = (BottomNavigationView) activity.findViewById(R.id.bottomNavigation);
        bottomNavigationView1.setSelectedItemId(R.id.home);
    }

}
