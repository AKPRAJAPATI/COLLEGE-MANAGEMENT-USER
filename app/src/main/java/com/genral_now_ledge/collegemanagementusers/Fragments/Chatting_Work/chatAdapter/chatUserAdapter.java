package com.genral_now_ledge.collegemanagementusers.Fragments.Chatting_Work.chatAdapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.genral_now_ledge.collegemanagementusers.Fragments.Chatting_Work.chatActivity;
import com.genral_now_ledge.collegemanagementusers.Models.registerModel;
import com.genral_now_ledge.collegemanagementusers.R;
import com.genral_now_ledge.collegemanagementusers.databinding.UserChatItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class chatUserAdapter extends RecyclerView.Adapter<chatUserAdapter.courseViewHolder> {
    private Context context;
    private ArrayList<registerModel> arrayList;

    String collgename;
    String coursename;
    String adminAuthId;
    private SharedPreferences sharedPreferences;

    public chatUserAdapter(Context context, ArrayList<registerModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public courseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new courseViewHolder(LayoutInflater.from(context).inflate(R.layout.user_chat_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull courseViewHolder holder, int position) {
        registerModel courseModel = arrayList.get(position);
        holder.binding.userChatNameItem.setText(courseModel.getName());
        holder.binding.userChatGmailItem.setText(courseModel.getEmail());
        Picasso.get().load(courseModel.getProfile_image()).into(holder.binding.chatUserProfile);

        Typeface typeface = ResourcesCompat.getFont(context,R.font.hand_writing);
        holder.binding.userChatGmailItem.setTypeface(typeface);
        holder.binding.userChatNameItem.setTypeface(typeface);
        sharedPreferences = context.getSharedPreferences("my_data", MODE_PRIVATE);
        collgename = sharedPreferences.getString("_college", "");
        coursename = sharedPreferences.getString("_course", "");
        adminAuthId =  sharedPreferences.getString("key_admin","");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, chatActivity.class);
                intent.putExtra("profile",courseModel.getProfile_image());
                intent.putExtra("username",courseModel.getName());
                intent.putExtra("gmail",courseModel.getEmail());
                intent.putExtra("course",courseModel.getCourse());
                intent.putExtra("userAuthId",courseModel.getAuth_id());

                intent.putExtra("college",collgename);
                intent.putExtra("adminId",adminAuthId);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class courseViewHolder extends RecyclerView.ViewHolder {
        UserChatItemBinding binding;

        public courseViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = UserChatItemBinding.bind(itemView);


        }
    }

}
