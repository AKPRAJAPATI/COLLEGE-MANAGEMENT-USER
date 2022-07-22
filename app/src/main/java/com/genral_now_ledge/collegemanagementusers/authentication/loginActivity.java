package com.genral_now_ledge.collegemanagementusers.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.genral_now_ledge.collegemanagementusers.R;
import com.genral_now_ledge.collegemanagementusers.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class loginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private String[] courses = {"Select Semester", "BCA I", "BCA II", "BCA III", "BCA IV", "BCA V", "BCA VI"};
//    private String[] colleges = {"Select College", "GCMT Aligarh", "Dharam Samaj Aligarh", "AMU Aligarh"};

    private String[] colleges = {
            "Select College"
            ,"GCMT Aligarh"
            ,"DS Degree College, Aligarh", "Shri Varshney College, Aligarh"
            ,"Aligarh College of Education, Aligarh"
            ,"Gyan Mahavidyalaya, Aligarh"
            ,"Institute of Information Management and Technology, Aligarh"
            ,"PM College of Education, Aligarh"
            ,"SSITM Aligarh - Shivdan Singh Institute of Technology and Management"
            ,"Amrita Singh Memorial Degree College, Aligarh"
            ,"Gramodhar Mahavidyalaya, Aligarh"
            ,"National P.G. College Lucknow - National Post Graduate College"
            ,"Raja Balwant Singh College, Agra"
    };
    private String selected_course;
    private String selected_college;

    /////////// FIREBASE /////////////////
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    /////////// LOCAL VARIABLE //////////
    private Uri IMAGE_URI;
    private String image_uri;
    private int GALLERY_REQUEST_CODE = 100;
    private ProgressDialog progressDialog, progressDialogRegister;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialogRegister = new ProgressDialog(this);
        progressDialogRegister.setCancelable(false);
        progressDialogRegister.setMessage("Register Account");

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Saving Data");

        ///////////////GET INSTANCE OF OUR DATABASE///////////////
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        //This is work area for courses
        binding.coursesspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_course = binding.coursesspinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
        binding.coursesspinner.setAdapter(ad);

        //This is work area for colleges
        binding.courseCollegespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_college = binding.courseCollegespinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter collegeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, colleges);
        binding.courseCollegespinner.setAdapter(collegeAdapter);

        binding.loginsumbitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.userEmailEdit.getText().toString().isEmpty()) {
                    Toast.makeText(loginActivity.this, "Email is empty", Toast.LENGTH_SHORT).show();
                } else if (!binding.userEmailEdit.getText().toString().contains("@gmail.com")) {
                    Toast.makeText(loginActivity.this, "Email is wrong", Toast.LENGTH_SHORT).show();
                } else if (binding.userPasswordEdit.getText().toString().isEmpty()) {
                    Toast.makeText(loginActivity.this, "Password is empty", Toast.LENGTH_SHORT).show();
                } else if (binding.userPasswordEdit.getText().toString().length() < 8) {
                    Toast.makeText(loginActivity.this, "Password more than 8 character", Toast.LENGTH_SHORT).show();
                }else if (selected_course.equals("Select Semester")) {
                    Toast.makeText(loginActivity.this, "Please select your course", Toast.LENGTH_SHORT).show();
                } else if (selected_college.equals("Select College")) {
                    Toast.makeText(loginActivity.this, "Please select your college", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialogRegister.show();
                    auth.signInWithEmailAndPassword(binding.userEmailEdit.getText().toString(), binding.userPasswordEdit.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialogRegister.dismiss();
                                 Intent intent = new Intent(getApplicationContext(),loginActivity.class);
                                 startActivity(intent);
                                 finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialogRegister.dismiss();
                            Toast.makeText(loginActivity.this, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void GOTO_REGISTER(View view) {
        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
    }
}