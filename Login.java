package com.example.publicohelpline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.publicohelpline.Databases.SessionManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    Button callSignUp;
    Button callLogin;
    TextInputEditText regName, regPassword;
    CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //to remove status and action bar in login
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        regName = (TextInputEditText) findViewById(R.id.name);
        regPassword = (TextInputEditText) findViewById(R.id.pwd);
        rememberMe = findViewById(R.id.checkBox);

        callSignUp = findViewById(R.id.btn3);
        callSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);
            }
        });

        //checking whether name and password already present in shared preferences or not
        SessionManager session_manager = new SessionManager(Login.this,SessionManager.SESSION_REMEMBER_ME_SESSION);
        if(session_manager.check_remember_me()){

            HashMap<String,String> rememberMeDetails = session_manager.getRememberMeFromSession();
            regName.setText(rememberMeDetails.get(SessionManager.KEY_SESSION_FULL_NAME));
            regPassword.setText(rememberMeDetails.get(SessionManager.KEY_SESSION_PASSWORD));
        }

    }
    //validating user email

    private Boolean validateUsername() {
        String val = regName.getText().toString();
        if (val.isEmpty()) {
            regName.setError("Field cannot be empty");
            return false;
        } else {
            regName.setError(null);
            return true;
        }
    }

    //validating password
    private Boolean validatePassword() {
        String val = regPassword.getText().toString();
        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else {
            regPassword.setError(null);
            return true;
        }
    }

    //this function will execute once the user press login button
    public void login_user(View view) throws InterruptedException {
        //to check internet connection
        if (!isConnected(this)) {
            showCustomDialog(view);
        }
        if (!validateUsername() | !validatePassword()) {
                return;
            }
            isUser();



            }


//
//            isAdmin();
//        new SweetAlertDialog(this).setConfirmText("Successfully logged in").show();
//            if(!isAdmin()) {
//                Intent intent = new Intent(Login.this, Dashboardpublic.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//            }




    private void showCustomDialog(View view) {
        AlertDialog.Builder builder = new  AlertDialog.Builder(Login.this);//user cant cancel the popup
        builder.setMessage("Please connect to the data or wifi to continue further").setCancelable(false).setPositiveButton("Connect", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));//redirect to settings
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //to check internet connection
    private Boolean isConnected(Login login) {
        ConnectivityManager connectivity_manager = (ConnectivityManager) login.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo wifi_connection = connectivity_manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo data_connection = connectivity_manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi_connection != null && wifi_connection.isConnected()) || (data_connection != null && data_connection.isConnected()))
            return true;
        else {
            return false;
//       boolean str = wifi_connection != null && wifi_connection.isConnected() || data_connection != null && data_connection.isConnected();
//        Log.d("wifi","result"+str);

        }
    }



    // to check whether user is public
    public void isUser(){
        String userEnteredName = regName.getText().toString().trim();
        String userEnteredPassword = regPassword.getText().toString().trim();
        if(rememberMe.isChecked()){
            SessionManager session_manager = new SessionManager(Login.this,SessionManager.SESSION_REMEMBER_ME_SESSION);
            session_manager.create_remember_me_session(userEnteredName,userEnteredPassword);
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        Query checkUser = reference.orderByChild("username").equalTo(userEnteredName);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String passwordFromDatabase = snapshot.child(userEnteredName).child("password").getValue(String.class);
                    String userTypeFromDatabase = snapshot.child(userEnteredName).child("user_type").getValue(String.class);
                    if (passwordFromDatabase.equals(userEnteredPassword)) {

                        if (userTypeFromDatabase.equals("admin")) {
                            new SweetAlertDialog(Login.this).setConfirmText("Successfully logged in").show();
                            Intent intent = new Intent(Login.this, Dashboardadmin.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        }
                        else if(userTypeFromDatabase.equals("public")){
                            new SweetAlertDialog(Login.this).setConfirmText("Successfully logged in").show();
                            Intent intent = new Intent(Login.this, Dashboardpublic.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                        }


                        //get all user data from firebase
                        String userName = snapshot.child(userEnteredName).child("username").getValue(String.class);
                        String userEmail = snapshot.child(userEnteredName).child("email").getValue(String.class);
                        String userPassword = snapshot.child(userEnteredName).child("password").getValue(String.class);
                        String userPhone = snapshot.child(userEnteredName).child("phone").getValue(String.class);
                        String userConstituency = snapshot.child(userEnteredName).child("constituency").getValue(String.class);
                        String userType = snapshot.child(userEnteredName).child("user_type").getValue(String.class);


                        //create session
                        SessionManager session_manager = new SessionManager(Login.this,SessionManager.SESSION_USER_SESSION);
                        session_manager.create_login_session(userName,userEmail,userPassword,userPhone,userConstituency,userType);
                    }
                    else {
                        regPassword.setError("wrong password");
                    }
                } else {
                    regName.setError("No such user Exists");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


//    //to check whether user is admin
//    public boolean isAdmin() {
//        String userEnteredName = regName.getText().toString().trim();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
//        Query checkUser = reference.orderByChild("username").equalTo(userEnteredName);
//        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    String userTypeFromDatabase = snapshot.child(userEnteredName).child("user_type").getValue(String.class);
//                    if (userTypeFromDatabase.equals("admin")) {
//                        Intent intent = new Intent(Login.this, Dashboardadmin.class);
//                        startActivity(intent);
//                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
//                        finish();
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        return false;
//    }
}


//<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
//<uses-permission android:name="android.permission.INTERNET"/>