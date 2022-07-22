package com.genral_now_ledge.collegemanagementusers.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.denzcoskun.imageslider.models.SlideModel;
import com.genral_now_ledge.collegemanagementusers.Adapters.courseAdapter;
import com.genral_now_ledge.collegemanagementusers.Adapters.ebookAdapter;
import com.genral_now_ledge.collegemanagementusers.Models.courseModel;
import com.genral_now_ledge.collegemanagementusers.Models.ebookModel;
import com.genral_now_ledge.collegemanagementusers.Models.noticeModel;
import com.genral_now_ledge.collegemanagementusers.R;
import com.genral_now_ledge.collegemanagementusers.databinding.FragmentEbookBinding;
import com.genral_now_ledge.collegemanagementusers.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ebookFragment extends Fragment {
    private FragmentEbookBinding binding;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private SharedPreferences sharedPreferences;
    private ArrayList<ebookModel> arrayListCourse;
    private ebookAdapter adapter;
    private Typeface typeface,typeface2,typeface3;
    private Context context;

    String collgename;
    String coursename;
    String adminKey;
    String mapAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
        binding = FragmentEbookBinding.inflate(inflater, container, false);
        context = getActivity().getApplicationContext();

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        sharedPreferences = context.getSharedPreferences("my_data", MODE_PRIVATE);
        collgename = sharedPreferences.getString("_college", "");
        coursename = sharedPreferences.getString("_course", "");
        adminKey = sharedPreferences.getString("key_admin", "");



        ///////////////////////////////////////set local data//////////////////////////////

        //recyclerview
        binding.ebookRecyclerview.setLayoutManager(new LinearLayoutManager(context.getApplicationContext()));
        binding.ebookRecyclerview.setHasFixedSize(true);
        arrayListCourse = new ArrayList<>();
        getOureEbook();
        adapter = new ebookAdapter(context.getApplicationContext(),arrayListCourse);
        binding.ebookRecyclerview.setAdapter(adapter);
        //recyclerview

        return binding.getRoot();
    }

    private void getOureEbook() {
        databaseReference.child(coursename).child(collgename).child(adminKey).child("pdf").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListCourse.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ebookModel model = dataSnapshot.getValue(ebookModel.class);
                    arrayListCourse.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}