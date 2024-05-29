package com.example.couselling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class editAbout extends AppCompatActivity {
    EditText about;
    //TextView showAbout;
    String ed_About;
    //TextView nothing;
    Button saveAbout;
    String email;
    String id;
    String aboutInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
       // nothing=new TextView(this);
        about = findViewById(R.id.typeAbout);
        saveAbout = findViewById(R.id.saveAbout);

        Intent i= getIntent();
        if(i!=null){
            email= i.getStringExtra("Email");
        }

        ed_About= about.getText().toString();

        //sendAbout(id,ed_About);
        saveAbout.setOnClickListener(new View.OnClickListener() {
              @Override
            public void onClick(View v) {
                  if(ed_About.isEmpty()){
                      Toast.makeText(editAbout.this, "Please enter something",Toast.LENGTH_SHORT).show();
                  }else{
                      getID(email, ed_About);
                      Intent i= new Intent(editAbout.this, Profile.class);
                      startActivity(i);
                  }

            }
      });


        //see();
    }
//    public void see(){
//        SharedPreferences sharedPreferences= getSharedPreferences()
//        //SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//        //String email = sharedPreferences.getString("EMAIL", "");
//        //String email2 = String.valueOf(t.getText());
//        //sendAbout(email, ed_About);
//        saveAbout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                save(email);
//            }
//        });
//    }
    OkHttpClient client= new OkHttpClient();
    public void getID(String email,String about){
        String url="https://lamp.ms.wits.ac.za/s2651487/getCounsellorbyEmail.php";
        RequestBody formBody= new FormBody.Builder()
                .add("Email",email)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                runOnUiThread(()-> Toast.makeText(editAbout.this, "Failed to get ID"+e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    try {
                        String responseData = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseData);
                        if (jsonObject.has("C_ID")) {
                            id = jsonObject.getString("C_ID");
                            sendAbout(id,about);
                        }
                        else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(editAbout.this,"User does not exit",Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                    }catch(JSONException e){
                        e.printStackTrace();

                    }

                }

            }
        });


    }
    public void save(String id){
        //getID(email,ed_About);
        RequestBody formBody= new FormBody.Builder()
                .add("C_ID", id)
                .build();
        Request request= new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/s2651487/getAbout.php")
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(editAbout.this, "Failed to get about info: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                        String responseStr = response.body().string();
                        processJSON(responseStr);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("AboutInfo", aboutInfo);
                                setResult(RESULT_OK, resultIntent);
                                finish();

                            }
                        });
                }

            }
        });


    }

    public void sendAbout(String id,String about){
        String url ="https://lamp.ms.wits.ac.za/s2651487/About.php";
        RequestBody formBody= new FormBody.Builder()
                .add("C_ID",id)
                .add("About",about)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           Toast.makeText(editAbout.this,"About info saved successfully",Toast.LENGTH_SHORT).show();
                           save(id);

                       }
                   });

                }else{
                    runOnUiThread(() -> Toast.makeText(editAbout.this, "Failed to send about info", Toast.LENGTH_SHORT).show());
                }

            }
        });


    }



    public void processJSON(String json){
        try {
            JSONObject item=new JSONObject(json);
            if (item.has("About")) {
                aboutInfo = item.getString("About");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }





}
