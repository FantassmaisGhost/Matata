package com.example.couselling;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class counselor_signup extends AppCompatActivity {
    EditText Username, Email, Pass, EPass,type;
    Button Sign;
    TextView Error;
    String name,email,password,word,typeofcounsel;
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

        Sign.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Error.setVisibility(View.GONE);
                name = String.valueOf(Username.getText());
                email = String.valueOf(Email.getText());
                typeofcounsel = String.valueOf(type.getText());
                password = String.valueOf(Pass.getText());
                word = String.valueOf(EPass.getText());
                RequestQueue queue = Volley.newRequestQueue(counselor_signup.this);
                String url = "https://lamp.ms.wits.ac.za/home/s2651487/Counsellor_Sign_Up.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (password.equals(word)){
                                    if (response.equals("success")) {
                                        Toast.makeText(counselor_signup.this, "SignUp successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(counselor_signup.this, client_logIn.class);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        Toast.makeText(counselor_signup.this, "Check Information You Gave me", Toast.LENGTH_SHORT).show();
                                    }// end of else
                                }  // end of if
                                else {
                                    Toast.makeText(counselor_signup.this, "Passwords Do Not Match", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }


                }){
                    protected Map<String, String> getParams(){
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

