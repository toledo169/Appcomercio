package com.example.oaxacacomercio.Mapas;
import android.app.ProgressDialog;
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
import com.example.oaxacacomercio.Modelos.Vendedor;
import com.example.oaxacacomercio.R;
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

import androidx.core.app.NotificationCompat;
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

public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback, Response.Listener<JSONObject>,Response.ErrorListener {
    private MapView mapView;
    private ArrayList<Double> lat= new ArrayList<>();
    private ArrayList<Double> lon= new ArrayList<>();
    ProgressDialog progress;
    JsonRequest jsonObjectRequest;
    RequestQueue request;
   private static ArrayList<Vendedor> listavendedoresdetalleszona=new ArrayList<>();
    String idzona;
    int claveZ;
            //,opcion1,opcion2,latitud,longitu,nombrev;
    /*static {
        OUTER_POINTS.add(Point.fromLngLat(-96.729958, 17.060081 ));

        OUTER_POINTS.add(Point.fromLngLat(-96.728998,17.059917 ));
        OUTER_POINTS.add(Point.fromLngLat(-96.729207,17.058932 ));
        OUTER_POINTS.add(Point.fromLngLat(-96.730125, 17.059107));
        POINTS.add(OUTER_POINTS);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "sk.eyJ1IjoiamFpMTg5IiwiYSI6ImNrOTUyeW95dzA1aXkzZXE5eGRxeXBmZWEifQ.Y4s7OIVr91HZt88ewuVZ-w");
        setContentView(R.layout.activity_mapa);
        //  lat = (ArrayList<Double>) getIntent().getSerializableExtra("lat");
       // lon = (ArrayList<Double>) getIntent().getSerializableExtra("lon");
        claveZ=getIntent().getExtras().getInt("id_zona");
        request = Volley.newRequestQueue(this);
        cargarwebservice();
        mapView = (MapView) findViewById(R.id.mapamaps);
        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);

    }
    public void agregarVendedores(MapboxMap mapboxMap){
        for (int i=0;i<listavendedoresdetalleszona.size();i++){
            mapboxMap.addMarker(new MarkerOptions()
                    .position(new LatLng(listavendedoresdetalleszona.get(i).getLatitud(),listavendedoresdetalleszona.get(i).getLongitud()))
                    .title(listavendedoresdetalleszona.get(i).getNombrev())

            );
        }
    }

    /*public void agregarPuntos(){
        for (int i=0; i<lat.size(); i++){
            OUTER_POINTS.add(Point.fromLngLat(lon.get(i),lat.get(i)));
        }
        /*OUTER_POINTS.add(Point.fromLngLat(-96.729958, 17.060081 ));

        OUTER_POINTS.add(Point.fromLngLat(-96.728998,17.059917 ));
        OUTER_POINTS.add(Point.fromLngLat(-96.729207,17.058932 ));
        OUTER_POINTS.add(Point.fromLngLat(-96.730125, 17.059107));*/
     //   POINTS.add(OUTER_POINTS);
    //}

    private void cargarwebservice() {
        //progress=new ProgressDialog(this);
       // progress.setMessage("Consultando...");
       // progress.show();
       // progress.dismiss();
        String url="http://192.168.0.11/api/Usuario/listarzonavendedor/"+ claveZ;
        // cuarto xoxo http://192.168.0.11/api/Usuario/listarorg
        //casa angel 192.168.0.23
        jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR: ", error.toString());
      //  progress.hide();
      //  progress.dismiss();

    }

    @Override
    public void onResponse(JSONObject response) {
        Vendedor vendedor=null;
        JSONArray json=response.optJSONArray("zonasvend");
        try {
            for (int i=0;i<json.length();i++){
                vendedor=new Vendedor();
                JSONObject jsonObject = null;
                jsonObject=json.getJSONObject(i);

                vendedor.setId(jsonObject.optInt("id_vendedor"));
                vendedor.setNombre(jsonObject.optString("name"));
                vendedor.setApellido_paterno(jsonObject.optString("apellido_paterno"));
                vendedor.setApellido_materno(jsonObject.optString("apellido_materno"));
                vendedor.setNomorganizacion(jsonObject.optString("nombre_organizacion"));
                vendedor.setActividad(jsonObject.optString("tipo_actividad"));
                vendedor.setGiro(jsonObject.optString("giro"));
                vendedor.setNomzona(jsonObject.optString("nombre"));
                vendedor.setLatitud(jsonObject.optDouble("latitud"));
                vendedor.setLongitud(jsonObject.optDouble("longitud"));
                listavendedoresdetalleszona.add(vendedor);
                //   listauxiliar.add(vendedor);
            }
          //  progress.hide();
          //  progress.dismiss();
            //recyclerViewDetalleszona.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,"no se ha podido establecer conexion"+" "+response,Toast.LENGTH_LONG).show();
        //    progress.hide();
        //    progress.dismiss();
        }
    }
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
             //  agregarVendedores(mapboxMap);
                mapboxMap.setStyle(Style.MAPBOX_STREETS);
                        for (int i=0;i<listavendedoresdetalleszona.size();i++){
                            mapboxMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(listavendedoresdetalleszona.get(i).getLatitud(),listavendedoresdetalleszona.get(i).getLongitud()))
                                    .title(listavendedoresdetalleszona.get(i).getNombrev())
                            );

                        }
                     //   mapView.onDestroy();
               listavendedoresdetalleszona.clear(); //lo hace aveces e inverso
            //    mapView.onStart();
                        //mapboxMap.clear();
                    }




            }


       // mapboxMap.notify();
     //   mapView.onStart();



