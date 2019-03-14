package com.ismailhakkiaydin.boundservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();
    private ServiceConnection connection;
    private boolean serviseBagliMi = false;
    private MyBoundService myBoundService;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void startMyBoundService(View view) {
        Intent intent = new Intent(this, MyBoundService.class);
        startService(intent);
        Log.e(TAG, "SERVICE BASLATILDI");
    }

    public void stopMyBoundService(View view) {
        Intent intent = new Intent(this, MyBoundService.class);
        stopService(intent);
        Log.e(TAG, "SERVICE DURDURULDU");
    }

    public void bindMyBoundService(View view) {

        if (connection == null) {

            connection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    MyBoundService.MyLocalBinder myLocalBinder = (MyBoundService.MyLocalBinder) service;
                    myBoundService = myLocalBinder.getService();
                    serviseBagliMi = true;
                    Log.e(TAG, "SERVICE CONNECTED CAGRILDI");
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    serviseBagliMi = false;
                    Log.e(TAG, "SERVICE DISCONNECTED CAGRILDI");
                }
            };
        }

        if (serviseBagliMi == false) {
            Intent intent = new Intent(this, MyBoundService.class);
            bindService(intent, connection, BIND_AUTO_CREATE);
            Log.e(TAG, "SERVIS BAGLANTISI YAPILDI");
            serviseBagliMi = true;
        } else {
            Toast.makeText(this, "Servise zaten bağlı", Toast.LENGTH_SHORT).show();
        }

    }


    public void unbindMyBoundService(View view) {

        if (serviseBagliMi == true) {
            unbindService(connection);
            serviseBagliMi = false;
            Log.e(TAG, "SERVIS BAGLANTISI KESILDI");

        } else {
            Toast.makeText(this, "Servise zaten bağlı değil", Toast.LENGTH_SHORT).show();
        }

    }

    public void getRandomNumber(View view) {

        if (serviseBagliMi == true) {

            Toast.makeText(this, "Üretilen Sayi :" + myBoundService.getSayi(), Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(this, "Önce servis bağlantısını yapın", Toast.LENGTH_SHORT).show();

        }

    }
}