package com.example.couselling;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

public class NewSelectApp extends AppCompatActivity {
    List<String> items = new ArrayList<>(Arrays.asList(
            "Depression",
            "Anxiety",
            "Relationship Issues",
            "Stress Management",
            "Grief and Loss",
            "Trauma",
            "Anger Management",
            "Self-Esteem Issues",
            "Addiction Recovery",
            "Family Conflict",
            "Workplace Issues",
            "Eating Disorders",
            "Identity Issues",
            "PTSD (Post-Traumatic Stress Disorder)",
            "Phobias",
            "Sleep Disorders",
            "Parenting Challenges",
            "Chronic Illness Management",
            "Financial Stress",
            "Loneliness and Isolation",
            "Cultural Adjustment",
            "Life Transitions (e.g., divorce, retirement)",
            "Sexual Issues",
            "Communication Problems",
            "Assertiveness Training",
            "Academic or Career Counseling",
            "Emotional Regulation",
            "Borderline Personality Disorder (BPD) Management",
            "Obsessive-Compulsive Disorder (OCD) Treatment",
            "Bipolar Disorder Management",
            "Schizophrenia Management",
            "Personality Disorders",
            "Suicidal Thoughts or Behaviors"
            // Add more items as needed
    ));
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    ArrayAdapter<String> adapterOriginalItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myselect);

        autoCompleteTextView = findViewById(R.id.auto_complete);
        adapterOriginalItems = new ArrayAdapter<>(this, R.layout.list_item, items);
        adapterItems = new ArrayAdapter<>(this, R.layout.list_item);

        // Set the adapter with original items initially
        autoCompleteTextView.setAdapter(adapterOriginalItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedWord = adapterView.getItemAtPosition(position).toString();
                postSelectedWord(selectedWord);
            }
        });
    }

    public void postSelectedWord(final String selectedWord) {
        String url = "https://lamp.ms.wits.ac.za/home/s2651487/Counsellor_pick.php";

        // Create a request queue
        RequestQueue queue = Volley.newRequestQueue(this);

        // Create a string request to post the selected word
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response
                        handleResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(NewSelectApp.this, "Error posting selected word: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Add parameters (selected word) to the request
                params.put("Type", selectedWord);
                return params;
            }
        };

        // Add the request to the RequestQueue
        queue.add(stringRequest);
    }

    private void handleResponse(String response) {

        String[] names = response.split("\n");

        adapterItems.clear();
        adapterItems.addAll(Arrays.asList(names));
        autoCompleteTextView.setAdapter(adapterItems);
    }
}
