package com.example.couselling;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class client_Profile extends AppCompatActivity {
    TextView textView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_profile);
        LinearLayout l= findViewById(R.id.chat_layout);
        TextView help= findViewById(R.id.help);
        textView = findViewById(R.id.profileemailclient);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String email = sharedPreferences.getString("EMAIL", "");
        textView.setText(email);

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(client_Profile.this,ListCounsellor.class);
                startActivity(i);

            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(client_Profile.this, NewSelectApp.class);
                startActivity(i);
            }
        });
    }
}
