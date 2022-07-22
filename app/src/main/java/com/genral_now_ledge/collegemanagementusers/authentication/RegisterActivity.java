package com.genral_now_ledge.collegemanagementusers.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.genral_now_ledge.collegemanagementusers.MainActivity;
import com.genral_now_ledge.collegemanagementusers.Models.registerModel;
import com.genral_now_ledge.collegemanagementusers.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    private String[] courses = {"Select Semester", "BCA I", "BCA II", "BCA III", "BCA IV", "BCA V", "BCA VI"};
    private String[] colleges = {"Select College", "GCMT Aligarh", "Dharam Samaj Aligarh", "AMU Aligarh"};
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
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
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


///////////////////////////////////////////////MAIN WORK START HERE//////////////////////////////////////////////////
        binding.userProfileRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        binding.registersumbitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.userNameEdit.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Name is empty", Toast.LENGTH_SHORT).show();
                } else if (binding.userEmailEdit.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Email is empty", Toast.LENGTH_SHORT).show();
                } else if (!binding.userEmailEdit.getText().toString().contains("@gmail.com")) {
                    Toast.makeText(RegisterActivity.this, "Email is wrong", Toast.LENGTH_SHORT).show();
                } else if (binding.userPasswordEdit.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Password is empty", Toast.LENGTH_SHORT).show();
                } else if (binding.userPasswordEdit.getText().toString().length() < 8) {
                    Toast.makeText(RegisterActivity.this, "Password more than 8 character", Toast.LENGTH_SHORT).show();
                } else if (binding.userConfirmPasswordEdit.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Confirm password is empty", Toast.LENGTH_SHORT).show();
                } else if (!binding.userPasswordEdit.getText().toString().equals(binding.userConfirmPasswordEdit.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Password no matches", Toast.LENGTH_SHORT).show();
                } else if (binding.userAdminKey.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Please enter unique key ", Toast.LENGTH_SHORT).show();
                } else if (selected_course.equals("Select Semester")) {
                    Toast.makeText(RegisterActivity.this, "Please select your course", Toast.LENGTH_SHORT).show();
                } else if (selected_college.equals("Select College")) {
                    Toast.makeText(RegisterActivity.this, "Please select your college", Toast.LENGTH_SHORT).show();
                } else if (IMAGE_URI == null) {
                    Toast.makeText(RegisterActivity.this, "Please select your Image", Toast.LENGTH_SHORT).show();
                    openGallery();
                } else {
                    progressDialogRegister.show();
                    auth.createUserWithEmailAndPassword(binding.userEmailEdit.getText().toString(), binding.userPasswordEdit.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialogRegister.dismiss();
                                progressDialog.show();
                                saveUserData();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialogRegister.dismiss();
                            Toast.makeText(RegisterActivity.this, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    private void saveUserData() {
        storageReference.child("User Data").child("profile").child(auth.getUid()).putFile(IMAGE_URI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> IMAGE_TASK = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                IMAGE_TASK.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        image_uri = uri.toString();
                        registerModel model = new registerModel(image_uri, binding.userNameEdit.getText().toString(), binding.userEmailEdit.getText().toString(), binding.userPasswordEdit.getText().toString(), selected_college, selected_course, binding.userAdminKey.getText().toString(), auth.getUid());
                        //get admin unique key
                        databaseReference.child(selected_course).child(selected_college).child(binding.userAdminKey.getText().toString()).child("All Students").child(auth.getUid()).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    sharedPreferences = getSharedPreferences("my_data", MODE_PRIVATE);
                                    editor = sharedPreferences.edit();
                                    editor.putString("_course", selected_course);
                                    editor.putString("_college", selected_college);
                                    editor.putString("key_admin",binding.userAdminKey.getText().toString());
                                    editor.apply();

                                    progressDialog.dismiss();
                                    Toast.makeText(RegisterActivity.this, "Account Created Success", Toast.LENGTH_SHORT).show();

                                   Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                                   intent.putExtra("admin_key",binding.userAdminKey.getText().toString());
//                                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                   startActivity(intent);

                                    finish();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Account Created Failed", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();

            }
        });
    }

    //////////onpen gallery/////////////
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE || resultCode == RESULT_OK) {
            IMAGE_URI = data.getData();
            binding.userProfileRegister.setImageURI(IMAGE_URI);
        }
    }

    public void GOTO_LOGIN(View view) {
        startActivity(new Intent(getApplicationContext(),loginActivity.class));
    }
    /////////////////close work //////////////////
}