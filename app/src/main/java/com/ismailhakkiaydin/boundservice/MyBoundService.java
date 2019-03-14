package com.ismailhakkiaydin.boundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

public class MyBoundService extends Service {

    private final static String TAG=MyBoundService.class.getSimpleName();

    private int sayi;
    private boolean sayiUreticisiAcikMi;

    class MyLocalBinder extends Binder {
        public MyBoundService getService(){
            return MyBoundService.this;
        }
    }

    private IBinder myLocalBinder=new MyLocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "ONBIND");
        return myLocalBinder;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "ONCREATE");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "ONDESTROY");
        sayiUreticisiAcikMi=false;
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
        Log.i(TAG, "ONSTARTCOMMAND");

        sayiUreticisiAcikMi=true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                sayiUret();
            }
        }).start();
        return START_STICKY;
    }

    private void sayiUret(){
        while(sayiUreticisiAcikMi) {
            try {
                Thread.sleep(1000);
                sayi = new Random().nextInt(100);
                Log.i(TAG, "Sayi : " + sayi + " Thread Adı: " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getSayi(){
        return sayi;
    }

}