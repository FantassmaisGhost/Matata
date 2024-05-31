package com.example.couselling;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.example.couselling.activity.chatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class client_logIn extends AppCompatActivity {
 EditText Email,Pass;
 Button LogIn;
    String email,password,name;
    TextView t;
    SharedPreferences shared;
    // String url = "sharedhttps://lamp.ms.wits.ac.za/home/s2651487/Client_LogIn.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_login);
        Email = findViewById(R.id.Email);
        Pass = findViewById(R.id.Password);
        LogIn = findViewById(R.id.logInhere);
        t=findViewById(R.id.logInClick);

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform your action here
                Intent intent = new Intent(client_logIn.this, client_SignUp.class);
                startActivity(intent);
            }
        });
        //shared = getSharedPreferences("MyAppName", MODE_PRIVATE);

       // if (shared.getString("logged","false").equals("true")){
         //   Intent intent = new Intent(client_logIn.this,MainActivity.class);
           // startActivity(intent);
            //finish();
       // }

        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = String.valueOf(Email.getText());
                password = String.valueOf(Pass.getText());
                //Toast.makeText(client_logIn.this, "SignUp successful", Toast.LENGTH_SHORT).show();
                RequestQueue queue = Volley.newRequestQueue(client_logIn.this);
                String url = "https://lamp.ms.wits.ac.za/home/s2651487/Client_LogIn.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                    if (response.equals("success")) {
                                        Toast.makeText(client_logIn.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(client_logIn.this, client_Profile.class);
                                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("EMAIL", email);
                                        editor.apply();
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        Toast.makeText(client_logIn.this, "Check Information You Gave me", Toast.LENGTH_SHORT).show();
                                    }// end of else

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }


                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("Email", email);
                        paramV.put("Password", password);
                        return paramV;
                    }
                };
                queue.add(stringRequest);


            }
        });// end of OnClick

    }



}

