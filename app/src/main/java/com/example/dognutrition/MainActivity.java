package com.example.dognutrition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerView, recyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewCategory();
        recyclerViewPopular();
        bottomNavigation();



    }
    private void bottomNavigation(){
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingBtn);
        LinearLayout homeBtn = findViewById(R.id.linearBtn);

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

        adapter = new CategoryAdapter(categories);
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