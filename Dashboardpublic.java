package com.example.publicohelpline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;


import com.example.publicohelpline.vaccineview.Vaccination;
import com.google.android.material.navigation.NavigationView;

public class Dashboardpublic extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    CardView card_view1, card_view2, card_view3, card_view4;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboardpublic);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//to remove status and action bar in dashboard
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);//toolbar
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);//navigation drawer menu
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //to make menu clickable
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);



        card_view1 = (CardView) findViewById(R.id.card1); // creating a CardView for  and assigning a value.
        card_view2 = (CardView) findViewById(R.id.card2);
        card_view3 = (CardView) findViewById(R.id.card3);
        card_view4 = (CardView) findViewById(R.id.card4);
        card_view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboardpublic.this, NewCard.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slidefrombottom, R.anim.stay);
            }
        });

        card_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboardpublic.this, CovidUpdates.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slidefrombottom, R.anim.stay);

            }

        });

        card_view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboardpublic.this, Complaintfeed.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slidefrombottom, R.anim.stay);
            }
        });

        card_view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboardpublic.this, Assembly.class);
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

    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     * @return true to display the item as the selected item
     */
    //this function is called when an item from navigation menu is selected
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        if (id == R.id.nav_news1){
            Intent intent = new Intent(Dashboardpublic.this, Newsfeed.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        if (id == R.id.nav_news2){
            Intent intent = new Intent(Dashboardpublic.this, CovidStatistics.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        if (id == R.id.nav_vaccine){
            Intent intent = new Intent(Dashboardpublic.this, Vaccination.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        if (id == R.id.nav_helpline){
            Intent intent = new Intent(Dashboardpublic.this, Helpline.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        if (id == R.id.nav_complaint){
            Intent intent = new Intent(Dashboardpublic.this, AddComplaint.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        if (id == R.id.nav_assembly){
            Intent intent = new Intent(Dashboardpublic.this, Assembly.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
    }



//    private void setSupportActionBar(Toolbar toolbar) {
//    }
}
