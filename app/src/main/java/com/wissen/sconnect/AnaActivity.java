package com.wissen.sconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import de.hdodenhof.circleimageview.CircleImageView;

public class AnaActivity extends AppCompatActivity {

    EditText et1, et2;
    static String serverIp,userNick;

    SharedPreferences sp;
    SharedPreferences.Editor edit;

    CircleImageView civ;
    TextView tvAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        edit = sp.edit();

        et1 = (EditText) findViewById(R.id.editText);
        et2 = (EditText) findViewById(R.id.editText2);

        civ = (CircleImageView) findViewById(R.id.avatar);
        tvAd= (TextView) findViewById(R.id.tvAd);

        userNick = sp.getString("userNick", "");
        if(userNick != "")
        {
            setTitle(userNick);
        }

        serverIp = ServerIP.serverIp;

        getSupportActionBar().setTitle("");
        getSupportActionBar().setSubtitle("Hoşgeldiniz");
    }

    public void sendMessage(View v) {

        if (false) {
            try {
                String adr = "https://gcm-http.googleapis.com/gcm/send";
                String registrationId = getReg();

                String body = "{ " +
                        " \"to\" : \"" + registrationId + "\","
                        + "\"data\" : { " + "\"op\" : " + "\"" + et1.getText().toString() + "\","
                        + "\"val\" : " + "\"" + et1.getText().toString() + "\""
                        + " }"
                        + "}";

                URL url = new URL(adr);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Length", body.length() + "");
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Authorization", "key=" + "AIzaSyAnxPC_gCnoM-2MazKkJ0KzFGh_JNMjM-g");
                con.setDoInput(true);
                con.setDoOutput(true);

                PrintWriter yaz = new PrintWriter(con.getOutputStream(), true);
                yaz.println(body);
                int responseCode = con.getResponseCode();

                // request gider, response gelir, gelen response (code) ->> 200 = HTTP_OK anlamına gelir.
                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getReg() {
        String cihazBilgisi = Build.MANUFACTURER + " - " + Build.MODEL + " - " + Build.SERIAL;
        String str = "";
        BufferedReader in = null;
        try {
            String adr = "http://\" + serverIp + \":8080/KullaniciKayitService/inventory/kullanici/getregid";
            URL url = new URL(adr);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setRequestProperty("Content-Type", "application/json");

            BufferedWriter outwrite = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));

            outwrite.append("{ "
                    + "\"cihazBilgisi\" : \"" + cihazBilgisi + "\""
                    + " }");

            outwrite.flush();
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            return in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Anasayfa").setIcon(android.R.drawable.ic_menu_search).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add("Bildirimler").setIcon(android.R.drawable.ic_menu_camera).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add("Mesajlar").setIcon(android.R.drawable.ic_menu_save).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.add("Profil");
        menu.add("Ayarlar");
        menu.add("Çıkış");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String select = item.getTitle().toString();

        if (select.equals("Anasayfa")) {
            Toast.makeText(this, "Anasayfa'ya bastın", Toast.LENGTH_SHORT).show();
        } else if (select.equals("Bildirimler")) {
            Toast.makeText(this, "Bildirimler'e bastın", Toast.LENGTH_SHORT).show();
        } else if (select.equals("Mesajlar")) {
            Toast.makeText(this, "Mesajlar'a bastın", Toast.LENGTH_SHORT).show();
        } else if (select.equals("Profil")) {
            Toast.makeText(this, "Profil'e bastın", Toast.LENGTH_SHORT).show();
        } else if (select.equals("Ayarlar")) {
            Toast.makeText(this, "Ayarlar'e bastın", Toast.LENGTH_SHORT).show();
        } else if (select.equals("Çıkış")) {

            edit.putInt("beniHatirla", 0);
            edit.commit();

            startActivity(new Intent(this, MainActivity.class));
            finish();

            Toast.makeText(this, "Çıkış'e bastın", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

}
