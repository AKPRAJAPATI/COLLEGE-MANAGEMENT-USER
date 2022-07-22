package com.genral_now_ledge.collegemanagementusers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.genral_now_ledge.collegemanagementusers.Fragments.HomeFragment;
import com.genral_now_ledge.collegemanagementusers.Fragments.ImageFragment;
import com.genral_now_ledge.collegemanagementusers.Fragments.NoticeFragment;
import com.genral_now_ledge.collegemanagementusers.Fragments.TeacherFragment;
import com.genral_now_ledge.collegemanagementusers.Fragments.VideoLectureFragment;
import com.genral_now_ledge.collegemanagementusers.Fragments.Chatting_Work.chatFragment;
import com.genral_now_ledge.collegemanagementusers.Fragments.developerFragment;
import com.genral_now_ledge.collegemanagementusers.Fragments.ebookFragment;
import com.genral_now_ledge.collegemanagementusers.Fragments.websitesFragment;
import com.genral_now_ledge.collegemanagementusers.authentication.RegisterActivity;
import com.genral_now_ledge.collegemanagementusers.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    String collgename;
    String coursename;
    String adminKey;
    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private SharedPreferences sharedPreferences;

    private ImageView navImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        sharedPreferences = getSharedPreferences("my_data", MODE_PRIVATE);
        collgename = sharedPreferences.getString("_college", "");
        coursename = sharedPreferences.getString("_course", "");
        adminKey = sharedPreferences.getString("admin_key", "");



        String fullCourseName = coursename +" - "+collgename;
        binding.coursename.setText(fullCourseName);



        //check user null or not
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        }

        HomeFragment homeFragment = new HomeFragment();
        loadFragments(homeFragment);
        drawerLayout.closeDrawer(GravityCompat.START);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.videoLecture:

                        VideoLectureFragment fragment = new VideoLectureFragment();
                        loadFragments(fragment);
                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;

                    case R.id.pdf:

                        ebookFragment ebookFragmentt = new ebookFragment();
                        loadFragments(ebookFragmentt);
                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;

                    case R.id.websites:

                        websitesFragment websitesFragment = new websitesFragment();
                        loadFragments(websitesFragment);
                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;
                    case R.id.share:
                        Toast.makeText(MainActivity.this, "share", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.rate_us:
                        Toast.makeText(MainActivity.this, "rate us ", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.developerProfile:
                        developerFragment developerFragment = new developerFragment();
                        loadFragments(developerFragment);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.logout:
                        auth.signOut();
                        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                        break;
                }
                return true;
            }
        });
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        HomeFragment homeFragment = new HomeFragment();
                        loadFragments(homeFragment);
                        break;
                    case R.id.news:
                        chatFragment chatFragment = new chatFragment();
                        loadFragments(chatFragment);
                        break;
                    case R.id.teacher:
                        TeacherFragment teacherFragment = new TeacherFragment();
                        loadFragments(teacherFragment);
                        break;
                    case R.id.image:
                        ImageFragment imageFragment = new ImageFragment();
                        loadFragments(imageFragment);
                        break;
                    case R.id.notice:
                        NoticeFragment noticeFragment = new NoticeFragment();
                        loadFragments(noticeFragment);
                        break;
                }
                return true;
            }
        });

        GET_MY_DATA();


    }





    private void GET_MY_DATA() {

    }

    private void loadFragments(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        fragmentTransaction.addToBackStack(null);

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
//            drawerLayout.closeDrawer(navigationView);
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            finish();
        }
    }
}