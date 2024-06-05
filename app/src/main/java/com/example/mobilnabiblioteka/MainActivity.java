package com.example.mobilnabiblioteka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.*;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Array;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button zerezerwuj = (Button) findViewById(R.id.bt_zarezerwuj);
        Button wyszukaj = (Button) findViewById(R.id.bt_wyszukaj);

        wyszukaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, wyszukiwanie.class);
                startActivity(i);
            }
        });
        zerezerwuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, rezerwacje.class);
                startActivity(i);
            }
        });
    }
}