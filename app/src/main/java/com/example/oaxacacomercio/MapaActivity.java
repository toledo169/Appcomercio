package com.example.oaxacacomercio;
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
    private static final List<List<Point>> POINTS = new ArrayList<>();
    private static final List<Point> OUTER_POINTS = new ArrayList<>();
    private ArrayList<Double> lat= new ArrayList<>();
    private ArrayList<Double> lon= new ArrayList<>();
    ProgressDialog progress;
    JsonRequest jsonObjectRequest;
    RequestQueue request;
    ArrayList<Vendedor> listavendedoresdetalleszona;
    String idzona,opcion1,opcion2,latitud,longitu,nombrev;
    /*static {
        OUTER_POINTS.add(Point.fromLngLat(-96.729958, 17.060081 ));

        OUTER_POINTS.add(Point.fromLngLat(-96.728998,17.059917 ));
        OUTER_POINTS.add(Point.fromLngLat(-96.729207,17.058932 ));
        OUTER_POINTS.add(Point.fromLngLat(-96.730125, 17.059107));
        POINTS.add(OUTER_POINTS);
    }*/

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
        lat = (ArrayList<Double>) getIntent().getSerializableExtra("lat");
        lon = (ArrayList<Double>) getIntent().getSerializableExtra("lon");
        idzona=getIntent().getExtras().getString("idZona");
        if(getIntent().getExtras().getString("zonas")!=null){
            opcion1=getIntent().getExtras().getString("zonas");
        }else if(getIntent().getExtras().getString("vendedor")!=null) {
            opcion2=getIntent().getExtras().getString("vendedor");
        }

        Toast.makeText(this,opcion1+"dffsdfsdf",Toast.LENGTH_SHORT).show();
        Toast.makeText(this,opcion2+"dgdfg",Toast.LENGTH_SHORT).show();

        //cargarwebservice();
        listavendedoresdetalleszona=new ArrayList<>();
        Mapbox.getInstance(this, "pk.eyJ1IjoidG9sZWRvMTYiLCJhIjoiY2s4eGR3aHl5MHg5ajNucGsxMHN6YWg0MyJ9.EcFmUIJJCWb47aJAFHddRw");
         latitud=getIntent().getExtras().getString("latitud");
          longitu=getIntent().getExtras().getString("longitud");
          nombrev=getIntent().getExtras().getString("name");
        setContentView(R.layout.activity_mapa);
        mapView = (MapView) findViewById(R.id.mapamaps);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

    }
    public void agregarVendedores(MapboxMap mapboxMap){
        for (int i=0;i<listavendedoresdetalleszona.size();i++){
            mapboxMap.addMarker(new MarkerOptions()
                    .position(new LatLng(listavendedoresdetalleszona.get(i).getLatitud(),listavendedoresdetalleszona.get(i).getLongitud()))
                    .title(listavendedoresdetalleszona.get(i).getNombrev())

            );
        }
    }
    public void agregarVendedor(MapboxMap mapboxMap){
        mapboxMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(latitud),Double.parseDouble(longitu)))
                    .title(nombrev)

            );

    }

    public void agregarPuntos(){
        for (int i=0; i<lat.size(); i++){
            OUTER_POINTS.add(Point.fromLngLat(lon.get(i),lat.get(i)));
        }
        /*OUTER_POINTS.add(Point.fromLngLat(-96.729958, 17.060081 ));

        OUTER_POINTS.add(Point.fromLngLat(-96.728998,17.059917 ));
        OUTER_POINTS.add(Point.fromLngLat(-96.729207,17.058932 ));
        OUTER_POINTS.add(Point.fromLngLat(-96.730125, 17.059107));*/
        POINTS.add(OUTER_POINTS);
    }


    @Override
    public void onMapReady(MapboxMap mapboxMap) {


        if(opcion1.equals("zonas")){
            request = Volley.newRequestQueue(this);
            agregarPuntos();
            agregarVendedores(mapboxMap);
            cargarwebservice();
            mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    style.addSource(new GeoJsonSource("source-id", Polygon.fromLngLats(POINTS)));
                    style.addLayerBelow(new FillLayer("layer-id", "source-id").withProperties(
                            fillColor(Color.parseColor("#3bb2d0"))), "settlement-label"
                    );
                }
            });
        }else if(opcion2.equals("vendedor")){
            agregarVendedor(mapboxMap);
        }

    }

    private void cargarwebservice() {
        progress=new ProgressDialog(this);
        progress.setMessage("Consultando...");
        progress.show();
        String url="http://192.168.0.11/api/Usuario/listarzonavendedor/"+Integer.parseInt(idzona);
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
        progress.hide();

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
            progress.hide();
            //recyclerViewDetalleszona.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,"no se ha podido establecer conexion"+" "+response,Toast.LENGTH_LONG).show();
            progress.hide();
        }
    }
}
