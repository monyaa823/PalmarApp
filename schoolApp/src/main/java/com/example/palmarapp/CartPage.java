package com.example.palmarapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class CartPage extends AppCompatActivity {

    LinearLayout cartItemsLayout;
    Button btnCheckout;
    static ArrayList<String> cartItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_act);

        cartItemsLayout = findViewById(R.id.cartItemsLayout);
        btnCheckout = findViewById(R.id.btnCheckout);

        displayCartItems();

        btnCheckout.setOnClickListener(v -> {
            Toast.makeText(this, "Purchase Successful!", Toast.LENGTH_SHORT).show();
            cartItems.clear();
            finish();
        });
    }

    void displayCartItems() {
        cartItemsLayout.removeAllViews();
        for (String item : cartItems) {
            TextView itemText = new TextView(this);
            itemText.setText(item);
            itemText.setTextSize(18);
            itemText.setPadding(10, 10, 10, 10);
            cartItemsLayout.addView(itemText);
        }
    }

    public static void addToCart(String productName) {
        cartItems.add(productName);
    }
}

