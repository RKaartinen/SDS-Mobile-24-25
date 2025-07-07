package com.lutstudent.quicklauncher;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

        // Attempts to launch an activity within our own app
        Button secondActivityBtn = findViewById(R.id.secondActivityBtn);
        secondActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent = new Intent(getApplicationContext(), SecondActivity.class);
                startIntent.putExtra("com.lutstudent.quicklauncher.SOMETHING", "HELLO WORLD!");
                startActivity(startIntent);
            }
        });

        // Attempt to launch an activity outside our app
        Button googleBtn = findViewById(R.id.googleBtn);
        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String google = "http://www.google.com";
                Uri webAddress = Uri.parse(google);

                Intent goToGoogle = new Intent(Intent.ACTION_VIEW, webAddress);
                if (goToGoogle.resolveActivity(getPackageManager()) != null) {
                    startActivity(goToGoogle);
                } else {
                    Toast.makeText(MainActivity.this, "Ei selainta!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}