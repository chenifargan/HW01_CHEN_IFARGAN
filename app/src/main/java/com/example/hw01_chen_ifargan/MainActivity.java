package com.example.hw01_chen_ifargan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    private MaterialButton btn;
    private EditText edt;
    private int level;
    private Context context = this;
    private  PackageManager packageManager;
   ///  private Boolean hasCamera;
    boolean connected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        check();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                packageManager = context.getPackageManager();

                if (connected == true) {
                    if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                        if (String.valueOf(level).equals(edt.getText().toString().trim())) {

                            startActivity(new Intent(MainActivity.this, MainActivity2.class));
                            finish();

                        }


                    }
                }
            }
        });


    }

    private void check() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
             level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
        }
    };


    private void initViews() {

        this.registerReceiver(this.broadcastReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        btn= findViewById(R.id.btn);
        edt= findViewById(R.id.editText);
    }
}