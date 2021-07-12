package com.example.fitnessapp.chat;

import static com.example.fitnessapp.auth.Authentication.getToken;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fitnessapp.R;
import com.example.fitnessapp.api.ApiUtilities;
import com.example.fitnessapp.api.UserData;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRoom extends AppCompatActivity {

  private Socket mSocket;
  private String name = "Has Not Changed";
  private String roomName;
  private Boolean isConnected = true;

  {
    try {
      //      mSocket = IO.socket("https://fitness-application-api.herokuapp.com");
      mSocket = IO.socket("http://172.26.97.109:5000");

    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat_room);
    roomName = getIntent().getStringExtra("roomName");
    TextView chatTitle = findViewById(R.id.chat_title);
    chatTitle.setText(roomName);
    mSocket.on(Socket.EVENT_CONNECT, onConnect);
    mSocket.on("newUserToChatRoom", onNewUser);
    mSocket.on("updateChat", onUpdateChat);
    mSocket.on("userLeftChatRoom", onUserLeft);
    mSocket.connect();
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
                                try {
                                  String jsonString =
                                      "{ name: '" + name + "', roomName: '" + roomName + "' }";
                                  JSONObject jsonData = new JSONObject(jsonString);
                                  mSocket.emit("subscribe", jsonData);
                                } catch (JSONException e) {
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
        public void call(Object... args) {}
      };

  private Emitter.Listener onUpdateChat =
      new Emitter.Listener() {
        @Override
        public void call(Object... args) {}
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
