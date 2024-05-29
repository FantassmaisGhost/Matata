package com.example.couselling;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Select_Names extends AppCompatActivity {
    ListView nameListView;
    ArrayAdapter<String> adapter;
    List<String> nameList;
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.select_names);
        nameListView = findViewById(R.id.ViewNames);
        nameList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nameList);
        nameListView.setAdapter(adapter);

        Intent intent = getIntent();
        String selectedType = intent.getStringExtra("SELECTED_TYPE");

        fetchNamesFromServer(selectedType);
    }
    public void fetchNamesFromServer(final String selectedType) {
        String url = "https://lamp.ms.wits.ac.za/home/s2651487/Select_Names.php";

        RequestQueue queue = Volley.newRequestQueue(Select_Names.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String[] names = response.split("\n");
                        nameList.clear();
                        nameList.addAll(Arrays.asList(names));
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Select_Names.this, "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Add parameters (selected type) to the request
                params.put("Type", selectedType);
                return params;
            }
        };

        queue.add(stringRequest);
    }

}

