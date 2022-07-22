package com.genral_now_ledge.collegemanagementusers.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.genral_now_ledge.collegemanagementusers.Models.ImageModel;
import com.genral_now_ledge.collegemanagementusers.Models.noticeModel;
import com.genral_now_ledge.collegemanagementusers.R;
import com.genral_now_ledge.collegemanagementusers.databinding.ImageItemsBinding;
import com.genral_now_ledge.collegemanagementusers.databinding.NoticeItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class noticeAdapter extends RecyclerView.Adapter<noticeAdapter.courseViewHolder> {
    private Context context;
    private ArrayList<noticeModel> arrayList;

    public noticeAdapter(Context context, ArrayList<noticeModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public courseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new courseViewHolder(LayoutInflater.from(context).inflate(R.layout.notice_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull courseViewHolder holder, int position) {
        noticeModel courseModel = arrayList.get(position);
        String text = courseModel.getNoticeImageText();
        holder.binding.imageText.setText(text);
       Picasso.get().load(courseModel.getNoticeImage()).into(holder.binding.imageviewImage);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class courseViewHolder extends RecyclerView.ViewHolder {
        NoticeItemBinding binding;
        public courseViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = NoticeItemBinding.bind(itemView);
        }
    }
}
