package com.example.publicohelpline;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;

public class Helpline extends AppCompatActivity {
    private static final int REQUEST_CALL = 1;
    private TextView txt1,txt2,txt3,txt4,txt5;
    private Button btn1,btn2,btn3,btn4,btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpline);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btn1 = findViewById(R.id.helpline1btn);
        btn2 = findViewById(R.id.helpline2btn);
        btn3 = findViewById(R.id.helpline3btn);
        btn4 = findViewById(R.id.helpline4btn);
        btn5 = findViewById(R.id.helpline5btn);
        txt1 = findViewById(R.id.helplinenum1);
        txt2 = findViewById(R.id.helplinenum2);
        txt3 = findViewById(R.id.helplinenum3);
        txt4 = findViewById(R.id.helplinenum4);
        txt5 = findViewById(R.id.helplinenum5);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callButton1();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callButton2();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callButton3();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callButton4();
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callButton5();
            }
        });
    }
    private void callButton1(){
        String number = txt1.getText().toString();
        if(number.trim().length()>0){
            if(ContextCompat.checkSelfPermission(Helpline.this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(Helpline.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }else{
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }
    private void callButton2(){
        String number = txt2.getText().toString();
        if(number.trim().length()>0){
            if(ContextCompat.checkSelfPermission(Helpline.this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(Helpline.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }else{
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }
    private void callButton3(){
        String number = txt3.getText().toString();
        if(number.trim().length()>0){
            if(ContextCompat.checkSelfPermission(Helpline.this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(Helpline.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }else{
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }
    private void callButton4(){
        String number = txt4.getText().toString();
        if(number.trim().length()>0){
            if(ContextCompat.checkSelfPermission(Helpline.this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(Helpline.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }else{
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }
    private void callButton5(){
        String number = txt5.getText().toString();
        if(number.trim().length()>0){
            if(ContextCompat.checkSelfPermission(Helpline.this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(Helpline.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }else{
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callButton1();
                callButton2();
                callButton3();
                callButton4();
                callButton5();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
