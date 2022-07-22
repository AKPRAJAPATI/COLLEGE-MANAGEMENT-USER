package com.genral_now_ledge.collegemanagementusers.Fragments.Chatting_Work.chatAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.genral_now_ledge.collegemanagementusers.Fragments.Chatting_Work.Model.MessageModel;
import com.genral_now_ledge.collegemanagementusers.R;
import com.genral_now_ledge.collegemanagementusers.databinding.RecieveMessageBinding;
import com.genral_now_ledge.collegemanagementusers.databinding.SendMessageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class chatAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<MessageModel> arrayList;

    private int MESSAGE_SEND = 1;
    private int MESSAGE_RECEIVE = 2;

    public chatAdapter(Context context, ArrayList<MessageModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MESSAGE_SEND) {
            return new sendViewHodler(LayoutInflater.from(context).inflate(R.layout.send_message, parent, false));
        } else {
            return new receiverViewHodler(LayoutInflater.from(context).inflate(R.layout.recieve_message, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel model = arrayList.get(position);
        if (holder.getClass() == sendViewHodler.class) {
            sendViewHodler sendViewHodler = (chatAdapter.sendViewHodler) holder;
            if (model.getMessageType().equals("text"))
            {
                sendViewHodler.binding.sendMessageTextView.setVisibility(View.VISIBLE);
                sendViewHodler.binding.sendImageView.setVisibility(View.GONE);
            }else
            {
                sendViewHodler.binding.sendMessageTextView.setVisibility(View.GONE);
                sendViewHodler.binding.sendImageView.setVisibility(View.VISIBLE);
            }

            sendViewHodler.binding.sendMessageTextView.setText(model.getMessage());
            Picasso.get().load(model.getImageUrl()).placeholder(R.drawable.imageak).into(sendViewHodler.binding.sendImageView);
        } else {
            receiverViewHodler receiverViewHodler = (chatAdapter.receiverViewHodler) holder;
            receiverViewHodler.binding.sendMessageTextView.setText(model.getMessage());
            Picasso.get().load(model.getImageUrl()).placeholder(R.drawable.imageak).into(receiverViewHodler.binding.sendImageView);
        }

    }

    @Override
    public int getItemViewType(int position) {
        MessageModel model = arrayList.get(position);
        if (FirebaseAuth.getInstance().getUid().equals(model.getSendTo())) {
            return MESSAGE_SEND;
        } else {
            return MESSAGE_RECEIVE;
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class sendViewHodler extends RecyclerView.ViewHolder {
        SendMessageBinding binding;
        public sendViewHodler(@NonNull View itemView) {
            super(itemView);
            binding = SendMessageBinding.bind(itemView);
        }
    }

    public class receiverViewHodler extends RecyclerView.ViewHolder {
        RecieveMessageBinding binding;
        public receiverViewHodler(@NonNull View itemView) {
            super(itemView);
            binding = RecieveMessageBinding.bind(itemView);
        }
    }
}
