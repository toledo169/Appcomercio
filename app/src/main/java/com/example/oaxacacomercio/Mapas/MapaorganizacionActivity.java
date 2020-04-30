package com.example.oaxacacomercio.Mapas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;

import com.example.oaxacacomercio.Modelos.User;
import com.example.oaxacacomercio.R;
import com.example.oaxacacomercio.Ventanas;
import com.example.oaxacacomercio.ui.home.HomeFragment;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.ArrayList;

public class MapaorganizacionActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    int claveZ;
    String nomv;
    ArrayList<Double> lati;
    ArrayList<Double>longi;
    ArrayList<String>nomb;
    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "sk.eyJ1IjoiamFpMTg5IiwiYSI6ImNrOTUyeW95dzA1aXkzZXE5eGRxeXBmZWEifQ.Y4s7OIVr91HZt88ewuVZ-w");
        setContentView(R.layout.activity_mapaorganizacion);
        Toolbar toolbar = findViewById(R.id.toolbarm);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String name=getIntent().getExtras().getString("nombre_organizacion");
        nomv=getIntent().getExtras().getString("name");
        getSupportActionBar().setTitle(name);
        lati=(ArrayList<Double>)getIntent().getSerializableExtra("lat");
        longi=(ArrayList<Double>)getIntent().getSerializableExtra("log");
        nomb=(ArrayList<String>)getIntent().getSerializableExtra("nom") ;

        // Inflate the layout for this fragment

        mapView = (MapView)findViewById(R.id.mapamapaso);
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        mapboxMap.setStyle(Style.MAPBOX_STREETS);
        for (int i = 0; i < lati.size(); i++) {
            mapboxMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lati.get(i), longi.get(i))).title(nomb.get(i))
            );

        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            User usuario= new User(this);
            Intent goMain = new Intent(MapaorganizacionActivity.this, Ventanas.class);
            goMain.putExtra(HomeFragment.apellido_paternos,usuario.getApellido_paterno());
            goMain.putExtra(HomeFragment.apellido_maternos,usuario.getApellido_materno());
            goMain.putExtra(HomeFragment.nombres,usuario.getNombre());
            goMain.putExtra(HomeFragment.correo,usuario.getCorreoelectronico());
            goMain.putExtra(HomeFragment.cargo,usuario.getCargo());
            goMain.putExtra(HomeFragment.municipio,usuario.getMunicipio());
            //   finish();
            startActivity(goMain);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
