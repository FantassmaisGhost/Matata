package com.example.couselling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
    private static final String TAG="editAbout";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
       // nothing=new TextView(this);
        about = findViewById(R.id.typeAbout);
        saveAbout = findViewById(R.id.saveAbout);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        email = sharedPreferences.getString("EMAIL", "");

//        Intent i= getIntent();
//        if(i!=null){
//            email= i.getStringExtra("Email");
//        }



        //sendAbout(id,ed_About);
        saveAbout.setOnClickListener(new View.OnClickListener() {

              @Override
            public void onClick(View v) {
                  ed_About= about.getText().toString();
                  if(ed_About.isEmpty()){
                      Toast.makeText(editAbout.this, "Please enter something",Toast.LENGTH_SHORT).show();
                  }else{
                      getID(email, ed_About);
//
                  }

            }
      });


        //see();
    }
//
    OkHttpClient client= new OkHttpClient();
    public void getID(String email,String about){

        String url="https://lamp.ms.wits.ac.za/home/s2651487/getCounsellorbyEmail.php?Email="+email;
        Log.d(TAG, "getID: email="+email);

        Log.d(TAG, "getID: url"+ url);
        Request request = new Request.Builder()
                .url(url)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                runOnUiThread(()-> Toast.makeText(editAbout.this, "Failed to get ID"+e.getMessage(), Toast.LENGTH_SHORT).show());
                Log.e(TAG, "onFailure: Failed to get ID",e );
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    try {
                        String responseData = response.body().string();
                        Log.d(TAG, "onResponse: Response Data:"+responseData);
                        JSONObject jsonObject = new JSONObject(responseData);
                        if (jsonObject.has("C_ID")) {
                            id = jsonObject.getString("C_ID");
                            Log.d(TAG, "onResponse: Counsellor id:"+id);
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
                        Log.e(TAG, "onResponse: JSON parsing error",e );

                    }

                }

            }
        });


    }
    public void save(String id){
        //getID(email,ed_About);
        Request request= new Request.Builder()
                .url("https://lamp.ms.wits.ac.za/home/s2651487/getAbout.php?C_ID="+id)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
               runOnUiThread(()->Toast.makeText(editAbout.this,"Failed to get about info"+e.getMessage(),Toast.LENGTH_SHORT).show());
                Log.e(TAG, "onFailure: Failed to get about info",e );
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                        String responseStr = response.body().string();
                    Log.d(TAG, "onResponse: responseStr="+responseStr);


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                processJSON(responseStr);
                                Log.d(TAG, "onResponse: Aboutinfo="+aboutInfo);
                                Intent i= new Intent(editAbout.this, Profile.class);
                                i.putExtra("AboutInfo",aboutInfo);
                                startActivity(i);
//
                            }
                        });
                }

            }
        });


    }

    public void sendAbout(String id,String about){
        String url ="https://lamp.ms.wits.ac.za/home/s2651487/About.php";
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
                Log.e(TAG, "onFailure: Failed to send info", e);
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
            JSONArray all=new JSONArray(json);
            for(int i=0;i<all.length();i++){
                JSONObject item=all.getJSONObject(i);
                    aboutInfo = item.getString("About");

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "processJSON: JSON Processing error",e );
        }
    }





}
