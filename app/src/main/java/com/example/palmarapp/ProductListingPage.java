package com.example.palmarapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class ProductListingPage extends AppCompatActivity {

    ImageView imgProduct1, imgProduct2;
    TextView tvProduct1, tvProduct2;
    Button btnViewCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plp_act);

        imgProduct1 = findViewById(R.id.imgProduct1);
        imgProduct2 = findViewById(R.id.imgProduct2);
        tvProduct1 = findViewById(R.id.tvProduct1);
        tvProduct2 = findViewById(R.id.tvProduct2);
        btnViewCart = findViewById(R.id.btnViewCart);

        View.OnClickListener openDetail1 = v -> openProductDetail("Handbag", "Stylish leather handbag with adjustable strap and secure zipper.", R.drawable.bag);
        View.OnClickListener openDetail2 = v -> openProductDetail("Shoes", "Comfortable and stylish shoes, perfect for daily wear. Available in all sizes.", R.drawable.nikeshoes);

        imgProduct1.setOnClickListener(openDetail1);
        tvProduct1.setOnClickListener(openDetail1);

        imgProduct2.setOnClickListener(openDetail2);
        tvProduct2.setOnClickListener(openDetail2);

        btnViewCart.setOnClickListener(v -> {
            Intent intent = new Intent(ProductListingPage.this, CartPage.class);
            startActivity(intent);
        });
    }

    void openProductDetail(String name, String description, int imageId) {
        Intent intent = new Intent(ProductListingPage.this, ProductDetailsPage.class);
        intent.putExtra("name", name);
        intent.putExtra("description", description);
        intent.putExtra("imageId", imageId);
        startActivity(intent);
    }
}
