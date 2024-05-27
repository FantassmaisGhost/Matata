package com.example.couselling;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class mainchat extends AppCompatActivity {

        private WebSocket webSocket;
        private MessageAdapter adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.main);
            ListView messageList = findViewById(R.id.messageList);
            EditText messageBox = findViewById(R.id.messageBox);
            TextView send = findViewById(R.id.send);

            instantiateWebSocket();

            adapter = new MessageAdapter();
            messageList.setAdapter(adapter);
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String message = messageBox.getText().toString();
                    if(!message.isEmpty()){
                        webSocket.send(message);
                        messageBox.setText("");
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("messsage",message);
                            jsonObject.put("bySever",false);
                            adapter.addItem(jsonObject);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });

        }

        private void instantiateWebSocket() {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("ws:// 146.141.21.92:8080").build();
            com.example.couselling.mainchat.SocketListener socketListener = new com.example.couselling.mainchat.SocketListener(this);
            webSocket = client.newWebSocket(request, socketListener);
        }

        public class SocketListener extends WebSocketListener {
            public com.example.couselling.mainchat activity;

            public SocketListener(com.example.couselling.mainchat activity) {
                this.activity = activity;
            }

            @Override
            public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
                super.onFailure(webSocket, t, response);
            }

            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
                super.onMessage(webSocket, text);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("message", text);
                            jsonObject.put("byServer", true);
                            adapter.addItem(jsonObject);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });


            }

            @Override
            public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                super.onClosing(webSocket, code, reason);
            }

            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull ByteString bytes) {
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                super.onOpen(webSocket, response);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, "Connection Established!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

            public class MessageAdapter extends BaseAdapter {
                List<JSONObject> messagesList = new ArrayList<>();

                @Override
                public int getCount() {
                    return messagesList.size();
                }

                @Override
                public Object getItem(int position) {
                    return messagesList.get(position);
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    if (convertView == null)
                        convertView = getLayoutInflater().inflate(R.layout.message_list_item, parent, false);
                    TextView sentMessage = convertView.findViewById(R.id.sentMessage);
                    TextView receivedMessage = convertView.findViewById(R.id.receivedMessage);
                    JSONObject item = messagesList.get(position);
                    try {
                        if (item.getBoolean("byServer")) {
                            receivedMessage.setVisibility(View.VISIBLE);
                            receivedMessage.setText(item.getString("message"));
                            sentMessage.setVisibility(View.INVISIBLE);
                        } else {
                            sentMessage.setVisibility(View.VISIBLE);
                            sentMessage.setText(item.getString("message"));
                            receivedMessage.setVisibility(View.INVISIBLE);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    return convertView;
                }

                void addItem(JSONObject item) {
                    messagesList.add(item);
                    notifyDataSetChanged();
                }
            }
        }








