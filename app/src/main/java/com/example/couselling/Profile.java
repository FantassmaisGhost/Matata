package com.example.couselling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.TaskStackBuilder;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class Profile extends AppCompatActivity {
    //EditText Username = findViewById(R.id.Name);
    //EditText Email = findViewById(R.id.Email);
    String name;
    String serverResponse;
    LinearLayout l;
    TextView t;
    TextView t2;

    LinearLayout l2;
    //String url2 = "https://lamp.ms.wits.ac.za/home/s2651487/getCounsellor.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prof);
        t = findViewById(R.id.profileemail);
        t2 = findViewById(R.id.profilename);
        see();
    }
    public void see(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String email = sharedPreferences.getString("EMAIL", "");
        t.setText(email);
        String shit = String.valueOf(t.getText());
        work(shit);

    }// end of see
    public void work(final String shit) {
        String url2 = "https://lamp.ms.wits.ac.za/home/s2651487/getCounsellor.php";

        // Create a request queue
        RequestQueue queue = Volley.newRequestQueue(this);

        // Create a string request to post the selected word
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(Profile.this, "Error posting selected word: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("Email", shit);
                return params;
            }
        };

        // Add the request to the RequestQueue
        queue.add(stringRequest);
    }
    private void handleResponse(String response) {
        // Set the name to a TextView
        t2.setText(response.trim());
    }
}// end of class