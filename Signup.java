package com.example.publicohelpline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Signup extends AppCompatActivity {
    Button callLogin;//button -on clicking login ,will go to login
    AutoCompleteTextView autoView;//for dropdown menu

    TextInputEditText regName, regPassword, regEmail, regPhoneNo;
    Button registerButton;//on clicking register button


    FirebaseDatabase rootNode;
    DatabaseReference reference;//to refer elements of rootNode


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//to remove status and action bar in sign up
        setContentView(R.layout.activity_signup);

        //hooks to all xml elements in activity_sign_up.xml
        regName = (TextInputEditText) findViewById(R.id.fullname);
        regEmail = (TextInputEditText) findViewById(R.id.email);
        regPassword = (TextInputEditText) findViewById(R.id.pwd);
        regPhoneNo = (TextInputEditText) findViewById(R.id.phoneno);
        registerButton = (Button) findViewById(R.id.regbtnid);
        autoView = (AutoCompleteTextView) findViewById(R.id.menu);
        String[] option = {"Basavanagudi", "Bommanahalli", "RRNagar", "Yeshwanthpur", "Mahadevapura", "Vijaynagar"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.dropdownmenuconstituency, option);
        autoView.setText(arrayAdapter.getItem(0).toString(), false);//to make default value

        autoView.setAdapter(arrayAdapter);

        callLogin = findViewById(R.id.btn5);
        callLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slideinleft, R.anim.slideoutright);

            }
        });

        //on clicking button register -store information in firebase
//        registerButton.setOnClickListener(new View.OnClickListener() {
//
//            @Override
    }

    //for validating username
    private Boolean validateUsername() {
        String val = regName.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        String noNumber = "[0-9]";

        if (val.isEmpty()) {
            regName.setError("Must contain 1 to 15 letters");
            return false;
        } else if (val.length() >= 15) {
            regName.setError("Username too long");
            return false;
        }else if (val.matches(noNumber)) {
            regName.setError("Digits not allowed");
            return false;
        }
        else if (!val.matches(noWhiteSpace)) {
            regName.setError("White Spaces are not allowed");
            return false;
        } else {
            regName.setError(null);
            return true;
        }
    }

    //validating user email
    private Boolean validateEmail() {
        String val = regEmail.getText().toString();
        String emailPattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$";
        if (val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address");
            return false;
        } else {
            regEmail.setError(null);
            return true;
        }
    }

    //validating user phone number
    private Boolean validatePhoneNo() {
        String val = regPhoneNo.getText().toString();

        if (val.isEmpty()) {
            regPhoneNo.setError("Field cannot be empty");
            return false;
        } else if (val.length() != 10) {
            regPhoneNo.setError("Invalid Phone number");
            return false;
        } else {
            regPhoneNo.setError(null);
            return true;
        }
    }

    //validating user password
    private Boolean validatePassword() {
        String val = regPassword.getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            regPassword.setError("Password is too weak");
            return false;
        } else {
            regPassword.setError(null);
            return true;
        }
    }


    //This function will execute when user click on Register Button
    public void register_user(View v) throws InterruptedException {


//        Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
        if (!validateUsername() | !validatePassword() | !validatePhoneNo() | !validateEmail() | !validateUsername()) {
            return;
        }


        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users");

        //get all values of register page
        String username = regName.getText().toString();
        String email = regEmail.getText().toString();
        String password = regPassword.getText().toString();
        String phone = regPhoneNo.getText().toString();
        String constituency = autoView.getText().toString();
        String userType = "public";
        UserHelperClass helperClass = new UserHelperClass(username, email, password, phone, constituency, userType);
        reference.child(username).setValue(helperClass);
        new SweetAlertDialog(this).setTitleText("Successfully Registered").show();

        //after saving to database redirects to login page
        Intent intent = new Intent(Signup.this, Login.class);
        startActivity(intent);
    }
}
