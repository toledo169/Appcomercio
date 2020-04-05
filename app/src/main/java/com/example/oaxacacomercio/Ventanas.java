package com.example.oaxacacomercio;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import com.example.oaxacacomercio.ui.gallery.GalleryFragment;
import com.example.oaxacacomercio.ui.home.HomeFragment;
import com.example.oaxacacomercio.ui.send.SendFragment;
import com.example.oaxacacomercio.ui.share.ShareFragment;
import com.example.oaxacacomercio.ui.slideshow.SlideshowFragment;
import com.example.oaxacacomercio.ui.tools.ToolsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

public class Ventanas extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        setContentView(R.layout.activity_ventanas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // FloatingActionButton fab = findViewById(R.id.fab);
       // fab.setOnClickListener(new View.OnClickListener() {
          //  @Override
         //   public void onClick(View view) {
         //       Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.municipiodeoaxaca.gob.mx"));
         //       startActivity(intent);
           // }
        //});
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        Fragment miFragment=null;
        boolean fragmentSeleccionado=false;

        if (id == R.id.nav_home) {
            miFragment=new HomeFragment();
            fragmentSeleccionado=true;
        }else if (id == R.id.nav_tools) {
            miFragment=new ToolsFragment();
            fragmentSeleccionado=true;
        } else if (id == R.id.nav_gallery) {
            miFragment=new GalleryFragment();
            fragmentSeleccionado=true;
        } else if (id == R.id.nav_send) {
            miFragment=new SendFragment();
            fragmentSeleccionado=true;
        } else if (id == R.id.nav_share) {
            miFragment=new ShareFragment();
            fragmentSeleccionado=true;
        } else if (id == R.id.nav_slideshow) {
            miFragment=new SlideshowFragment();
            fragmentSeleccionado=true;
        }

        if (fragmentSeleccionado==true){
            getSupportFragmentManager().beginTransaction().replace(R.id.escena,miFragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
