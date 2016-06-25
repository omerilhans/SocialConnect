package com.wissen.sconnect;

import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class KayitActivity extends AppCompatActivity {

    TextView ad, soyad, eMail, kulAdin, dogumGunu, sifre, sifreTekrar;
    static String serverIp;
    String cihazBilgisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);

        serverIp = ServerIP.serverIp;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String manu = Build.MANUFACTURER;
        String model = Build.MODEL;
        cihazBilgisi = manu + " - " + model;

        ad = (TextView) ((EditText) findViewById(R.id.ad));
        soyad = (TextView) ((EditText) findViewById(R.id.soyad));
        kulAdin = (TextView) ((EditText) findViewById(R.id.kulAdin));
        eMail = (TextView) ((EditText) findViewById(R.id.eMail));
        dogumGunu = (TextView) ((EditText) findViewById(R.id.dogumGunu));
        sifre = (TextView) ((EditText) findViewById(R.id.sifre));
        sifreTekrar = (TextView) ((EditText) findViewById(R.id.sifreTekrar));
    }

    public void kayitOl(View v) {

        User user = new User();
        cihazBilgisi = Build.MANUFACTURER + " - " + Build.MODEL + " - " + Build.SERIAL;

        user.setUserName(ad.getText() + "");
        user.setUserSurname(soyad.getText() + "");
        user.setUserNick(kulAdin.getText() + "");
        user.setUserEmail(eMail.getText() + "");
        user.setUserBirthDate(dogumGunu.getText() + "");
        user.setUserPassword(sifre.getText() + "");
        user.setCihazBilgisi(cihazBilgisi);


        // eğer 1 gelirse, böyle bir kullanıcı mevcut ama 0 gelirse mevcut değil. Bu yüzden kayıt yapılabilir.
        int kontrol = 0;
        kontrol = kullaniciKontrol(user.getUserNick());

        if (kontrol == 0) {
            if (sifre.getText().toString().equals(sifreTekrar.getText().toString())) {

                try {
                    String adr = "http://" + serverIp + ":8080/KullaniciKayitService/inventory/kullanici/todb";
                    URL url = new URL(adr);
                    URLConnection con = url.openConnection();
                    con.setDoOutput(true);
                    con.setReadTimeout(30000);
                    con.setRequestProperty("Content-Type", "application/json");

                    BufferedWriter outwrite = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));

                    outwrite.append("{ "
                            + "\"userName\" : \"" + user.getUserName() + "\","
                            + "\"userSurname\" : \"" + user.getUserSurname() + "\","
                            + "\"userNick\" : \"" + user.getUserNick() + "\","
                            + "\"userEmail\" : \"" + user.getUserEmail() + "\","
                            + "\"userBirthDate\" : \"" + user.getUserBirthDate() + "\","
                            + "\"userPassword\" : \"" + user.getUserPassword() + "\","
                            + "\"cihazBilgisi\" : \"" + user.getCihazBilgisi() + "\""
                            + " }");

                    outwrite.flush();
                    StringBuilder build;
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    build = new StringBuilder();
                    String str;
                    while ((str = in.readLine()) != null) {
                        build.append(str).append("\r\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
            } else {
                Toast.makeText(KayitActivity.this, "Şifreler uyuşmuyor tekrar deneyin", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(KayitActivity.this, "Girdiğiniz kullanıcı adı alınmış tekrar deneyin", Toast.LENGTH_SHORT).show();
        }
    }

    public int kullaniciKontrol(String userNick) {
        String result = "";
        try {
            String adr = "http://" + serverIp + ":8080/KullaniciKayitService/inventory/kullanici/checkuser";
            URL url = new URL(adr);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setRequestProperty("Content-Type", "application/json");

            BufferedWriter outwrite = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
            outwrite.append("{ "
                    + "\"userNick\" : \"" + userNick + "\""
                    + " }");
            outwrite.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Integer(result);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
