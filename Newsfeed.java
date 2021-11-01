package com.example.publicohelpline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.publicohelpline.Databases.SessionManager;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Newsfeed extends AppCompatActivity {
    RecyclerView recView;
    MyNewsAdapter adapter;
    ImageView back_button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//to remove status and action bar in news feed
        back_button = findViewById(R.id.back_button_id);
        recView = findViewById(R.id.newsrecycleview);
        recView.setLayoutManager(new LinearLayoutManager(this));
        //taking constituency name from session
        SessionManager sessionManager = new SessionManager(this,SessionManager.SESSION_USER_SESSION);
        HashMap<String,String> userData = sessionManager.getUserDataFromSession();
        String adminConstituency = userData.get(SessionManager.KEY_CONSTITUENCY);
        //taking user type from session
        String type = userData.get(SessionManager.KEY_USER_TYPE);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("admin")) {
                    Intent i = new Intent(Newsfeed.this, Dashboardadmin.class);
                    startActivity(i);
                }
                else{
                    Intent intent = new Intent(Newsfeed.this, Dashboardpublic.class);
                    startActivity(intent);

                }
            }
        });





        FirebaseRecyclerOptions<NewsFeedHelperClass> options =
                new FirebaseRecyclerOptions.Builder<NewsFeedHelperClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("NewsFeed").orderByChild("admin_constituency").equalTo(adminConstituency), NewsFeedHelperClass.class)
                        .build();// query to get news from particular constituency

        adapter = new MyNewsAdapter(options);
        recView.setAdapter(adapter);

    }


    //o read the data before calling startListening() or your query will fail.
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    //Similarly, the stopListening() call removes the event listener and all data in the adapter. Call this method when the containing Activity or Fragment stops:

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}