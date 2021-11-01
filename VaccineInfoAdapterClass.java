package com.example.publicohelpline.vaccineadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.publicohelpline.R;
import com.example.publicohelpline.vaccinemodel.VaccineModelClass;

import java.util.List;

public class VaccineInfoAdapterClass extends RecyclerView.Adapter<VaccineInfoAdapterClass.ViewHolder> {

    private LayoutInflater layoutInflater ;
    private List<VaccineModelClass> list_vaccine_center;

    public VaccineInfoAdapterClass(Context mcontext, List<VaccineModelClass> list_vaccine_center) {
        this.layoutInflater = layoutInflater.from(mcontext);
        this.list_vaccine_center = list_vaccine_center;
    }

    @NonNull
    @Override
    public VaccineInfoAdapterClass.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.vaccination_single_item,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull VaccineInfoAdapterClass.ViewHolder holder, int position) {

        holder.vaccinationCenter.setText(list_vaccine_center.get(position).getVaccinationCenter());
        holder.vaccinationCenterAddress.setText(list_vaccine_center.get(position).getVaccinationCenterAddress());
        holder.vaccinationTimings.setText(String.format("%s - %s", list_vaccine_center.get(position).getVaccinationTimings(), list_vaccine_center.get(position).getVaccineCenterTime()));
        holder.vaccinationName.setText(list_vaccine_center.get(position).getVaccinationName());
        holder.vaccineAvailable.setText(list_vaccine_center.get(position).getVaccineAvailable());
        holder.vaccinationCharges.setText(list_vaccine_center.get(position).getVaccinationCharges());
        holder.vaccinationAge.setText(list_vaccine_center.get(position).getVaccinationAge());
    }

    @Override
    public int getItemCount() {
        return list_vaccine_center.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView vaccinationCenter,vaccinationCharges,vaccinationAge,vaccinationTimings,vaccinationName,vaccinationCenterAddress,vaccineAvailable;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vaccinationCenter = itemView.findViewById(R.id.vacccenterid);
            vaccinationCharges = itemView.findViewById(R.id.vaccfeeid);
            vaccinationAge = itemView.findViewById(R.id.vaccageid);
            vaccinationTimings = itemView.findViewById(R.id.timevaccid);
            vaccinationName = itemView.findViewById(R.id.vaccnameid);
            vaccinationCenterAddress = itemView.findViewById(R.id.locvaccid);
            vaccineAvailable = itemView.findViewById(R.id.vaccavailibilityid);

        }
    }
}
