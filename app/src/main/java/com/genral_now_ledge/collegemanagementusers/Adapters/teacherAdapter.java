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
import com.genral_now_ledge.collegemanagementusers.Models.teacherModel;
import com.genral_now_ledge.collegemanagementusers.R;
import com.genral_now_ledge.collegemanagementusers.databinding.CourseItemBinding;
import com.genral_now_ledge.collegemanagementusers.databinding.TeacherItemsBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class teacherAdapter extends RecyclerView.Adapter<teacherAdapter.courseViewHolder> {
    private Context context;
    private ArrayList<teacherModel> arrayList;

    public teacherAdapter(Context context, ArrayList<teacherModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public courseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new courseViewHolder(LayoutInflater.from(context).inflate(R.layout.teacher_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull courseViewHolder holder, int position) {
        teacherModel courseModel = arrayList.get(position);
        holder.binding.teacherName.setText(courseModel.getTeacherName());
        holder.binding.teacherNo.setText(courseModel.getTeacherPhone());
        holder.binding.teacherPost.setText(courseModel.getTeacherPost());
        holder.binding.teacherSubjects.setText(courseModel.getTeacherSubjects());
        //  holder.binding.teacherImage.setImageResource(R.drawable.ic_baseline_person_outline_24);
       Picasso.get().load(courseModel.getTeacherProfile()).into(holder.binding.teacherImage);

       Typeface typeface = ResourcesCompat.getFont(context.getApplicationContext(),R.font.hand_writing);
       holder.binding.teacherSubjects.setTypeface(typeface);
       holder.binding.teacherName.setTypeface(typeface);
       holder.binding.teacherNo.setTypeface(typeface);
       holder.binding.teacherPost.setTypeface(typeface);


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class courseViewHolder extends RecyclerView.ViewHolder {
        TeacherItemsBinding binding;
        public courseViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = TeacherItemsBinding.bind(itemView);
        }
    }
}
