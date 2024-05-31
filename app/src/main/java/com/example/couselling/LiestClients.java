package com.example.couselling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LiestClients extends AppCompatActivity {
    String name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_counsellor);
        TextView personName = findViewById(R.id.Counsellor_name);
        LinearLayout List = findViewById(R.id.list);
        // Inflate the custom view
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.chat_friends, List, false);
        List.addView(customView);
        personName.setText(name);
        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(LiestClients.this, mainchat.class);
                startActivity(i);

            }
        });


    }

}
