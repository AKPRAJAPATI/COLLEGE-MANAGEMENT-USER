package com.genral_now_ledge.collegemanagementusers.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.genral_now_ledge.collegemanagementusers.Adapters.imageAdapter;
import com.genral_now_ledge.collegemanagementusers.Adapters.noticeAdapter;
import com.genral_now_ledge.collegemanagementusers.Models.ImageModel;
import com.genral_now_ledge.collegemanagementusers.Models.noticeModel;
import com.genral_now_ledge.collegemanagementusers.R;
import com.genral_now_ledge.collegemanagementusers.databinding.FragmentImageBinding;
import com.genral_now_ledge.collegemanagementusers.databinding.FragmentNoticeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class NoticeFragment extends Fragment {

    private FragmentNoticeBinding binding;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    private SharedPreferences sharedPreferences;

    private ArrayList<noticeModel> noticeArrayList;
    private noticeAdapter noticeAdapter;

    private Typeface typeface, typeface2, typeface3;
    private Context context;
    String collgename;
    String coursename;
    String adminKey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNoticeBinding.inflate(inflater, container, false);
        context = getActivity().getApplicationContext();

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        sharedPreferences = context.getSharedPreferences("my_data", MODE_PRIVATE);
        collgename = sharedPreferences.getString("_college", "");
        coursename = sharedPreferences.getString("_course", "");
        adminKey = sharedPreferences.getString("key_admin", "");
        noticeArrayList = new ArrayList<>();

        binding.noticeRecyclerview.setLayoutManager(new LinearLayoutManager(context.getApplicationContext()));
        binding.noticeRecyclerview.setHasFixedSize(true);
        noticeArrayList = new ArrayList<>();
        getOurNotices();
        noticeAdapter = new noticeAdapter(context.getApplicationContext(), noticeArrayList);
        binding.noticeRecyclerview.setAdapter(noticeAdapter);

        return binding.getRoot();
    }

    private void getOurNotices() {
        databaseReference.child(coursename).child(collgename).child(adminKey).child("Notice").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                noticeArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    noticeModel model = dataSnapshot.getValue(noticeModel.class);
                    noticeArrayList.add(model);
                }
                noticeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}