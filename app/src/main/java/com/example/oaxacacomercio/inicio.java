package com.example.oaxacacomercio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.WindowManager;

public class inicio extends AppCompatActivity {
    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
      //  prefs =getSharedPreferences("Preferences", Context.MODE_PRIVATE);
      //  Intent intentLogin = new Intent(inicio.this, MainActivity.class);
       // Intent intentmain=new Intent(this,Ventanas.class);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
              new Handler().postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      Intent intent = new Intent(inicio.this, MainActivity.class);
                      startActivity(intent);
                      inicio.this.finish();
                  }
              },3000);
    }


}
