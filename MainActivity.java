package com.example.publicohelpline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //for second activity
    private static int SPLASH_SCREEN = 5000;
    //variables for top and bottom animations
    Animation topAnim,bottomAnim;
    ImageView image;
    TextView logo,slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//to remove status and action bar in splash
        setContentView(R.layout.activity_main);
        //animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation_splash);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation_splash);

        //hooks
        image = findViewById(R.id.imgview);
        logo = findViewById(R.id.textView);


        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);

        }

    }
