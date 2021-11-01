package com.example.publicohelpline.vaccineview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.publicohelpline.R;
import com.example.publicohelpline.vaccineadapter.VaccineInfoAdapterClass;
import com.example.publicohelpline.vaccinemodel.VaccineModelClass;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Vaccination extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    String base_url = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin";

    private TextInputEditText areaPinCode;
    Button button;
    ProgressBar holdOnProgress;
    private ArrayList<VaccineModelClass> vaccine_centers;
    private RecyclerView resultRecyclerView;
    String areaPin,avlDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //to remove status and action bar in vaccination
        mapViews();
        onClickSetup();
    }

    private void onClickSetup() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holdOnProgress.setVisibility(View.VISIBLE);
                DialogFragment dp = new PickDate();
                dp.show(getSupportFragmentManager(),"Pick a Date");
            }
        });

    }

    private void mapViews() {
        button = findViewById(R.id.searchhbtnid);
        holdOnProgress = findViewById(R.id.progressbarvaccid);
        areaPinCode = findViewById(R.id.pincodeid);
        resultRecyclerView = findViewById(R.id.vaccrecycleview);
        resultRecyclerView.setHasFixedSize(true);
        vaccine_centers = new ArrayList<VaccineModelClass>();


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar k = Calendar.getInstance();
        k.set(Calendar.YEAR,year);
        k.set(Calendar.MONTH,month);
        k.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
        dateFormat.setTimeZone(k.getTimeZone());
        String d = dateFormat.format(k.getTime());
       setup(d);

    }

    private void setup(String d) {
        avlDate = d;
        fetchDataNow();

    }

    private void fetchDataNow() {
        vaccine_centers.clear();
        areaPin = areaPinCode.getText().toString();
        String url_api = base_url + "?pincode=" +areaPin + "&date=" + avlDate;
        Log.d("msn","url_api"+url_api);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray sessionArray = object.getJSONArray("sessions");
                    int i;
                    for(i = 0; i < sessionArray.length(); i++) {
                        JSONObject sesObject = sessionArray.getJSONObject(i);
                        VaccineModelClass vaccineModel = new VaccineModelClass();
                        vaccineModel.setVaccinationCenter(sesObject.getString("name"));
                        vaccineModel.setVaccinationCenterAddress(sesObject.getString("address"));
                        vaccineModel.setVaccinationTimings(sesObject.getString("from"));
                        vaccineModel.setVaccineCenterTime(sesObject.getString("to"));
                        vaccineModel.setVaccinationName(sesObject.getString("vaccine"));
                        vaccineModel.setVaccinationCharges(sesObject.getString("fee_type"));
                        vaccineModel.setVaccinationAge(sesObject.getString("min_age_limit"));
                        vaccineModel.setVaccineAvailable(sesObject.getString("available_capacity"));
                        vaccine_centers.add(vaccineModel);


                    }
                    VaccineInfoAdapterClass vaccineInfoAdapter = new VaccineInfoAdapterClass(getApplicationContext(), vaccine_centers);
                    resultRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    resultRecyclerView.setAdapter(vaccineInfoAdapter);
                    holdOnProgress.setVisibility(View.INVISIBLE);

                } catch (Exception e) {
                    holdOnProgress.setVisibility(View.INVISIBLE);
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                holdOnProgress.setVisibility(View.INVISIBLE);
                Toast.makeText(Vaccination.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
}