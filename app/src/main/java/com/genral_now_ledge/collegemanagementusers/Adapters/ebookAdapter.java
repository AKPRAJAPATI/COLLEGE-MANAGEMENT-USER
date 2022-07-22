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
import com.genral_now_ledge.collegemanagementusers.Models.ebookModel;
import com.genral_now_ledge.collegemanagementusers.R;
import com.genral_now_ledge.collegemanagementusers.databinding.CourseItemBinding;
import com.genral_now_ledge.collegemanagementusers.databinding.EbookItemBinding;

import java.util.ArrayList;

public class ebookAdapter extends RecyclerView.Adapter<ebookAdapter.courseViewHolder> {
    private Context context;
    private ArrayList<ebookModel> arrayList;

    public ebookAdapter(Context context, ArrayList<ebookModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public courseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new courseViewHolder(LayoutInflater.from(context).inflate(R.layout.ebook_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull courseViewHolder holder, int position) {
        ebookModel courseModel = arrayList.get(position);
        holder.binding.courseItem.setText(courseModel.getPdf_name());
        String download_link = courseModel.getPdf_url();

        holder.binding.courseItem.setSelected(true);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class courseViewHolder extends RecyclerView.ViewHolder {
        EbookItemBinding binding;

        public courseViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = EbookItemBinding.bind(itemView);


        }
    }
}
