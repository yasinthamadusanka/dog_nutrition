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
    String[] arrname;
    int[] arrimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        searchView = findViewById(R.id.searchView);

        arrname = new String[]{
                "Royal",
                "Hill",
                "Buffalo",
                "Purina",
                "Wellness",
                "Merrick",
                "Nature",
                "Taste",
                "Orijen",
                "Acana",
                "Greenies",
                "Nylabone",
                "Vita",
                "Ollie",
                "Honest",
                "Zuke",
                "Canidae",
                "Solid",
                "Purina",
                "Merrick",
                "Eukanuba",
                "Wellness",
                "Rachael",
                "Victor",
                "Canidae"
        };

        arrimg = new int[]{
                R.drawable.pic1,
                R.drawable.pic2,
                R.drawable.pic3,
                R.drawable.pic4,
                R.drawable.pic5,
                R.drawable.pic6,
                R.drawable.pic7,
                R.drawable.pic8,
                R.drawable.pic9,
                R.drawable.pic10,
                R.drawable.pic11,
                R.drawable.pic12,
                R.drawable.pic13,
                R.drawable.pic14,
                R.drawable.pic15,
                R.drawable.pic16,
                R.drawable.pic17,
                R.drawable.pic18,
                R.drawable.pic19,
                R.drawable.pic20,
                R.drawable.pic21,
                R.drawable.pic22,
                R.drawable.pic23,
                R.drawable.pic24,
                R.drawable.pic25
        };



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

        mList = new ArrayList<>();
        mList.add(new Popular("Royal Canin","pic1","Tailored nutrition for different dog sizes (small, medium, large)",90.0,3.5));
        mList.add(new Popular("Hill's Science Diet","pic2","Available in chicken and barley or lamb and rice, formulated for adults",70.0,3.0));
        mList.add(new Popular("Blue Buffalo","pic3","Includes real meat as the first ingredient, with whole grains and garden",80.0,2.5));
        mList.add(new Popular("Purina","pic4","Contains live probiotics for digestive health, high in protein",70.0,4.5));
        mList.add(new Popular("Wellness","pic5","High protein, grain-free formula with deboned turkey and chicken",90.0,4.0));
        mList.add(new Popular("Merrick","pic6","High-protein, grain-free formula with real beef as the first ingredient",100.0,2.5));
        mList.add(new Popular("Nature's Logic","pic7"," Natural, whole-food ingredients with no synthetic vitamins or minerals",85.0,3.5));
        mList.add(new Popular("Taste of the Wild","pic8","Grain-free, with roasted bison and roasted roasted roasted beef",85.0,3.0));
        mList.add(new Popular("Orijen","pic9","Biologically appropriate food with fresh, regional ingredients",120.0,2.5));
        mList.add(new Popular("Acana","pic10","Limited ingredient diets with single animal protein sources and wholesome grains.",110.0,2.5));
        mList.add(new Popular("Greenies","pic11","Helps clean teeth and freshen breath",25.0,2.0));
        mList.add(new Popular("Nylabone","pic12","Durable chew toy designed for heavy chewers",20.0,3.5));
        mList.add(new Popular("Vita Bone","pic13","Various flavors with added vitamins and minerals",15.0,4.5));
        mList.add(new Popular("Ollie","pic14","Customized meal plans delivered fresh to your door",10.0,4.0));
        mList.add(new Popular("The Honest Kitchen","pic15","Human-grade ingredients with minimal processing",110.0,4.5));
        mList.add(new Popular("Zuke's","pic16","Soft, moist treats ideal for training",15.0,3.5));
        mList.add(new Popular("Canidae","pic17","Limited ingredient diet with fresh meat, whole foods, and 8 key ingredients.",85.0,2.5));
        mList.add(new Popular("Solid Gold","pic18","Grain and gluten-free with high-quality protein like chicken and vegetables",45.0,3.5));
        mList.add(new Popular("Purina","pic19","Natural, grain-free formula with high-quality protein sources",70.0,2.5));
        mList.add(new Popular("Merrick","pic20","High-protein, grain-free kibble mixed with freeze-dried raw pieces",100.0,4.5));
        mList.add(new Popular("Eukanuba","pic21","Balanced nutrition with high-quality proteins and prebiotics",80.0,4.0));
        mList.add(new Popular("Wellness","pic22","Limited ingredient diet for dogs with food sensitivities",85.0,3.0));
        mList.add(new Popular("Rachael Ray Nutrish","pic23","Grain-free with real turkey or chicken as the first ingredient",70.0,4.5));
        mList.add(new Popular("Victor","pic24","High-protein, multi-meat formula suitable for active and working dogs",90.0,3.5));
        mList.add(new Popular("Canidae","pic25"," Limited ingredient treats with high-quality proteins and no fillers",20.0,3.0));



        adapter = new SearchAdapter(mList);
        recyclerView.setAdapter(adapter);
    }
}