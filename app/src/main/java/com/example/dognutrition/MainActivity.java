package com.example.dognutrition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.FirebaseApp;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private RecyclerView.Adapter adapter;
private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewCategory();
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
}