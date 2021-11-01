package com.example.publicohelpline;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.publicohelpline.Databases.SessionManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddNews extends AppCompatActivity {
    Calendar calender;
    SimpleDateFormat dateFormat;
    String date;
    TextInputEditText newsTitle, newsDesc;
    Button submitButton, chooseFileButton;
    public Uri img_uri;
   private  Uri resultUri;
    String new_uri;


    FirebaseDatabase rootNode;
    DatabaseReference reference;
    StorageReference storage_ref;//to refer elements of rootNode


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

//        storage_ref = FirebaseStorage.getInstance().getReference("Newsimages");
//        reference = FirebaseDatabase.getInstance().getReference("NewsFeed");


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//to remove status and action bar in add news
        newsTitle = (TextInputEditText) findViewById(R.id.addnewstitle);
        newsDesc = (TextInputEditText) findViewById(R.id.addnewsdesc);
        chooseFileButton = (Button) findViewById(R.id.btnchooseimage);
        submitButton = (Button) findViewById(R.id.submitbtn);


        chooseFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file_chooser();

            }
        });

    }

    private Boolean validateNewsTitle() {
        String val = newsTitle.getText().toString();

        if (val.isEmpty()) {
            newsTitle.setError("Please Enter proper Feeds!!");
            return false;
        } else if (val.length() > 100) {
            newsTitle.setError("Title Too Long!!");
            return false;
        } else {
            newsTitle.setError(null);
            return true;
        }
    }

    private Boolean validateNewsDesc() {
        String val = newsDesc.getText().toString();

        if (val.isEmpty()) {
            newsDesc.setError("Please Enter proper Feeds!!");
            return false;
        } else if (val.length() > 600) {
            newsDesc.setError("Description Too Long!!");
            return false;
        } else {
            newsDesc.setError(null);
            return true;
        }
    }

        private Boolean validateNewsFile() {
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
                Toast toast = Toast.makeText(AddNews.this, "File selected", Toast.LENGTH_LONG);
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


    //executed when admin clicks on upload file button
//    public void file_chooser(){//first one
//        Intent intent = new Intent();
//        intent.setType("image/");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent,1);
//
//    }
    private void file_chooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    1);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(AddNews.this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }

    }


    private String getExtension(Uri uri){
        ContentResolver content_resolver = getContentResolver();
        MimeTypeMap mime_type_map = MimeTypeMap.getSingleton();
        return mime_type_map.getExtensionFromMimeType(content_resolver.getType(uri));
    }



    //executed when admin clicks on submit news button
    public void add_news(View v) throws InterruptedException {
        final String str;
        String url = null;
        if (!validateNewsTitle() | !validateNewsDesc() | !validateNewsFile())  {
            return;
        }

        try {
            storage_ref = FirebaseStorage.getInstance().getReference("Newsimages");
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("NewsFeed");
            //get all values of register page
            String news_title = newsTitle.getText().toString();
            String news_desc = newsDesc.getText().toString();

            //taking constituency name from session
            SessionManager sessionManager = new SessionManager(this,SessionManager.SESSION_USER_SESSION);
            HashMap<String,String> userData = sessionManager.getUserDataFromSession();
            String adminConstituency = userData.get(SessionManager.KEY_CONSTITUENCY);

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
//                                      NewsHelperClass new_helper = new NewsHelperClass(new_uri);
                                      NewsHelperClass newshelperClass = new NewsHelperClass(news_title,news_desc,adminConstituency,new_uri,date);
                                      reference.child(news_title).setValue(newshelperClass);
//                                      reference.child(news_title).setValue(new_helper);

                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddNews.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });




                new SweetAlertDialog(this).setTitleText("Successfully Added News").show();

                newsTitle.setText(null);
                newsDesc.setText(null);
            }
            else{
                Toast.makeText(AddNews.this, "No file selected", Toast.LENGTH_SHORT).show();
            }

           //here


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}