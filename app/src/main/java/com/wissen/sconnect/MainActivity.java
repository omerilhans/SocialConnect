package com.wissen.sconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etServerIp;

    SharedPreferences sp;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        edit = sp.edit();

        if (sp.getInt("beniHatirla", 0) == 1) {
            startActivity(new Intent(this, AnaActivity.class));
            finish();
        }

        etServerIp = (EditText) findViewById(R.id.etServerIP);
    }

    public void girisYap(View v) {
        ServerIP.serverIp = etServerIp.getText() + "";
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    public void kayitOl(View v) {
        ServerIP.serverIp = etServerIp.getText() + "";
        Intent i = new Intent(this, KayitActivity.class);
        startActivity(i);
        finish();
    }
}
