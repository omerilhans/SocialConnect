package com.wissen.sconnect;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class KayitServisi extends FirebaseInstanceIdService {

    static String serverIp;

    @Override
    public void onTokenRefresh() {
        serverIp = ServerIP.serverIp;

        Log.e("Geldi TEST","onTokenRefresh");
        FirebaseInstanceId firebase = FirebaseInstanceId.getInstance();
        String registrationId = firebase.getToken();

        Log.e("FireBaseID",registrationId);

        String cihazBilgisi = Build.MANUFACTURER + " - "+ Build.MODEL + " - "+ Build.SERIAL;

        String str = "";
        try {
            String adr = "http://\" + serverIp + \":8080/KullaniciKayitService/inventory/kullanici/devicetodb";
            URL url = new URL(adr);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");

            BufferedWriter outwrite = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
            outwrite.append("{ "
                    + "\"registrationId\" : \"" + registrationId + "\","
                    + "\"cihazBilgisi\" : \"" + cihazBilgisi + "\""
                    + " }");
            outwrite.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            str = in.readLine();
            Log.e("x"," Persist : " + str);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
