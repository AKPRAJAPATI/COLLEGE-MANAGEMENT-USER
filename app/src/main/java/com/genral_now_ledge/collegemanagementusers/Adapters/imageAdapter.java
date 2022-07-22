package com.genral_now_ledge.collegemanagementusers.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.genral_now_ledge.collegemanagementusers.Models.ImageModel;
import com.genral_now_ledge.collegemanagementusers.Models.teacherModel;
import com.genral_now_ledge.collegemanagementusers.R;
import com.genral_now_ledge.collegemanagementusers.databinding.ImageItemsBinding;
import com.genral_now_ledge.collegemanagementusers.databinding.TeacherItemsBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class imageAdapter extends RecyclerView.Adapter<imageAdapter.courseViewHolder> {
    private Context context;
    private ArrayList<ImageModel> arrayList;

    public imageAdapter(Context context, ArrayList<ImageModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public courseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new courseViewHolder(LayoutInflater.from(context).inflate(R.layout.image_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull courseViewHolder holder, int position) {
        ImageModel courseModel = arrayList.get(position);
       Picasso.get().load(courseModel.getImage_url()).into(holder.binding.imageItems);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class courseViewHolder extends RecyclerView.ViewHolder {
        ImageItemsBinding binding;
        public courseViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ImageItemsBinding.bind(itemView);
        }
    }
}
