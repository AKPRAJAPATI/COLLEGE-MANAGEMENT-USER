package com.genral_now_ledge.collegemanagementusers.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.genral_now_ledge.collegemanagementusers.R;
import com.genral_now_ledge.collegemanagementusers.databinding.FragmentDeveloperBinding;

public class developerFragment extends Fragment {
    private FragmentDeveloperBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_developer, container, false);
        binding = FragmentDeveloperBinding.inflate(inflater, parent, false);
        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.hand_writing);
        binding.signingText.setTypeface(typeface);
        return binding.getRoot();
    }
}