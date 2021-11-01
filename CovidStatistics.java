package com.example.publicohelpline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.publicohelpline.covidtracker.CountryData;
import com.example.publicohelpline.covidtracker.CovidTrackerUtilities;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.gson.internal.bind.util.ISO8601Utils.format;

public class CovidStatistics extends AppCompatActivity {
    private TextView totalConfirm,totalActive,totalRecovered,totalDeath,totalTest,updatedAt;
    private TextView todayConfirm,todayRecovered,todayDeath;
    private List<CountryData> list;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_statistics);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        list = new ArrayList<>();

        initialize();
        findViewById(R.id.conname).setOnClickListener(v ->
                startActivity(new Intent(CovidStatistics.this,CountryWiseCovid.class)));
        CovidTrackerUtilities.getcovidTrackerInterface().getCountryData().enqueue(new Callback<List<CountryData>>() {
            @Override
            public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                list.addAll(response.body());
                for(int i=0;i<list.size();i++){
                    if(list.get(i).getCountry().equals("India")){

                        int confirm =Integer.parseInt(list.get(i).getCases());
                        int active =Integer.parseInt(list.get(i).getActive());
                        int recovered =Integer.parseInt(list.get(i).getRecovered());
                        int death =Integer.parseInt(list.get(i).getDeaths());
                        int todConfirm =Integer.parseInt(list.get(i).getTodayCases());
                        int todRecovered =Integer.parseInt(list.get(i).getTodayRecovered());
                        int todDeath =Integer.parseInt(list.get(i).getTodayDeaths());
                        int tests =Integer.parseInt(list.get(i).getTests());


                        totalConfirm.setText(NumberFormat.getInstance().format(confirm));
                        totalActive.setText(NumberFormat.getInstance().format(active));
                        totalRecovered.setText(NumberFormat.getInstance().format(recovered));
                        totalDeath.setText(NumberFormat.getInstance().format(death));
                        todayConfirm.setText(String.format("( %s )", NumberFormat.getInstance().format(todConfirm)));
                        todayRecovered.setText(String.format("( %s )", NumberFormat.getInstance().format(todRecovered)));
                        todayDeath.setText(String.format("( %s )", NumberFormat.getInstance().format(todDeath)));
                        totalTest.setText(NumberFormat.getInstance().format(tests));

                        setText(list.get(i).getUpdated());


                        pieChart.addPieSlice(new PieModel("Confirm", confirm, getResources().getColor(R.color.yellow)));
                        pieChart.addPieSlice(new PieModel("Active", active, getResources().getColor(R.color.blue_pie)));
                        pieChart.addPieSlice(new PieModel("Recovered", recovered, getResources().getColor(R.color.green_pie)));
                        pieChart.addPieSlice(new PieModel("Death", death, getResources().getColor(R.color.redd)));

                        pieChart.startAnimation();
                    }

                }

            }

            @Override
            public void onFailure(Call<List<CountryData>> call, Throwable t) {
                Toast.makeText(CovidStatistics.this, "Error  : "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void setText(String updated){
        DateFormat format = new SimpleDateFormat("MMM dd,yyyy");
        long milliseconds = Long.parseLong(updated);
        Calendar calender = Calendar.getInstance();
        calender.setTimeInMillis(milliseconds);
        updatedAt.setText("Updated at "+format.format(calender.getTime()));

    }

    private void initialize() {
        totalConfirm = findViewById(R.id.totalconfirm);
        todayConfirm = findViewById(R.id.todayconfirm);
        totalActive = findViewById(R.id.totalactive);
        totalRecovered = findViewById(R.id.totalrecovered);
        todayRecovered = findViewById(R.id.todayrecovered);
        totalDeath = findViewById(R.id.totaldeath);
        todayDeath = findViewById(R.id.todaydeath);
        totalTest = findViewById(R.id.totaltests);
        pieChart = findViewById(R.id.piechart);
        updatedAt = findViewById(R.id.updatedatdate);
    }
}
