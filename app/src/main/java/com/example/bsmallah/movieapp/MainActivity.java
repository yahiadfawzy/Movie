package com.example.bsmallah.movieapp;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BigTask task1 = new BigTask("task1");
        BigTask task2 = new BigTask("task2");

        task1.start();
        task2.start();



       /* if(isInternetAvailable())
          Toast.makeText(getBaseContext(),"connected",Toast.LENGTH_SHORT).show();
        else
          Toast.makeText(getBaseContext(),"not connected",Toast.LENGTH_SHORT).show();*/

      ConnectionTask connectionTask = new ConnectionTask();
      connectionTask.start();


    }

    int x = 0;
    //this method execute one by one
    synchronized void printx(String tag){
        Log.d(TAG, tag + x++);
    }

    class ConnectionTask extends Thread{
        @Override
       public void run() {
       super.run();
       final boolean online =  isOnline();
       //todo add your data
       runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(online)
                    Toast.makeText(getBaseContext(),"connected",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getBaseContext(),"not connected",Toast.LENGTH_SHORT).show();
            }
        });
        }

    }

    public boolean isOnline() {
        try {
            int timeoutMs = 1500;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

            sock.connect(sockaddr, timeoutMs);
            sock.close();

            return true;
        } catch (IOException e) { return false; }
    }


    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    class BigTask extends Thread{
        String threadName;
        BigTask(String name){
            threadName = name;
        }
        @Override
        public void run() {
            super.run();
            for (int i = 0;i<5;i++){
            printx(""+threadName);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }



 }

