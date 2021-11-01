package com.example.publicohelpline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.publicohelpline.Databases.SessionManager;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Complaintfeed extends AppCompatActivity {
    RecyclerView recView;
    MyComplaintAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaintfeed);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//to remove status and action bar in complaint feed

        recView = findViewById(R.id.complaintrecycleview);
        recView.setLayoutManager(new LinearLayoutManager(this));


        //taking constituency name from session
        SessionManager sessionManager = new SessionManager(this,SessionManager.SESSION_USER_SESSION);
        HashMap<String,String> userData = sessionManager.getUserDataFromSession();
        String userConstituency = userData.get(SessionManager.KEY_CONSTITUENCY);

        FirebaseRecyclerOptions<ComplaintFeedHelperClass> options =
                new FirebaseRecyclerOptions.Builder<ComplaintFeedHelperClass>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Complaints").orderByChild("user_constituency").equalTo(userConstituency), ComplaintFeedHelperClass.class)
                        .build();// query to get news from particular constituency

        adapter = new MyComplaintAdapter(options);
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
