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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genral_now_ledge.collegemanagementusers.Adapters.teacherAdapter;
import com.genral_now_ledge.collegemanagementusers.Models.teacherModel;
import com.genral_now_ledge.collegemanagementusers.databinding.FragmentTeacherBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class TeacherFragment extends Fragment {

    private FragmentTeacherBinding binding;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    private SharedPreferences sharedPreferences;

    private ArrayList<teacherModel> array_teacherList;
    private teacherAdapter adapter;

    private Typeface typeface, typeface2, typeface3;

    private Context context;
    String collgename;
    String coursename;
    String adminKey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_teacher, container, false);
        binding = FragmentTeacherBinding.inflate(inflater, container, false);
        context = getActivity().getApplicationContext();

        binding.shimmerLayout.startShimmer();
        binding.shimmerLayout2.startShimmer();
        binding.shimmerLayout3.startShimmer();
        binding.shimmerLayout4.startShimmer();
        binding.shimmerLayout5.startShimmer();

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        sharedPreferences = context.getSharedPreferences("my_data", MODE_PRIVATE);
        collgename = sharedPreferences.getString("_college", "");
        coursename = sharedPreferences.getString("_course", "");
        adminKey = sharedPreferences.getString("key_admin", "");
        array_teacherList = new ArrayList<>();


        //recyclerview
        binding.teacherRecyclerview.setLayoutManager(new LinearLayoutManager(context.getApplicationContext()));
        binding.teacherRecyclerview.setHasFixedSize(true);
        array_teacherList = new ArrayList<>();
        getOurTeacherData();
        adapter = new teacherAdapter(context.getApplicationContext(), array_teacherList);
        binding.teacherRecyclerview.setAdapter(adapter);


        return binding.getRoot();
    }

    private void getOurTeacherData() {
        databaseReference.child(coursename).child(collgename).child(adminKey).child("Teachers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                array_teacherList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    teacherModel model = dataSnapshot.getValue(teacherModel.class);
                    array_teacherList.add(model);
                }

binding.simmerLinear.setVisibility(View.GONE);
                binding.shimmerLayout.setVisibility(View.GONE);
                binding.shimmerLayout2.setVisibility(View.GONE);
                binding.shimmerLayout3.setVisibility(View.GONE);
                binding.shimmerLayout4.setVisibility(View.GONE);
                binding.shimmerLayout5.setVisibility(View.GONE);

                binding.teacherRecyclerview.setVisibility(View.VISIBLE);


                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}