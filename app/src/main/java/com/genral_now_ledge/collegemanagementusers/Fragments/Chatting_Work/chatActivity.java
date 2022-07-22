package com.genral_now_ledge.collegemanagementusers.Fragments.Chatting_Work;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genral_now_ledge.collegemanagementusers.Fragments.Chatting_Work.Model.MessageModel;
import com.genral_now_ledge.collegemanagementusers.Fragments.Chatting_Work.chatAdapter.chatAdapter;
import com.genral_now_ledge.collegemanagementusers.R;
import com.genral_now_ledge.collegemanagementusers.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class chatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    private String userName;
    private String userProfile;
    private String userAuthId;
    private String admin_key;
    private String gmail;
    private String collgenameString;
    private String coursenameString;
    private int GALLERY_REQUEST_CODE = 100;

    private String SENDER_ROOM;
    private String RECEIVER_ROOM;

    private Uri IMAGE_URI;


    private MessageModel myMessage;
    private chatAdapter chatAdptr;
    private ArrayList<MessageModel> messageArrayList;

    private ProgressDialog progressDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /////////////////////////////FIREBASE VERIABLE///////////////
        userAuthId = getIntent().getStringExtra("userAuthId");
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        binding.shimmerLayoutImageSlider.startShimmer();
        binding.chatRelativeLayout.setBackgroundColor(getResources().getColor(R.color.white));

        SENDER_ROOM = auth.getUid() + userAuthId;
        RECEIVER_ROOM = userAuthId + auth.getUid();
        /////////////////////////////FIREBASE VERIABLE///////////////
          progressDialog2 = new ProgressDialog(getApplicationContext());
        progressDialog2.setMessage("Send Image");
        progressDialog2.setCancelable(false);

        userName = getIntent().getStringExtra("username");
        userProfile = getIntent().getStringExtra("profile");
        gmail = getIntent().getStringExtra("gmail");
        coursenameString = getIntent().getStringExtra("course");
        collgenameString = getIntent().getStringExtra("college");
        admin_key = getIntent().getStringExtra("adminId");

        //////////////////SET UP TOOLBAR DATA////////////////////////////////////
        Picasso.get().load(userProfile).into(binding.friendProfile);
        binding.coursename.setText(coursenameString);
        binding.friendGmail.setText(gmail);
        //////////////////SET UP TOOLBAR DATA////////////////////////////////////
        binding.sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if (!binding.messageEditText.getText().toString().equals("")){
                     sendMessage();
                }else{
                    sendImage();
                    binding.messageEditText.setHint("Wait Uploading Image");
                }
            }
        });
        binding.ourSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
openGallery();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.chatRecyclerview.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setStackFromEnd(true);
        binding.chatRecyclerview.setHasFixedSize(true);

        messageArrayList = new ArrayList<>();

        chatAdptr = new chatAdapter(getApplicationContext(), messageArrayList);
        getOurMessages();
        binding.chatRecyclerview.setAdapter(chatAdptr);

    }

    private void sendImage() {


        storageReference.child("chat_images").child(auth.getUid() + new Date().getTime()).putFile(IMAGE_URI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> IMAGE_TASK = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                IMAGE_TASK.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String image_uri = uri.toString();

                        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                        Date date = new Date();
                        String messageTime = dateFormat.format(date);

                        myMessage = new MessageModel();
                        myMessage.setImageUrl(image_uri);
                        myMessage.setMessageTime(messageTime);
                        myMessage.setMessageType("photo");
                        myMessage.setSendTo(auth.getUid());

                        databaseReference.child(coursenameString).child(collgenameString).child(admin_key)
                                .child("Chats")
                                .child(SENDER_ROOM)
                                .child("messages")
                                .push()
                                .setValue(myMessage).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            databaseReference.child(coursenameString).child(collgenameString).child(admin_key)
                                                    .child("Chats")
                                                    .child(RECEIVER_ROOM)
                                                    .child("messages")
                                                    .push()
                                                    .setValue(myMessage).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                binding.messageEditText.setHint("Image Uploaded Success");
                                                                Toast.makeText(chatActivity.this, "Image sent", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });

                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                progressDialog2.setMessage("Saving Image " + (int) progress + "%");
            }
        });

    }

    private void getOurMessages() {
        databaseReference.child(coursenameString).child(collgenameString).child(admin_key)
                .child("Chats")
                .child(SENDER_ROOM)
                .child("messages").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageArrayList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            MessageModel model = dataSnapshot.getValue(MessageModel.class);
                            messageArrayList.add(model);
                            binding.chatRelativeLayout.setBackgroundColor(getResources().getColor(R.color.whitebck));
                            binding.shimmerLayoutImageSlider.setVisibility(View.GONE);
                            binding.chatRecyclerview.setVisibility(View.VISIBLE);

                            binding.chatRecyclerview.smoothScrollToPosition(binding.chatRecyclerview.getAdapter().getItemCount());
                        }
                        chatAdptr.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void sendMessage() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String messageTime = dateFormat.format(date);

        myMessage = new MessageModel();
        myMessage.setMessage(binding.messageEditText.getText().toString());
        myMessage.setMessageTime(messageTime);
        myMessage.setMessageType("text");
        myMessage.setSendTo(auth.getUid());

        databaseReference.child(coursenameString).child(collgenameString).child(admin_key)
                .child("Chats")
                .child(SENDER_ROOM)
                .child("messages")
                .push()
                .setValue(myMessage).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            databaseReference.child(coursenameString).child(collgenameString).child(admin_key)
                                    .child("Chats")
                                    .child(RECEIVER_ROOM)
                                    .child("messages")
                                    .push()
                                    .setValue(myMessage).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                binding.messageEditText.setText("");
                                                Toast.makeText(chatActivity.this, "sent", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });



    }

    //////////onpen gallery/////////////
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE || resultCode == RESULT_OK) {
              IMAGE_URI = data.getData();
            binding.ourSelectImage.setImageURI(IMAGE_URI);
        }
    }
    /////////////////close work //////////////////
}