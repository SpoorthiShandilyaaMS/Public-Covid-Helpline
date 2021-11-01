 package com.example.publicohelpline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;

import android.view.WindowManager;

import com.google.android.material.navigation.NavigationView;

public class Dashboardadmin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    CardView card_view1, card_view2, card_view3, card_view4;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboardadmin);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//to remove status and action bar in dashboard
        drawerLayout = findViewById(R.id.drawer_layout_admin);//
        navigationView = findViewById(R.id.nav_view_admin);
        toolbar = findViewById(R.id.toolbar_admin);
        setSupportActionBar(toolbar);//toolbar
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);//navigation drawer menu
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //to make menu clickable
        navigationView.setNavigationItemSelectedListener( this);
        navigationView.setCheckedItem(R.id.admin_nav_home);
        card_view1 = (CardView) findViewById(R.id.admincard1); // creating a CardView for  and assigning a value.
        card_view2 = (CardView) findViewById(R.id.admincard2);
        card_view3 = (CardView) findViewById(R.id.admincard3);
        card_view4 = (CardView) findViewById(R.id.admincard4);
//
        card_view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboardadmin.this, NewCard.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slidefrombottom, R.anim.stay);
            }
        });
        card_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboardadmin.this, CovidUpdates.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slidefrombottom, R.anim.stay);
            }
        });
        card_view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboardadmin.this, Complaintfeed.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slidefrombottom, R.anim.stay);
            }
        });

        card_view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboardadmin.this, Assembly.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slidefrombottom, R.anim.stay);
            }
        });
    }
    //back pressed

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }


    //    this function is called when an item from navigation menu is selected
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.admin_nav_home){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        if (id == R.id.admin_nav_addnews){
            Intent intent = new Intent(Dashboardadmin.this, AddNews.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        if (id == R.id.admin_nav_news1){
            Intent intent = new Intent(Dashboardadmin.this, Newsfeed.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        if (id == R.id.admin_nav_news2){
            Intent intent = new Intent(Dashboardadmin.this, CovidStatistics.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        if (id == R.id.admin_nav_complaint){
            Intent intent = new Intent(Dashboardadmin.this, Complaintfeed.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        if (id == R.id.admin_nav_assembly){
            Intent intent = new Intent(Dashboardadmin.this, Assembly.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
    }
}

