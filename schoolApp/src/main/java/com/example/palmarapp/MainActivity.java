package com.example.palmarapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText searchBar;
    TextView tvCategories, tvFeatured, tvNewIn, tvDiscounts;
    Button btnGoBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_act);

        searchBar = findViewById(R.id.searchBar);
        tvCategories = findViewById(R.id.tvCategories);
        tvFeatured = findViewById(R.id.tvFeatured);
        tvNewIn = findViewById(R.id.tvNewIn);
        tvDiscounts = findViewById(R.id.tvDiscounts);
        btnGoBack = findViewById(R.id.btnGoBack);

        btnGoBack.setOnClickListener(v -> {
            finish(); // This closes the current activity and returns to the previous one
        });
        tvFeatured.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProductListingPage.class);
            startActivity(intent);
        });

    }
}
