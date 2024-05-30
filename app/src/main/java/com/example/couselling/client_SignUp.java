package com.example.couselling;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class client_SignUp extends AppCompatActivity{
    EditText Username, Email, Pass, EPass;
    Button Sign;
    TextView Error;
    String name,email,password,word;
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.client_sign_up);
        Username = findViewById(R.id.name);
        Email = findViewById(R.id.Email);
        Pass = findViewById(R.id.password);
        EPass = findViewById(R.id.password2);
        Sign = findViewById(R.id.Signup);
        Error = findViewById(R.id.status);
        TextView textView = findViewById(R.id.login);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform your action here
                Intent intent = new Intent(client_SignUp.this, client_logIn.class);
                startActivity(intent);
            }
        });
        Sign.setOnClickListener(new View.OnClickListener(){
       // public void Login(View view){
         //   Intent intent = new Intent(client_SignUp.this, client_logIn.class);
           // startActivity(intent);
        //}
            @Override
            public void onClick(View v) {
                //Error.setVisibility(View.GONE);
                name = String.valueOf(Username.getText());
                email = String.valueOf(Email.getText());
                password = String.valueOf(Pass.getText());
                word = String.valueOf(EPass.getText());
                RequestQueue queue = Volley.newRequestQueue(client_SignUp.this);
                String url = "https://lamp.ms.wits.ac.za/home/s2651487/Sign_Up.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                  if (password.equals(word)){
                                      if (response.equals("success")) {
                                          Toast.makeText(client_SignUp.this, "SignUp successful", Toast.LENGTH_SHORT).show();
                                          Intent intent = new Intent(client_SignUp.this, client_logIn.class);
                                          startActivity(intent);
                                          //finish();

                                      } else if (response.equals("Information")){
                                          Toast.makeText(client_SignUp.this, "Information is already taken!!!", Toast.LENGTH_SHORT).show();
                                      }// end of else
                                      else {
                                          Toast.makeText(client_SignUp.this, "Check Information you gave me", Toast.LENGTH_SHORT).show();
                                      }
                                  }  // end of if
                                else {
                                    Error.setText("Passwords Do Not Match!!!");
                                  }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }


            }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("Name", name);
                        paramV.put("Email", email);
                        paramV.put("Password", password);
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });



    }


}// end of class