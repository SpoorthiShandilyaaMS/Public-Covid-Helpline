package com.example.publicohelpline;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyNewsAdapter extends FirebaseRecyclerAdapter<NewsFeedHelperClass, MyNewsAdapter.my_view_holder> {
    public MyNewsAdapter(@NonNull FirebaseRecyclerOptions<NewsFeedHelperClass> options) {  //constructor
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull my_view_holder holder, int position, @NonNull NewsFeedHelperClass model) {
        holder.title.setText(model.getNews_title());
        holder.description.setText(model.getNews_desc());
        holder.dateTime.setText(model.getDateTime());
//        holder.image.setText(model.getImage_url());
        Glide.with(holder.image.getContext()).load(model.getImage_url()).into(holder.image);

    }

    @NonNull
    @Override
    public my_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_singlerow,parent,false);
      return new my_view_holder(view);
    }

    class my_view_holder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title,description,dateTime;


        public my_view_holder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.image1id);
            title = (TextView)itemView.findViewById(R.id.title1id);
            description = (TextView)itemView.findViewById(R.id.desc1id);
            dateTime = (TextView)itemView.findViewById(R.id.datetimeid);

        }
    }
}
