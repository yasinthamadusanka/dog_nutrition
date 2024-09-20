package com.example.dognutrition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;

public class SearchViewActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SearchView searchView;
    private ArrayList<Popular> mList;
    private SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        searchView = findViewById(R.id.searchView);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
        addDataToList();
        bottomNavigation();
    }

    private void bottomNavigation(){
        FloatingActionButton floatingActionButton = findViewById(R.id.floatingBtn);
        ImageView homeBtn = findViewById(R.id.homeBtn);
        ImageView profileBtn = findViewById(R.id.profileBtn);
        ImageView educationBtn = findViewById(R.id.educationBtn);
        ImageView settingsBtn = findViewById(R.id.settingsBtn);

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchViewActivity.this,SearchViewActivity.class);
                startActivity(intent);
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchViewActivity.this,CartActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchViewActivity.this, MainActivity.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchViewActivity.this, ProfileActivity.class));
            }
        });
        educationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchViewActivity.this, ArticleActivity.class));
            }
        });
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }
    private void filterList(String query) {
        if (query != null) {
            ArrayList<Popular> filteredList = new ArrayList<>();
            for (Popular language : mList) {
                if (language.getTitle().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))) {
                    filteredList.add(language);
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show();
            } else {
                adapter.setFilteredList(filteredList);
            }
        }
    }


    private void addDataToList(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Popular> mList = new ArrayList<>();
        mList.add(new Popular("Royal Canin","pic1","Tailored nutrition for different dog sizes (small, medium, large)",90.0));
        mList.add(new Popular("Hill's Science Diet","pic2","Available in chicken and barley or lamb and rice, formulated for adults",70.0));
        mList.add(new Popular("Blue Buffalo","pic3","Includes real meat as the first ingredient, with whole grains and garden",80.0));
        mList.add(new Popular("Purina","pic4","Contains live probiotics for digestive health, high in protein",70.0));
        mList.add(new Popular("Wellness","pic5","High protein, grain-free formula with deboned turkey and chicken",90.0));
        mList.add(new Popular("Merrick","pic6","High-protein, grain-free formula with real beef as the first ingredient",100.0));

        adapter = new SearchAdapter(mList);
        recyclerView.setAdapter(adapter);
    }
}