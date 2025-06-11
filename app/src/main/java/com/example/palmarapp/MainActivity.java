package com.example.palmarapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Animation top, bottom;
    private TextView letterM, letterY, letterS, letterC, letterH, letterO, letterO2, letterL, letterA, letterP, letterP2;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        top = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottom = AnimationUtils.loadAnimation(this, R.anim.buttom_animation);


        img = findViewById(R.id.img);
        letterM = findViewById(R.id.letterM);
        letterY = findViewById(R.id.letterY);
        letterS = findViewById(R.id.letterS);
        letterC = findViewById(R.id.letterC);
        letterH = findViewById(R.id.letterH);
        letterO = findViewById(R.id.letterO);
        letterO2 = findViewById(R.id.letterO2);
        letterL = findViewById(R.id.letterL);
        letterA = findViewById(R.id.letterA);
        letterP = findViewById(R.id.letterP);
        letterP2 = findViewById(R.id.letterP2);


        img.startAnimation(top);


        new Handler().postDelayed(() -> letterP2.startAnimation(bottom), 1600);
        new Handler().postDelayed(() -> letterP.startAnimation(bottom), 1800);
        new Handler().postDelayed(() -> letterA.startAnimation(bottom), 2000);
        new Handler().postDelayed(() -> letterL.startAnimation(bottom), 2200);
        new Handler().postDelayed(() -> letterO2.startAnimation(bottom), 2400);
        new Handler().postDelayed(() -> letterO.startAnimation(bottom), 2600);
        new Handler().postDelayed(() -> letterH.startAnimation(bottom), 2800);
        new Handler().postDelayed(() -> letterC.startAnimation(bottom), 3000);
        new Handler().postDelayed(() -> letterS.startAnimation(bottom), 3200);
        new Handler().postDelayed(() -> letterY.startAnimation(bottom), 3400);
        new Handler().postDelayed(() -> letterM.startAnimation(bottom), 3700);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}
