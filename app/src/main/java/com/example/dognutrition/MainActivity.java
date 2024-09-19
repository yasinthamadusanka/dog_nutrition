package com.example.dognutrition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

      adapter2 = new PopularAdapter(populars);
      recyclerView2.setAdapter(adapter2);
    }
}