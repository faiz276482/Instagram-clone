package com.nerdytech.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.nerdytech.instagram.Fragments.HomeFragment;
import com.nerdytech.instagram.Fragments.NotificationFragment;
import com.nerdytech.instagram.Fragments.ProfileFragment;
import com.nerdytech.instagram.Fragments.SearchFragment;

public class MainActivity extends AppCompatActivity {

    //widgets
    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView=findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.nav_home: {
                        selectorFragment = new HomeFragment();
                        break;
                    }
                    case R.id.nav_search: {
                        selectorFragment = new SearchFragment();
                        break;
                    }
                    case R.id.nav_add: {
                        selectorFragment = null;
                        startActivity(new Intent(MainActivity.this,PostActivity.class));
                        break;
                    }
                    case R.id.nav_heart: {
                        selectorFragment = new NotificationFragment();
                        break;
                    }
                    case R.id.nav_profile: {
                        selectorFragment = new ProfileFragment();

                        break;
                    }
                }

                if(selectorFragment!=null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectorFragment).commit();
                }
                return true;
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

    }
}
