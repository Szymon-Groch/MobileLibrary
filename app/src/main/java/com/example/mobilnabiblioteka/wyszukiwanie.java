package com.example.mobilnabiblioteka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class wyszukiwanie extends AppCompatActivity {
    OkHttpClient client_wyszukiwanie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wyszukiwanie);

        Button szukaj_tytul = (Button) findViewById(R.id.bt_tytul_szukaj);
        Button szukaj_gatunek = (Button) findViewById(R.id.bt_szukaj_gatunek);

        Button powrot = (Button) findViewById(R.id.bt_powrot2);

        EditText tytul = (EditText) findViewById(R.id.txt_tytul2);
        Spinner gatunek = (Spinner) findViewById(R.id.sp_gatunek);
        ListView Wyszukane_ksiazki = (ListView) findViewById(R.id.znalezione_ksiazki);

        client_wyszukiwanie = new OkHttpClient();
        String url =  "http://10.0.2.2/SpinnerGatunek.php";
        //zapełnienie spinnera
        Request request2 = new Request.Builder().url(url).build();
        client_wyszukiwanie.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    wyszukiwanie.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //id.setText(myResponse); /*try { JSONObject jsonObject = new JSONObject(myResponse); tekst.setText(jsonObject.getString("a")); }catch (Exception err){ }*/
                            try { //JSONObject jsonObject = new JSONObject(myResponse);
                                ArrayList<String> gatunki = new ArrayList<>();
                                JSONArray jsonarray = new JSONArray(myResponse);
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    gatunki.add(jsonobject.getString("nazwa"));
                                }
                                gatunek.setAdapter(new ArrayAdapter<String>(wyszukiwanie.this,
                                        android.R.layout.simple_spinner_dropdown_item, gatunki));
                                //tekst.setText(jsonarray.getString(1));
                                //tekst.setText(myResponse);

                            } catch (Exception err) {
                                Toast.makeText(getApplicationContext(), err.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        szukaj_tytul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Tytul = tytul.getText()+"";

                String url = "http://10.0.2.2/B_Wyszukiwanie_tytul.php?dane1="+Tytul;
                Request request3 = new Request.Builder().url(url).build();
                client_wyszukiwanie.newCall(request3).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            final String myResponse = response.body().string();
                            wyszukiwanie.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try { //JSONObject jsonObject = new JSONObject(myResponse);
                                        ArrayList<String> ksiazki = new ArrayList<>();
                                        JSONArray jsonarray = new JSONArray(myResponse);
                                        for (int i = 0; i < jsonarray.length(); i++) {
                                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                                            ksiazki.add("'"+jsonobject.getString("tytul")+"' autorstwa: "+jsonobject.getString("autor")+" gatunku "+jsonobject.getString("gatunek_nazwa"));
                                        }
                                        Wyszukane_ksiazki.setAdapter(new ArrayAdapter<String>(wyszukiwanie.this,
                                                android.R.layout.simple_expandable_list_item_1, ksiazki));
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
        szukaj_gatunek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Gatunek = gatunek.getSelectedItem().toString();

                String url = "http://10.0.2.2/B_Wyszukiwanie_gatunek.php?dane1="+Gatunek;
                Request request3 = new Request.Builder().url(url).build();
                client_wyszukiwanie.newCall(request3).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            final String myResponse = response.body().string();
                            wyszukiwanie.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try { //JSONObject jsonObject = new JSONObject(myResponse);
                                        ArrayList<String> ksiazki = new ArrayList<>();
                                        JSONArray jsonarray = new JSONArray(myResponse);
                                        for (int i = 0; i < jsonarray.length(); i++) {
                                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                                            ksiazki.add("'"+jsonobject.getString("tytul")+"' gatunku "+jsonobject.getString("gatunek_nazwa")+"' autorstwa: "+jsonobject.getString("autor"));
                                        }
                                        Wyszukane_ksiazki.setAdapter(new ArrayAdapter<String>(wyszukiwanie.this,
                                                android.R.layout.simple_expandable_list_item_1, ksiazki));
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
                Intent i = new Intent(wyszukiwanie.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}