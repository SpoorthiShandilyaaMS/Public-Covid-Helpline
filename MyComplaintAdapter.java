package com.example.publicohelpline;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MyComplaintAdapter extends FirebaseRecyclerAdapter<ComplaintFeedHelperClass, MyComplaintAdapter.myView_holder> {
    public MyComplaintAdapter(@NonNull FirebaseRecyclerOptions<ComplaintFeedHelperClass> options) {  //constructor
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull MyComplaintAdapter.myView_holder holder, int position, @NonNull ComplaintFeedHelperClass model) {
        holder.title.setText(model.getComplaint_title());
        holder.description.setText(model.getComplaint_desc());
        holder.sector.setText("Sector : "+model.getSector_name());
        holder.postedBy.setText("Posted by : "+model.getPosted_by_name());
        holder.email.setText("Email : "+model.getUser_email());
        holder.dateTime.setText(model.getDateTime());
//        holder.image.setText(model.getImage_url());
        Glide.with(holder.image.getContext()).load(model.getImage_url()).into(holder.image);
    }

    @NonNull
    @Override
    public MyComplaintAdapter.myView_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_singlerow,parent,false);
        return new MyComplaintAdapter.myView_holder(view);

    }

    class myView_holder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title,description,sector,postedBy,email,dateTime;


        public myView_holder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.imagecid);
            title = (TextView)itemView.findViewById(R.id.titlecid);
            description = (TextView)itemView.findViewById(R.id.desccid);
            sector = (TextView)itemView.findViewById(R.id.sectorcid);
            postedBy = (TextView)itemView.findViewById(R.id.postedbycid);
            email = (TextView)itemView.findViewById(R.id.emailccid);
            dateTime = (TextView)itemView.findViewById(R.id.datetimecid);

        }
    }
}
