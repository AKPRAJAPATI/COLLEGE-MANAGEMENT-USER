package com.genral_now_ledge.collegemanagementusers.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.genral_now_ledge.collegemanagementusers.Models.contectModel;
import com.genral_now_ledge.collegemanagementusers.Models.courseModel;
import com.genral_now_ledge.collegemanagementusers.R;
import com.genral_now_ledge.collegemanagementusers.Adapters.courseAdapter;
import com.genral_now_ledge.collegemanagementusers.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private SharedPreferences sharedPreferences;
    private ArrayList<courseModel> arrayListCourse;
    private courseAdapter adapter;
    private Typeface typeface,typeface2,typeface3,typeface4;
    private Context context;
    private List<SlideModel> arrayList,arrayListTeacher;

    private ShimmerFrameLayout shimmerFrameLayout;
    String collgename;
    String coursename;
    String adminKey;
    String mapAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        context = getActivity().getApplicationContext();
         //simmer start

        binding.shimmerLayoutImageSlider.startShimmer();

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        sharedPreferences = context.getSharedPreferences("my_data", MODE_PRIVATE);
        collgename = sharedPreferences.getString("_college", "");
        coursename = sharedPreferences.getString("_course", "");
        adminKey = sharedPreferences.getString("key_admin", "");

        ////////////////////////////SET FONTS //////////////////////////////
          typeface = ResourcesCompat.getFont(context.getApplicationContext(), R.font.sketch3d);
          typeface2 = ResourcesCompat.getFont(context.getApplicationContext(), R.font.mst);
          typeface3 = ResourcesCompat.getFont(context.getApplicationContext(), R.font.easy_3d);
          typeface4 = ResourcesCompat.getFont(context.getApplicationContext(), R.font.hand_writing);

        binding.collegeName.setTypeface(typeface);
        binding.textView.setTypeface(typeface);
        binding.collegeDetailTextView.setTypeface(typeface2);
        binding.CONTECTINFO.setTypeface(typeface3);
        binding.bottomCollegeName.setTypeface(typeface4);


        ///////////////////////////////////////set local data//////////////////////////////

        binding.bottomCollegeName.setSelected(true);
        Typeface typeface  = ResourcesCompat.getFont(getContext(),R.font.hand_writing);
        binding.admissionOpen.setTypeface(typeface);
        ///////////////////////////////////////set local data//////////////////////////////
        arrayList = new ArrayList<>();
        arrayListTeacher = new ArrayList<>();

        //recyclerview
        binding.courseRecyclerview.setLayoutManager(new GridLayoutManager(context.getApplicationContext() , 2));
        binding.courseRecyclerview.setHasFixedSize(true);
        arrayListCourse = new ArrayList<>();
        getOurCourse();
       adapter = new courseAdapter(context.getApplicationContext(),arrayListCourse);
       binding.courseRecyclerview.setAdapter(adapter);
        //recyclerview


        //get Top Slider
        getTopSlider();
        getCollegeAbout();
        getBottomSlidersTeachers();
        getCollegeAddress();
        getMapImages();
        //end all the methods
        binding.mapImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mapAddress == null)
                {
                    Toast.makeText(context, "Wait for load data", Toast.LENGTH_SHORT).show();
                }else{
                    openMap();
                }
            }
        });

        return binding.getRoot();
    }

    private void openMap() {
        Uri uri = Uri.parse("geo:0, 0?q="+mapAddress);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }

    private void getOurCourse() {
        databaseReference.child(coursename).child(collgename).child(adminKey).child("Course List").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListCourse.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    courseModel model = dataSnapshot.getValue(courseModel.class);
                    arrayListCourse.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getMapImages() {
        databaseReference.child(coursename).child(collgename).child(adminKey).child("map").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String mapImage,link;
                    mapImage = snapshot.child("mapImage").getValue(String.class);
                    link = snapshot.child("link").getValue(String.class);
                    mapAddress = link;
                    Picasso.get().load(mapImage).into(binding.mapImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getCollegeAddress()  {
        databaseReference.child(coursename).child(collgename).child(adminKey).child("Contect Info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              if (snapshot.exists()){
                  String full_address , state , pincode , email , phone;
                  full_address = snapshot.child("address").getValue(String.class);
                  state = snapshot.child("state").getValue(String.class);
                  pincode = snapshot.child("pincode").getValue(String.class);
                  email = snapshot.child("gmail").getValue(String.class);
                  phone = snapshot.child("phone").getValue(String.class);

                  binding.collegeAddress.setText(full_address);
                  binding.state.setText(state);
                  binding.pincode.setText(pincode);
                  binding.email.setText(email);
                  binding.phoneNumber.setText(phone);

                  binding.bottomCollegeName.setText(full_address);
                     binding.bottmPincode.setText(pincode);
                     binding.bottomContect.setText(phone);
                     binding.bottomGmail.setText(email);
                     binding.bottomState.setText(state);

              }
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    contectModel contModel = dataSnapshot.getValue(contectModel.class);
//                     binding.collegeAddress.setText(contModel.getAddress());
//                     binding.state.setText(contModel.getState());
//                     binding.pincode.setText(contModel.getPincode());
//                     binding.email.setText(contModel.getGmail());
//                     binding.phoneNumber.setText(contModel.getPhone());
//
//                     binding.bottomCollegeName.setText(contModel.getAddress());
//                     binding.bottmPincode.setText(contModel.getPincode());
//                     binding.bottomContect.setText(contModel.getPhone());
//                     binding.bottomGmail.setText(contModel.getGmail());
//                     binding.bottomState.setText(contModel.getState());
//
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getBottomSlidersTeachers() {
        databaseReference.child(coursename).child(collgename).child(adminKey).child("Teacher Slider Image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListTeacher.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String image_data = dataSnapshot.child("slideImage").getValue(String.class);
                    String image_dataText = dataSnapshot.child("slideTitle").getValue(String.class);
                    arrayListTeacher.add(new SlideModel(image_data, image_dataText , ScaleTypes.CENTER_CROP));
                    binding.imageSliderTeachers.setImageList(arrayListTeacher);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getCollegeAbout() {
        databaseReference.child(coursename).child(collgename).child(adminKey).child("College About").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    String about = snapshot.child("about").getValue(String.class);
                    binding.collegeDetailTextView.setText(about);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getTopSlider() {
        databaseReference.child(coursename).child(collgename).child(adminKey).child("Slider Image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String image_data = dataSnapshot.child("slideImage").getValue(String.class);
                    arrayList.add(new SlideModel(image_data , ScaleTypes.CENTER_CROP));
                    binding.imageSlider.setImageList(arrayList);

                    binding.shimmerLayoutImageSlider.stopShimmer();
                    binding.collegeName.setText(collgename);
                    binding.shimmerLayoutImageSlider.setVisibility(View.GONE);
                    binding.myScrollview.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}