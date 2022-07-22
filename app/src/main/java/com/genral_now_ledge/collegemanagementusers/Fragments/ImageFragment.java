package com.genral_now_ledge.collegemanagementusers.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.genral_now_ledge.collegemanagementusers.Adapters.imageAdapter;
import com.genral_now_ledge.collegemanagementusers.Models.ImageModel;
import com.genral_now_ledge.collegemanagementusers.databinding.FragmentImageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ImageFragment extends Fragment {

    private FragmentImageBinding binding;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    private SharedPreferences sharedPreferences;

    private ArrayList<ImageModel> januaryArrayList, augustArrayList, otherArrayList;
    private imageAdapter augustAdapter, januaryAdapter, otherAdapter;

    private Typeface typeface, typeface2, typeface3;
    private Context context;
    String collgename;
    String coursename;
    String adminKey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentImageBinding.inflate(inflater, container, false);
        context = getActivity().getApplicationContext();

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        sharedPreferences = context.getSharedPreferences("my_data", MODE_PRIVATE);
        collgename = sharedPreferences.getString("_college", "");
        coursename = sharedPreferences.getString("_course", "");
        adminKey = sharedPreferences.getString("key_admin", "");
        januaryArrayList = new ArrayList<>();

        binding.augustRel.setVisibility(View.GONE);
        binding.januaryRel.setVisibility(View.GONE);
        binding.otherRel.setVisibility(View.GONE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 26 january recyclerview
                binding.january26Recyclerview.setLayoutManager(new GridLayoutManager(context.getApplicationContext(), 3));
                binding.january26Recyclerview.setHasFixedSize(true);
                januaryArrayList = new ArrayList<>();
                get26January();
                januaryAdapter = new imageAdapter(context.getApplicationContext(), januaryArrayList);
                binding.january26Recyclerview.setAdapter(januaryAdapter);

            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                binding.august15Recyclerview.setLayoutManager(new GridLayoutManager(context.getApplicationContext(), 3));
                binding.august15Recyclerview.setHasFixedSize(true);
                augustArrayList = new ArrayList<>();
                get15August();
                augustAdapter = new imageAdapter(context.getApplicationContext(), augustArrayList);
                binding.august15Recyclerview.setAdapter(augustAdapter);

            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                binding.otherRecyclerview.setLayoutManager(new GridLayoutManager(context.getApplicationContext(), 3));
                binding.otherRecyclerview.setHasFixedSize(true);
                otherArrayList = new ArrayList<>();
                getOther();
                otherAdapter = new imageAdapter(context.getApplicationContext(), otherArrayList);
                binding.otherRecyclerview.setAdapter(otherAdapter);
            }
        }).start();

        return binding.getRoot();
    }

    private void get26January() {
        databaseReference.child(coursename).child(collgename).child(adminKey).child("Images").child("26 January").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.januaryRel.setVisibility(View.VISIBLE);
                    januaryArrayList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        ImageModel model = dataSnapshot.getValue(ImageModel.class);
                        januaryArrayList.add(model);
                    }
                    januaryAdapter.notifyDataSetChanged();
                } else {
                    binding.januaryRel.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void get15August() {
        databaseReference.child(coursename).child(collgename).child(adminKey).child("Images").child("15 August").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding. augustRel.setVisibility(View.VISIBLE);
                    augustArrayList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        ImageModel model = dataSnapshot.getValue(ImageModel.class);
                        augustArrayList.add(model);
                    }
                } else {
                    binding.augustRel.setVisibility(View.GONE);
                }
                augustAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getOther() {
        databaseReference.child(coursename).child(collgename).child(adminKey).child("Images").child("Other").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    binding.otherRel.setVisibility(View.VISIBLE);
                    otherArrayList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        ImageModel model = dataSnapshot.getValue(ImageModel.class);
                        otherArrayList.add(model);
                    }
                } else {
                    binding.otherRel.setVisibility(View.GONE);
                }
                otherAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}