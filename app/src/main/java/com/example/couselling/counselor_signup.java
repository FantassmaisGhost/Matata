package com.example.couselling;

import static com.example.couselling.R.layout.prof;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

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
import java.util.HashMap;
import java.util.Map;


import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


public class counselor_signup extends AppCompatActivity {
    EditText Username, Email, Pass, EPass, type;
    Button Sign;
    TextView Error;
    String name, email, password, word, typeofcounsel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counsellor_sign_up);
        Username = findViewById(R.id.Name);
        Email = findViewById(R.id.Email);
        type = findViewById(R.id.Type);
        Pass = findViewById(R.id.Password);
        EPass = findViewById(R.id.confirm);
        Sign = findViewById(R.id.SignUp);
        // Error = findViewById(R.id.status);


        TextView textView = findViewById(R.id.singUpClick);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform your action here
                Intent intent = new Intent(counselor_signup.this, Counellor_LogIn.class);
                startActivity(intent);
            }
        });

        Sign.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Error.setVisibility(View.GONE);
                name = String.valueOf(Username.getText());
                email = String.valueOf(Email.getText());
                typeofcounsel = String.valueOf(type.getText());
                password = String.valueOf(Pass.getText());
                word = String.valueOf(EPass.getText());
                //SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                //SharedPreferences.Editor editor = sharedPreferences.edit();
                //editor.putString("NAME", name);
                //editor.putString("EMAIL", email);
                //editor.apply();
                RequestQueue queue = Volley.newRequestQueue(counselor_signup.this);
                String url = "https://lamp.ms.wits.ac.za/home/s2651487/Counsellor_Sign_Up.php";



                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (password.equals(word)) {
                                    if (response.equals("success")) {
                                        Toast.makeText(counselor_signup.this, "SignUp successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(counselor_signup.this, Counellor_LogIn.class);
                                        //SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                        //SharedPreferences.Editor editor = sharedPreferences.edit();
                                        //editor.putString("NAME", name);
                                        //editor.putString("EMAIL", email);
                                        //editor.apply();
                                        startActivity(intent);
                                        finish();

                                    } else if (response.equals("Information")){
                                        Toast.makeText(counselor_signup.this, "Information is already taken!!", Toast.LENGTH_SHORT).show();
                                    }// end of else
                                    else {
                                        Toast.makeText(counselor_signup.this, "Check Information you gave me", Toast.LENGTH_SHORT).show();

                                    }
                                }  // end of if
                                else {
                                    Toast.makeText(counselor_signup.this, "Passwords Do Not Match", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }


                }) {
                    protected Map<String, String> getParams() {
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("FullName", name);
                        paramV.put("Email", email);
                        paramV.put("Type", typeofcounsel);
                        paramV.put("Password", password);
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }


        });

    }

}
