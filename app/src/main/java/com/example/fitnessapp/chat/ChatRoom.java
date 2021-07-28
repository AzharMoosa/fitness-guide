package com.example.fitnessapp.chat;

import static com.example.fitnessapp.auth.Authentication.getToken;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fitnessapp.R;
import com.example.fitnessapp.api.ApiUtilities;
import com.example.fitnessapp.api.settings_data.ChatInfo;
import com.example.fitnessapp.api.settings_data.SettingsData;
import com.example.fitnessapp.api.auth_data.UserData;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRoom extends AppCompatActivity {

  private Socket mSocket;
  private String name = "";
  private String roomName;
  private Boolean isConnected = true;
  private List<Message> messageList;
  private RecyclerView recyclerView;
  private ChatRoomAdapter chatRoomAdapter;
  private EditText message;
  private ImageButton send;
  private String settingsID;
  private ChatInfo info;

  {
    try {
      mSocket = IO.socket("https://fitness-application-api.herokuapp.com");
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    setContentView(R.layout.activity_chat_room);
    getChatSettings();
  }

  private void initServer() {
    roomName = getIntent().getStringExtra("roomName");
    TextView chatTitle = findViewById(R.id.chat_title);
    chatTitle.setText(roomName);

    messageList = new ArrayList<>();
    recyclerView = findViewById(R.id.message_list);
    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
    recyclerView.setLayoutManager(mLayoutManager);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    chatRoomAdapter = new ChatRoomAdapter(messageList);
    chatRoomAdapter.notifyDataSetChanged();
    recyclerView.setAdapter(chatRoomAdapter);

    send = findViewById(R.id.send_msg);

    send.setOnClickListener(
        new OnClickListener() {
          @Override
          public void onClick(View v) {
            if (!message.getText().toString().isEmpty()) {
              String jsonString =
                  "{ messageContent: '"
                      + message.getText().toString()
                      + "', roomName: '"
                      + roomName
                      + "' }";
              try {
                JSONObject jsonData = new JSONObject(jsonString);
                mSocket.emit("newMessage", jsonData);
              } catch (JSONException e) {
                Toast.makeText(getApplicationContext(), "Error: Sending Message", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
              }
              message.setText(" ");
            }
          }
        });

    message = findViewById(R.id.text_message);
    mSocket.connect();

    mSocket.on(Socket.EVENT_CONNECT, onConnect);
    mSocket.on("newUserToChatRoom", onNewUser);
    mSocket.on("updateChat", onUpdateChat);
    mSocket.on("userLeftChatRoom", onUserLeft);
  }

  private void getChatSettings() {
    ApiUtilities.getApiInterface()
        .getSettings(getToken(this))
        .enqueue(
            new Callback<SettingsData>() {
              @Override
              public void onResponse(Call<SettingsData> call, Response<SettingsData> response) {
                if (response.body() != null) {
                  settingsID = response.body().getId();
                  info = response.body().getChatSettings();
                  initServer();
                }
              }

              @Override
              public void onFailure(Call<SettingsData> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error Connecting To Server", Toast.LENGTH_SHORT).show();
              }
            });
  }

  private Emitter.Listener onConnect =
      new Emitter.Listener() {
        @Override
        public void call(Object... args) {
          runOnUiThread(
              new Runnable() {
                @Override
                public void run() {
                  ApiUtilities.getApiInterface()
                      .getUserData(getToken(ChatRoom.this))
                      .enqueue(
                          new Callback<UserData>() {
                            @Override
                            public void onResponse(
                                Call<UserData> call, Response<UserData> response) {
                              if (response.body() != null) {
                                name = response.body().getName();
                                if (!info.getCustomName().equals("")) {
                                  name = info.getCustomName();
                                }
                                if (!info.getIsVisible()) {
                                  name = "Anonymous";
                                }
                                try {
                                  String jsonString =
                                      "{ name: '" + name + "', roomName: '" + roomName + "' }";
                                  JSONObject jsonData = new JSONObject(jsonString);
                                  mSocket.emit("subscribe", jsonData);
                                } catch (JSONException e) {
                                  Toast.makeText(getApplicationContext(), "Cannot Connect To Server", Toast.LENGTH_SHORT).show();
                                  e.printStackTrace();
                                }
                              }
                            }

                            @Override
                            public void onFailure(Call<UserData> call, Throwable t) {
                              Log.e("Error", t.getMessage());
                            }
                          });
                }
              });
        }
      };

  private Emitter.Listener onNewUser =
      new Emitter.Listener() {
        @Override
        public void call(Object... args) {
          runOnUiThread(
              new Runnable() {
                @Override
                public void run() {
                  Toast.makeText(
                          ChatRoom.this,
                          args[0].toString() + " has joined the chat",
                          Toast.LENGTH_SHORT)
                      .show();
                }
              });
        }
      };

  private Emitter.Listener onUpdateChat =
      new Emitter.Listener() {
        @Override
        public void call(Object... args) {
          runOnUiThread(
              new Runnable() {
                @Override
                public void run() {
                  JSONObject data = (JSONObject) args[0];
                  try {
                    String username = data.getString("name");
                    String messageContent = data.getString("messageContent");
                    Message newMessage = new Message(username, messageContent);
                    messageList.add(newMessage);
                    chatRoomAdapter.notifyItemInserted(messageList.size());
                    recyclerView.scrollToPosition(messageList.size() - 1);
                  } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Cannot Connect To Server", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                  }
                }
              });
        }
      };

  private Emitter.Listener onUserLeft =
      new Emitter.Listener() {
        @Override
        public void call(Object... args) {}
      };

  @Override
  public void onDestroy() {
    mSocket.disconnect();
    super.onDestroy();
  }
}
