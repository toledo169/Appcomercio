package com.example.oaxacacomercio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout inputCorreo,inputPassword;
    private Button usuarioadmi,vendedor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        crearComponentes();
    }
    public void goMain(View v){

        Intent goMain = new Intent(MainActivity.this, Ventanas.class);
        goMain.putExtra("valor",inputCorreo.getEditText().getText().toString());

        startActivity(goMain);
    }

    private void crearComponentes(){
        usuarioadmi=findViewById(R.id.btn_login);
        vendedor=findViewById(R.id.btn_vendedor);
        inputCorreo=findViewById(R.id.usuario);
        inputPassword=findViewById(R.id.password);
    }
}
