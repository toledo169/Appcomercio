package com.example.oaxacacomercio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
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
