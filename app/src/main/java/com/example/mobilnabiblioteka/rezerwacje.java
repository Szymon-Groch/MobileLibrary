package com.example.mobilnabiblioteka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class rezerwacje extends AppCompatActivity {
    OkHttpClient client_rezerwacje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezerwacje);

        Button powrot = (Button) findViewById(R.id.bt_powrot2);
        Button zerezerwuj = (Button) findViewById(R.id.bt_zerezerwuj);

        EditText tytul = (EditText) findViewById(R.id.txt_tytul);
        EditText rezerwujacy = (EditText) findViewById(R.id.txt_zamawiajacy);

        client_rezerwacje = new OkHttpClient();

        zerezerwuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Rezerwujacy = rezerwujacy.getText()+"";
                String Tytul = tytul.getText()+"";


                String url = "http://10.0.2.2/Zarezerwuj.php?dane1="+Tytul+"&dane2="+Rezerwujacy;
                Request request = new Request.Builder().url(url).build();
                client_rezerwacje.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            final String myResponse = response.body().string();
                            rezerwacje.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //id.setText(myResponse); /*try { JSONObject jsonObject = new JSONObject(myResponse); tekst.setText(jsonObject.getString("a")); }catch (Exception err){ }*/
                                    try { //JSONObject jsonObject = new JSONObject(myResponse);
                                        Toast.makeText(getApplicationContext(), "Pomyślnie zerezerwowano: "+Tytul, Toast.LENGTH_SHORT).show();

                                    } catch (Exception err) {
                                        Toast.makeText(getApplicationContext(), err.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
        powrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(rezerwacje.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
}