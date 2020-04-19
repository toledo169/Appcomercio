package com.example.oaxacacomercio.Mapas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.example.oaxacacomercio.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

public class MapavendedorActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    String latitud,longitu,nombrev;
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
        Mapbox.getInstance(this, "pk.eyJ1IjoidG9sZWRvMTYiLCJhIjoiY2s4eGR3aHl5MHg5ajNucGsxMHN6YWg0MyJ9.EcFmUIJJCWb47aJAFHddRw");
         latitud=getIntent().getExtras().getString("latitud");
         longitu=getIntent().getExtras().getString("longitud");
         nombrev=getIntent().getExtras().getString("name");
        setContentView(R.layout.activity_mapavendedor);
         mapView=(MapView)findViewById(R.id.mapavendedores);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        mapboxMap.setStyle(Style.MAPBOX_STREETS);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latitud),Double.parseDouble(longitu))).title(nombrev));
            }
        });
    }
}
