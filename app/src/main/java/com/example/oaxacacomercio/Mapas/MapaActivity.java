package com.example.oaxacacomercio.Mapas;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.oaxacacomercio.Detalles.DetallesMapaZonaActivity;
import com.example.oaxacacomercio.MainActivity;
import com.example.oaxacacomercio.Modelos.User;
import com.example.oaxacacomercio.Modelos.Vendedor;
import com.example.oaxacacomercio.R;
import com.example.oaxacacomercio.Ventanas;
import com.example.oaxacacomercio.ui.gallery.GalleryFragment;
import com.example.oaxacacomercio.ui.home.HomeFragment;
import com.example.oaxacacomercio.ui.send.SendFragment;
import com.example.oaxacacomercio.ui.share.ShareFragment;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.navigation.Navigation;
import timber.log.Timber;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillOpacity;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor;

public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    int claveZ;
    String nombre;
    ArrayList<Double>lati;
    ArrayList<Double>longi;
     ArrayList<Double>latizona;
     ArrayList<Double>longizona;
    ArrayList<String>nomb;
    private  List<List<Point>> POINTS = new ArrayList<>();
    private  List<Point> OUTER_POINTS = new ArrayList<>();

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
        setContentView(R.layout.activity_mapa);
       // Navigation.findNavController(this,R.id.mapazonaven).navigate(R.id.action_splashFragment_to_AFragment);

       Toolbar toolbar = findViewById(R.id.toolbarzonam);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
      //  getSupportActionBar().setTitle(name);
        lati=(ArrayList<Double>)getIntent().getSerializableExtra("lat");
        longi=(ArrayList<Double>)getIntent().getSerializableExtra("log");
        nomb=(ArrayList<String>)getIntent().getSerializableExtra("nom") ;
        latizona=(ArrayList<Double>)getIntent().getSerializableExtra("latitudzona");
        longizona=(ArrayList<Double>)getIntent().getSerializableExtra("longitudzona");
       String nombreZ=getIntent().getExtras().getString("nombre");
        nombre=getIntent().getExtras().getString("name");
        getSupportActionBar().setTitle(nombreZ);
        //  lat = (ArrayList<Double>) getIntent().getSerializableExtra("lat");
        // lon = (ArrayList<Double>) getIntent().getSerializableExtra("lon");
        mapView = (MapView) findViewById(R.id.mapamaps);
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            User usuario= new User(this);
            Intent goMain = new Intent(MapaActivity.this, Ventanas.class);

            goMain.putExtra(HomeFragment.apellido_paternos,usuario.getApellido_paterno());
            goMain.putExtra(HomeFragment.apellido_maternos,usuario.getApellido_materno());
            goMain.putExtra(HomeFragment.nombres,usuario.getNombre());
            goMain.putExtra(HomeFragment.correo,usuario.getCorreoelectronico());
            goMain.putExtra(HomeFragment.cargo,usuario.getCargo());
            goMain.putExtra(HomeFragment.municipio,usuario.getMunicipio());
            //   finish();
            startActivity(goMain);
            finish();
         //  Navigation.findNavController(android.R.id.home).navigate(R.id.irmapita);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


     public void agregarPuntos(){
 for (int i=0; i<latizona.size(); i++){
            OUTER_POINTS.add(Point.fromLngLat(longizona.get(i),latizona.get(i)));
        }
         POINTS.add(OUTER_POINTS);
    }
        @Override
        public void onMapReady ( final MapboxMap mapboxMap){
            //  agregarVendedores(mapboxMap);
            mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                   // agregarPuntos();
                    for (int i=0; i<latizona.size(); i++){
                        OUTER_POINTS.add(Point.fromLngLat(longizona.get(i),latizona.get(i)));
                    }
                    POINTS.add(OUTER_POINTS);
                    style.addSource(new GeoJsonSource("source-id", Polygon.fromLngLats(POINTS)));
                    style.addLayerBelow(new FillLayer("layer-id", "source-id").withProperties(
                            fillColor(Color.parseColor("#3bb2d0"))), "settlement-label"
                    );
                }
            });
            for (int i = 0; i < lati.size(); i++) {
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lati.get(i), longi.get(i))).title(nomb.get(i))
                );

            }
        }


    }




