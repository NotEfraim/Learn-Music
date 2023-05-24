package com.example.learnmusic;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class Tutor_admin extends AppCompatActivity {

    ViewPager tutorPager;
    TabLayout tutortablayout;
    TabItem tutorProfile, tutorStudent, tutorRequest;
    Tutor_tab_pager_adapter tutor_tab_pager_adapter;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_admin);

        tutorPager = findViewById(R.id.viewPager);
        tutortablayout = findViewById(R.id.tutorAdmin_tablayout);

        back = findViewById(R.id.tutor_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tutor_admin.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

        tutortablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tutorPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tutorPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tutortablayout));

        // Tablayout Items
        tutorProfile = findViewById(R.id.tutorMyProfile);
        tutorStudent = findViewById(R.id.tutorStudentList);
        tutorRequest = findViewById(R.id.tutorRequest);

        tutor_tab_pager_adapter = new Tutor_tab_pager_adapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, tutortablayout.getTabCount());
        tutorPager.setAdapter(tutor_tab_pager_adapter);


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Tutor_admin.this, Home.class);
        startActivity(intent);
        finish();
    }
}