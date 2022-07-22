package com.genral_now_ledge.collegemanagementusers.Fragments.Chatting_Work;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.genral_now_ledge.collegemanagementusers.Fragments.Chatting_Work.chatAdapter.chatUserAdapter;
import com.genral_now_ledge.collegemanagementusers.Models.registerModel;
import com.genral_now_ledge.collegemanagementusers.databinding.FragmentChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class chatFragment extends Fragment {
    private FragmentChatBinding binding;

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    private SharedPreferences sharedPreferences;

    private ArrayList<registerModel> userArrayList;
    private chatUserAdapter adapter;
    private Context context;
    String collgename;
    String coursename;
    String adminKey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater, container, false);
        context = getActivity().getApplicationContext();

        binding.shimmerLayout.startShimmer();
        binding.shimmerLayout2.startShimmer();
        binding.shimmerLayout3.startShimmer();
        binding.shimmerLayout4.startShimmer();
        binding.shimmerLayout5.startShimmer();
        binding.shimmerLayout6.startShimmer();
        binding.shimmerLayout7.startShimmer();
        binding.shimmerLayout8.startShimmer();

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        sharedPreferences = context.getSharedPreferences("my_data", MODE_PRIVATE);
        collgename = sharedPreferences.getString("_college", "");
        coursename = sharedPreferences.getString("_course", "");
        adminKey = sharedPreferences.getString("key_admin", "");
        userArrayList = new ArrayList<>();
        binding.userChatRecyclerview.setLayoutManager(new LinearLayoutManager(context.getApplicationContext()));
        binding.userChatRecyclerview.setHasFixedSize(true);
        userArrayList = new ArrayList<>();
        getOurUsers();
        adapter = new chatUserAdapter(context.getApplicationContext(), userArrayList);
        binding.userChatRecyclerview.setAdapter(adapter);

        //create group

        return  binding.getRoot();
    }

    private void getOurUsers() {
        databaseReference.child(coursename).child(collgename).child(adminKey).child("All Students").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    registerModel model = dataSnapshot.getValue(registerModel.class);

                    if (!auth.getUid().equals(model.getAuth_id())){
                        userArrayList.add(model);
                    }


binding.simmerLinear.setVisibility(View.GONE);
                    binding.shimmerLayout.setVisibility(View.GONE);
                    binding.shimmerLayout2.setVisibility(View.GONE);
                    binding.shimmerLayout3.setVisibility(View.GONE);
                    binding.shimmerLayout4.setVisibility(View.GONE);
                    binding.shimmerLayout5.setVisibility(View.GONE);
                    binding.shimmerLayout6.setVisibility(View.GONE);
                    binding.shimmerLayout7.setVisibility(View.GONE);
                    binding.shimmerLayout8.setVisibility(View.GONE);
                    binding.userChatRecyclerview.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}