package com.wissen.sconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class LoginActivity extends AppCompatActivity {

    TextView kulAd, kulSifre;
    CheckBox chkButon;

    static String serverIp;
    SharedPreferences sp;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        edit = sp.edit();


        serverIp = ServerIP.serverIp;

        kulAd = ((EditText) findViewById(R.id.kulAd));
        kulSifre = ((EditText) findViewById(R.id.kulSifre));
        chkButon = (CheckBox) findViewById(R.id.chkHatirla);

        Toast.makeText(LoginActivity.this,
                "Shared'e Kaydedilen " + sp.getInt("beniHatirla", 0), Toast.LENGTH_SHORT).show();

        if (sp.getInt("beniHatirla", 0) == 1) {
            startActivity(new Intent(this, AnaActivity.class));
            Toast.makeText(LoginActivity.this, "Bir kez daha burdan geçildi", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void girisYap(View v) {
        boolean chkLogin = loginCheck(kulAd.getText() + "", kulSifre.getText() + "");

        if (chkLogin) {

            if (chkButon.isChecked()) {
                edit.putString("userNick", kulAd.getText() + "");
                edit.putString("userPassword", kulSifre.getText() + "");
                edit.putInt("beniHatirla", 1);
                edit.commit();
            }

            startActivity(new Intent(this, AnaActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Kullanıcı Adı yada Şifre yanlış!", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean loginCheck(String userNick, String userPassword) {
        String str = "";
        try {
            String adr = "http://" + serverIp + ":8080/KullaniciKayitService/inventory/kullanici/checklogin";
            URL url = new URL(adr);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setRequestProperty("Content-Type", "application/json");

            BufferedWriter outwrite = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));

            outwrite.append("{"
                    + "\"userNick\" : \"" + userNick + "\", "
                    + "\"userPassword\" : \"" + userPassword + "\""
                    + "}");
            outwrite.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            str = in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (str.equals("Match")) {
            return true;
        }
        return false;
    }

}
