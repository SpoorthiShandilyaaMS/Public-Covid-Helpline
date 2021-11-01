package com.example.publicohelpline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.publicohelpline.covidtracker.CountryData;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

public class CountryCovidAdapter extends RecyclerView.Adapter<CountryCovidAdapter.CountryViewHolder>{
    private Context context;
    private List<CountryData> list;

    public CountryCovidAdapter(Context context, List<CountryData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.country_item_covid,parent,false);
        return new CountryViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {

        CountryData data = list.get(position);
        holder.countryCases.setText(NumberFormat.getInstance().format(Integer.parseInt(data.getCases())));
        holder.countryName.setText(data.getCountry());
        holder.sno.setText(String.valueOf(position+1));

        Map<String,String> img = data.getCountryInfo();
        Glide.with(context).load(img.get("flag")).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CountryViewHolder extends RecyclerView.ViewHolder {

        private TextView sno,countryName,countryCases;
        private ImageView imageView;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            sno = itemView.findViewById(R.id.sno);
            countryName = itemView.findViewById(R.id.countrynamecovid);
            countryCases = itemView.findViewById(R.id.covidcasescovid);
            imageView = itemView.findViewById(R.id.countryflag);
        }
    }
}
