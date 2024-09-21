package com.example.dognutrition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CartActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private ManagementCart managementCart;
    TextView totalItemText,totalDeliveryText,totalTaxText,totalText, emptytext;
    private double tax;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        managementCart = new ManagementCart(this);

        initView();
        initList();
        CalculateCart();
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
                startActivity(new Intent(CartActivity.this,CartActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this, MainActivity.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this, ProfileActivity.class));
            }
        });
        educationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this, ArticleActivity.class));
            }
        });
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this, EditProfileActivity.class));
            }
        });

    }


    private void initView(){
        recyclerView = findViewById(R.id.recyclerView3);
        totalItemText = findViewById(R.id.totalItemText);
        totalDeliveryText = findViewById(R.id.totalDeliveryText);
        totalTaxText = findViewById(R.id.totalTaxText);
        totalText = findViewById(R.id.totalText);
        emptytext = findViewById(R.id.emptyText);
        scrollView = findViewById(R.id.scrollView3);
    }

    private  void initList(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CartAdapter(managementCart.getListCart(), this, new ChangeNumberItemListener() {
            @Override
            public void changed() {
                CalculateCart();
            }
        });
        recyclerView.setAdapter(adapter);
        if(managementCart.getListCart().isEmpty()){
            emptytext.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }else{
            emptytext.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }
    private void CalculateCart(){
        double percentageTax = 0.02;
        double delivery = 10;

        tax = Math.round((managementCart.getTotalFee() * percentageTax) * 100) / 100;
        double total = Math.round((managementCart.getTotalFee() + tax + delivery) * 100) / 100;
        double itemTotal = Math.round((managementCart.getTotalFee()*100)/100);

        Log.d("CartActivity", "Total Fee: " + total + ", Tax: " + tax + ", Total: " + itemTotal);
        totalItemText.setText("$"+itemTotal);
        totalTaxText.setText("$"+ tax);
        totalDeliveryText.setText("$"+delivery);
        totalText.setText("$"+ total);

    }
}