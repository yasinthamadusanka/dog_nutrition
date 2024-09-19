package com.example.dognutrition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ShowDetailsActivity extends AppCompatActivity {
    private TextView addToCartBtn;
    private TextView titleText, feeText, descriptionText, numberOrderText;
    private ImageView plusBtn, minusBtn, pic;
    private Popular popular;
    int numberOrder = 1;
    private ManagementCart managementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        managementCart = new ManagementCart(this);
        initView();
        getBundle();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Close this activity and go back to the previous one
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getBundle(){
        popular = (Popular) getIntent().getSerializableExtra("popular");

        int drawableResourceId = this.getResources().getIdentifier(popular.getPic(),"drawable",this.getPackageName());
        Glide.with(this)
                .load(drawableResourceId)
                .into(pic);

        titleText.setText(popular.getTitle());
        feeText.setText("$" +popular.getFee());
        descriptionText.setText(popular.getDescription());
        numberOrderText.setText(String.valueOf(numberOrder));

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrder = numberOrder + 1;
                numberOrderText.setText(String.valueOf(numberOrder));

            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberOrder>1){
                    numberOrder = numberOrder - 1;
                }
                numberOrderText.setText(String.valueOf(numberOrder));
            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               popular.setNumberInCart(numberOrder);
               managementCart.insertFood(popular);
            }
        });

    }

    private void initView(){
        addToCartBtn = findViewById(R.id.addToCartBtn);
        titleText = findViewById(R.id.titleText);
        feeText = findViewById(R.id.priceText);
        descriptionText = findViewById(R.id.descriptionText);
        numberOrderText = findViewById(R.id.numberOrderText);
        minusBtn = findViewById(R.id.minusBtn);
        plusBtn = findViewById(R.id.plusBtn);
        pic = findViewById(R.id.picFood);
    }
}