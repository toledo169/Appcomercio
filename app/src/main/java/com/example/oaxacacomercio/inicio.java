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

import com.example.oaxacacomercio.Modelos.User;
import com.example.oaxacacomercio.Modelos.Vendedor;
import com.example.oaxacacomercio.Vendedor.PermisoFragment;
import com.example.oaxacacomercio.Vendedor.VendedorActivity;
import com.example.oaxacacomercio.ui.home.HomeFragment;

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
        final User user=new User(inicio.this);
        final Vendedor vendedor=new Vendedor(inicio.this);
              new Handler().postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      if(user.getCorreoelectronico()!="") {
                          Intent intent = new Intent(inicio.this, Ventanas.class);
                          intent.putExtra(HomeFragment.apellido_paternos,user.getApellido_paterno());
                          intent.putExtra(HomeFragment.apellido_maternos,user.getApellido_materno());
                          intent.putExtra(HomeFragment.nombres,user.getNombre());
                          intent.putExtra(HomeFragment.correo,user.getCorreoelectronico());
                          intent.putExtra(HomeFragment.cargo,user.getCargo());
                          intent.putExtra(HomeFragment.municipio,user.getMunicipio());
                          startActivity(intent);
                          finish();
                      }else if (vendedor.getNombrev()!=""){
                          Intent goMain = new Intent(inicio.this, VendedorActivity.class);
                          goMain.putExtra(PermisoFragment.apellido_paternos,vendedor.getApellido_paterno());
                          goMain.putExtra(PermisoFragment.apellido_maternos,vendedor.getApellido_materno());
                          goMain.putExtra(PermisoFragment.nombres,vendedor.getNombrev());
                          goMain.putExtra(PermisoFragment.numexpediente,vendedor.getNumexpediente());
                          goMain.putExtra(PermisoFragment.numcuenta,vendedor.getNumcuenta());
                          goMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                          startActivity(goMain);
                          finish();
                      }
                      else{
                          Intent intent = new Intent(inicio.this, MainActivity.class);
                          startActivity(intent);
                          finish();

                      }
                  }
              },3000);
    }


}
