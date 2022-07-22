package com.genral_now_ledge.collegemanagementusers.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.genral_now_ledge.collegemanagementusers.Models.courseModel;
import com.genral_now_ledge.collegemanagementusers.R;
import com.genral_now_ledge.collegemanagementusers.databinding.CourseItemBinding;

import java.util.ArrayList;

public class courseAdapter extends RecyclerView.Adapter<courseAdapter.courseViewHolder> {
    private Context context;
    private ArrayList<courseModel> arrayList;

    public courseAdapter(Context context, ArrayList<courseModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public courseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new courseViewHolder(LayoutInflater.from(context).inflate(R.layout.course_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull courseViewHolder holder, int position) {
        courseModel courseModel = arrayList.get(position);
        holder.binding.courseItem.setText(courseModel.getCourse());
        Typeface typeface = ResourcesCompat.getFont(context, R.font.hand_writing);
        holder.binding.courseItem.setTypeface(typeface);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class courseViewHolder extends RecyclerView.ViewHolder {
        CourseItemBinding binding;

        public courseViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CourseItemBinding.bind(itemView);


        }
    }
}
