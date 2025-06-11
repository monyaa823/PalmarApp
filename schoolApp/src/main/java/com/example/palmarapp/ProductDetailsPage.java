package com.example.palmarapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import com.example.palmarapp.CartPage;

public class ProductDetailsPage extends AppCompatActivity {

    TextView tvProductName, tvProductDescription;
    ImageView imgProduct;
    Button btnGoBack, btnAddToCart, btnViewCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prod_details_act);

        tvProductName = findViewById(R.id.tvProductName);
        tvProductDescription = findViewById(R.id.tvProductDescription);
        imgProduct = findViewById(R.id.imgProduct);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnViewCart = findViewById(R.id.btnViewCart);
        btnGoBack = findViewById(R.id.btnGoBack);

        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        int imageId = getIntent().getIntExtra("imageId", 0);

        tvProductName.setText(name);
        tvProductDescription.setText(description);
        imgProduct.setImageResource(imageId);

        btnAddToCart.setOnClickListener(v -> {
            CartPage.addToCart(name);
            Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show();
        });

        btnGoBack.setOnClickListener(v -> finish());

        btnViewCart.setOnClickListener(v -> {
            Intent intent = new Intent(ProductDetailsPage.this, CartPage.class);
            startActivity(intent);
        });
    }


}
