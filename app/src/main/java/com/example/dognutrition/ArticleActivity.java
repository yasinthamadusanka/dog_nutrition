package com.example.dognutrition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ArticleActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ArticleClass> dataList;
    ArticleAdapter adapter;
    ArticleClass androidData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        recyclerView = findViewById(R.id.articleRecyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(ArticleActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        dataList = new ArrayList<>();

        androidData = new ArticleClass("Dog Food Ingredients for Healthy Dogs", R.string.essential_dog_food_ingredients_content,R.drawable.article1);
        dataList.add(androidData);
        androidData = new ArticleClass("How to Choose the Best Dog Food", R.string.choose_best_dog_food_content,R.drawable.article2);
        dataList.add(androidData);
        androidData = new ArticleClass("How to Tell if Your Dog is Underweight", R.string.how_to_tell_if_underweight_content,R.drawable.article3);
        dataList.add(androidData);
        androidData = new ArticleClass("How to Choose Healthy Dry Dog Food", R.string.healthy_dry_dog_food_content,R.drawable.article4);
        dataList.add(androidData);
        androidData = new ArticleClass("Finding the Right Dog Bowl for Your Dog", R.string.finding_right_dog_bowl_content,R.drawable.article5);
        dataList.add(androidData);

        adapter = new ArticleAdapter(ArticleActivity.this, dataList);
        recyclerView.setAdapter(adapter);
        bottomNavigation();
    }

    private void bottomNavigation(){
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingBtn);
        ImageView homeBtn = findViewById(R.id.homeBtn);
        ImageView profileBtn = findViewById(R.id.profileBtn);
        ImageView educationBtn = findViewById(R.id.educationBtn);
        ImageView settingsBtn = findViewById(R.id.settingsBtn);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ArticleActivity.this,CartActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ArticleActivity.this, MainActivity.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ArticleActivity.this, ProfileActivity.class));
            }
        });
        educationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ArticleActivity.this, ArticleActivity.class));
            }
        });
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ArticleActivity.this, EditProfileActivity.class));
            }
        });



    }
}