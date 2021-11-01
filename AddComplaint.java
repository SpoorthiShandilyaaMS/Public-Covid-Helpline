package com.example.publicohelpline;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.publicohelpline.Databases.SessionManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static java.text.DateFormat.getDateTimeInstance;

public class AddComplaint extends AppCompatActivity {
    Calendar calender;
    SimpleDateFormat dateFormat;
    String date;
    TextInputEditText complaintTitle, complaintDesc;
    Button submitButton, chooseFileButton;
    AutoCompleteTextView autoView;//for dropdown menu

    public Uri img_uri;
    private  Uri resultUri;
    String new_uri;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    StorageReference storage_ref;//to refer elements of rootNode

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaint);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//to remove status and action bar in add complaint
        complaintTitle = (TextInputEditText) findViewById(R.id.complainttitle);
        complaintDesc = (TextInputEditText) findViewById(R.id.complaintdesc);
        chooseFileButton = (Button) findViewById(R.id.complaintchooseimage);
        submitButton = (Button) findViewById(R.id.complaintsubmitbtn);
        autoView = (AutoCompleteTextView) findViewById(R.id.complaintmenu);
        String[] option = {"Health", "Power", "Water", "Security", "Transportation", "Infrastructure","Others"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.dropdownmenusectors, option);
        autoView.setText(arrayAdapter.getItem(0).toString(), false);//to make default value
        autoView.setAdapter(arrayAdapter);

        //executed when upload button is clicked
        chooseFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file_chooser();

            }
        });
        

    }

    private void file_chooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    1);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(AddComplaint.this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private String getExtension(Uri uri){
        ContentResolver content_resolver = getContentResolver();
        MimeTypeMap mime_type_map = MimeTypeMap.getSingleton();
        return mime_type_map.getExtensionFromMimeType(content_resolver.getType(uri));
    }

    private Boolean validateComplaintTitle() {
        String val = complaintTitle.getText().toString();

        if (val.isEmpty()) {
            complaintTitle.setError("Please Enter proper Feeds!!");
            return false;
        } else if (val.length() > 100) {
            complaintTitle.setError("Title Too Long!!");
            return false;
        } else {
            complaintTitle.setError(null);
            return true;
        }
    }

    private Boolean validateComplaintDesc() {
        String val = complaintDesc.getText().toString();

        if (val.isEmpty()) {
            complaintDesc.setError("Please Enter proper Feeds!!");
            return false;
        } else if (val.length() > 600) {
            complaintDesc.setError("Description Too Long!!");
            return false;
        } else {
            complaintDesc.setError(null);
            return true;
        }
    }

    private Boolean validateComplaintFile() {
        String val = chooseFileButton.getText().toString();

        if (val.isEmpty()) {
            chooseFileButton.setError("File not selected");
            return false;
        } else {
            chooseFileButton.setError(null);
            return true;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            img_uri =  data.getData();
            // start cropping activity for pre-acquired image saved on the device
            CropImage.activity(img_uri)
                    .start(this);
//            Toast.makeText(AddNews.this, "File selected",
//                    Toast.LENGTH_SHORT).show();


//            img.setImageURI(img_uri);//to set an image to image field

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                Toast toast = Toast.makeText(AddComplaint.this, "File selected", Toast.LENGTH_LONG);
                View view = toast.getView();

                //To change the Background of Toast
                view.setBackgroundColor(Color.TRANSPARENT);
                TextView text = (TextView) view.findViewById(android.R.id.message);

                //Shadow of the Of the Text Color
                text.setShadowLayer(0, 0, 0, Color.TRANSPARENT);
                text.setTextSize(20);
                text.setTextColor(Color.BLACK);
                toast.show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }




    //executed when admin clicks on submit news button
    public void add_complaint(View v) throws InterruptedException {

        if (!validateComplaintTitle() | !validateComplaintDesc() | !validateComplaintFile() )  {
            return;
        }

        try {
        storage_ref = FirebaseStorage.getInstance().getReference("Complaintimages");
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Complaints");
        //get all values of complaint page
        String comp_title =complaintTitle.getText().toString();
        String comp_desc = complaintDesc.getText().toString();
        String sector = autoView.getText().toString();




        //taking constituency name from session and taking username AND EMAIL from session
        SessionManager sessionManager = new SessionManager(this,SessionManager.SESSION_USER_SESSION);
        HashMap<String,String> userData = sessionManager.getUserDataFromSession();
        String userConstituency = userData.get(SessionManager.KEY_CONSTITUENCY);
        String username = userData.get(SessionManager.KEY_FULL_NAME);
        String userEmail = userData.get(SessionManager.KEY_EMAIL);

            calender = Calendar.getInstance();
            dateFormat = new SimpleDateFormat("dd-MM-yyyyy HH:mm:ss");
            date = dateFormat.format(calender.getTime());


            //image
            String image_uri;

            image_uri = System.currentTimeMillis() + "." + getExtension(img_uri);
            if(img_uri != null) {


                StorageReference ref = storage_ref.child(image_uri);
                ref.putFile(resultUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String new_uri =  uri.toString();
//
                                        ComplaintHelperClass complainthelperClass = new ComplaintHelperClass(comp_title,comp_desc,sector,username,userEmail,userConstituency,new_uri,date);
                                        reference.child(comp_title).setValue(complainthelperClass);
//
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddComplaint.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

                new SweetAlertDialog(this).setTitleText("Complaint registered Successfully").show();

                complaintTitle.setText(null);
                complaintDesc.setText(null);
            }
            else{
                Toast.makeText(AddComplaint.this, "No file selected", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        }

    }
