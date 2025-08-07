package com.lutstudent.fangs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button playBtn = findViewById(R.id.playBtn);
        Button achievementsBtn = findViewById(R.id.achievementsBtn);

        SharedPreferences prefs = getSharedPreferences("FangsGameState", MODE_PRIVATE);
        if (savedInstanceState == null) {
            prefs.edit().clear().apply();
        }

        playBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        });

        achievementsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AchievementsActivity.class);
            startActivity(intent);
        });

    }




}