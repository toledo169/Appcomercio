package com.example.oaxacacomercio;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.example.oaxacacomercio.Modelos.User;
import com.example.oaxacacomercio.ui.gallery.GalleryFragment;
import com.example.oaxacacomercio.ui.home.HomeFragment;
import com.example.oaxacacomercio.ui.send.SendFragment;
import com.example.oaxacacomercio.ui.share.ShareFragment;
import com.example.oaxacacomercio.ui.slideshow.SlideshowFragment;
import com.example.oaxacacomercio.ui.tools.ToolsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

public class Ventanas extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
  //  private SharedPreferences prefs;
    int clave;
    String mail;
  ///public static final String numexpediente="id";
   // public static final String correoe = "email";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_ventanas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //  int correo = getIntent().getExtras().getInt("id");
     //   System.out.println("clave porfavor"+correo);
    //    mail=getIntent().getExtras().getString("email");
     //   clave=getIntent().getExtras().getInt("id");
     //   System.out.println("la clave es"+clave);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ventanas, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
   /*  @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
              /*  AlertDialog.Builder builder= new AlertDialog.Builder(Ventanas.this);
                LayoutInflater inflater= getLayoutInflater();
                View view= inflater.inflate(R.layout.dialog_cerrar,null);
                builder.setView(view);
                final AlertDialog dialog=builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button btnsi= view.findViewById(R.id.btnsi);
                btnsi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        logOut();
                    }
                });
                Button btnno=view.findViewById(R.id.btnno);
                btnno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();*/
     /*           SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Ventanas.this, SweetAlertDialog.WARNING_TYPE);
                sweetAlertDialog.setTitleText("¿Esta seguro");
                sweetAlertDialog.setContentText("¿Usted desea salir de la aplicación");
                sweetAlertDialog.setCancelText("Cancelar");
                sweetAlertDialog.setConfirmText("Continuar");
                sweetAlertDialog.showCancelButton(true);
                sweetAlertDialog.setCancelable(false);
                sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.setTitleText("Cancelado").setContentText("Los cambios han sido descartados").showCancelButton(false)
                                .setCancelClickListener(null).setConfirmClickListener(null).changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        sDialog.setCancelable(false);
                        sDialog.setCanceledOnTouchOutside(false);
                    }
                });
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.setTitleText("Actualizado").setContentText("Usted esta cerrando cesión")
                                .showCancelButton(false).setCancelClickListener(null).setConfirmClickListener(null).changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        sDialog.setCancelable(false);
                        sDialog.setCanceledOnTouchOutside(false);
                        logOut();
                    }
                });
                sweetAlertDialog.setCanceledOnTouchOutside(false);
                sweetAlertDialog.show();
        break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logOut() {
        new User(Ventanas.this).removeuser();
        Intent intent= new Intent(Ventanas.this,MainActivity.class);
        startActivity(intent);
        finish();
    }*/
}
