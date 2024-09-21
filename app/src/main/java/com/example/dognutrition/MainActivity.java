package com.example.dognutrition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerView, recyclerView2;
    private FirebaseAuth firebaseAuth;
    TextView textView3;
    ImageView imageView2;
    ConstraintLayout orderNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView3 = findViewById(R.id.textView3);
        imageView2 = findViewById(R.id.imageView2);
        orderNow = findViewById(R.id.orderNow);

        firebaseAuth = FirebaseAuth.getInstance();

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        loadProfileImage();
        fetchUserData(userId);

        recyclerViewCategory();
        recyclerViewPopular();
        bottomNavigation();

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchViewActivity.class);
                startActivity(intent);
            }
        });



    }

    private void loadProfileImage() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        databaseReference.child("profileImageUrl").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String imageUrl = dataSnapshot.getValue(String.class);
                    if (imageUrl != null) {
                        Glide.with(MainActivity.this)
                                .load(imageUrl)
                                .transform(new CircleTransformation())
                                .placeholder(R.drawable.profile_background)
                                .error(R.drawable.person)
                                .into(imageView2);
                    }
                }else{
                    imageView2.setImageResource(R.drawable.profile_background);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUserData(String userId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String username = dataSnapshot.child("userName").getValue(String.class);

                textView3.setText("Hi "+username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void bottomNavigation(){
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingBtn);
        ImageView homeBtn = findViewById(R.id.homeBtn);
        ImageView profileBtn = findViewById(R.id.profileBtn);
        ImageView educationBtn = findViewById(R.id.educationBtn);
        ImageView settingsBtn = findViewById(R.id.settingsBtn);
        EditText searchView = findViewById(R.id.searchView);

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SearchViewActivity.class);
                startActivity(intent);
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CartActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
        educationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ArticleActivity.class));
            }
        });
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }



    private void recyclerViewCategory(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category("Kibble","cat1"));
        categories.add(new Category("Canned","cat2"));
        categories.add(new Category("Grain","cat3"));
        categories.add(new Category("Special","cat4"));
        categories.add(new Category("Treats","cat5"));

        adapter = new CategoryAdapter(categories,this);
        recyclerView.setAdapter(adapter);
    }

    private void recyclerViewPopular(){
      LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
      recyclerView2 = findViewById(R.id.recyclerView2);
      recyclerView2.setLayoutManager(linearLayoutManager);

      ArrayList<Popular> populars = new ArrayList<>();
      populars.add(new Popular("Royal Canin","pic1","Tailored nutrition for different dog sizes (small, medium, large)",90.0));
      populars.add(new Popular("Hill's Science Diet","pic2","Available in chicken and barley or lamb and rice, formulated for adults",70.0));
      populars.add(new Popular("Blue Buffalo","pic3","Includes real meat as the first ingredient, with whole grains and garden",80.0));
      populars.add(new Popular("Purina","pic4","Contains live probiotics for digestive health, high in protein",70.0));
      populars.add(new Popular("Wellness","pic5","High protein, grain-free formula with deboned turkey and chicken",90.0));
      populars.add(new Popular("Merrick","pic6","High-protein, grain-free formula with real beef as the first ingredient",100.0));

      adapter2 = new PopularAdapter(populars);
      recyclerView2.setAdapter(adapter2);
    }
}