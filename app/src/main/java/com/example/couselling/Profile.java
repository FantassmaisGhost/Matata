package com.example.couselling;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.TaskStackBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class Profile extends AppCompatActivity {
    EditText Username = findViewById(R.id.Name);
    EditText Email = findViewById(R.id.Email);
    String name, email;
    String serverResponse;
    LinearLayout l;
    TextView t;
    TextView t2;

    LinearLayout l2;
    String url2 = "https://lamp.ms.wits.ac.za/home/s2651487/getCounsellor.php";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prof);
        l=findViewById(R.id.chat_layout);
        t=findViewById(R.id.profilename);
        t2=findViewById(R.id.profileemail);
        l2=findViewById(R.id.settings_layout);
        name = String.valueOf(Username.getText());
        email = String.valueOf(Email.getText());
        OkHttpClient client = new OkHttpClient();
        RequestBody postBody = new FormBody.Builder()
                .add("name", name)
                .add("email", email)
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url2)
                .post(postBody)
                .build();
        Call call = client.newCall(request);
        okhttp3.Response Resp = null;
        try {
            Resp = call.execute();
            serverResponse = Resp.body().string();
            //will use this to put into profile
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    processJSON(serverResponse);
                }
            });

        } catch (IOException e) {

        }



//        l2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Perform your action here
//                Intent intent = new Intent(Profile.this, Settings.class);
//                startActivity(intent);
//            }
//        });
//        l.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Perform your action here
//                Intent intent = new Intent(Profile.this, chatActivity.class);
//                startActivity(intent);
//            }
//        });

    }
    public void processJSON (String json){
        try {
            JSONArray all = new JSONArray(json);
            for (int i = 0; i < all.length(); i++) {
                JSONObject item = all.getJSONObject(i);
                String Name = item.getString("name");
                String E_mail = item.getString("email");
                t.setText(Name);
                t2.setText(E_mail);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    };
}
