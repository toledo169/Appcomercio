package com.example.oaxacacomercio.Detalles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Transition;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.oaxacacomercio.Mapas.MapaActivity;
import com.example.oaxacacomercio.Modelos.Vendedor;
import com.example.oaxacacomercio.R;
import com.example.oaxacacomercio.Ventanas;
import com.example.oaxacacomercio.ui.gallery.GalleryFragment;
import com.example.oaxacacomercio.ui.send.SendFragment;
import com.example.oaxacacomercio.ui.send.SendViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetallesMapaZonaActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    ArrayList<Vendedor> listavendedoresdetalleszona;
    ArrayList<Double>lat=new ArrayList<>();
    ArrayList<Double>log=new ArrayList<>();
     ArrayList<Double>latitudZona=new ArrayList<>();
      ArrayList<Double>longitudzona=new ArrayList<>();
    ArrayList<String>nom=new ArrayList<>();
    // ArrayList<Vendedor>listauxiliar;
    ProgressDialog progress;
    JsonRequest jsonObjectRequest;
    RequestQueue request;
    int claveZ;
    String nombreZ;
    String name;
    Button mapaven;
    TextView txtnombrez,txtClavezona;
    private Transition transition;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_mapa_zona);
        toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        claveZ=getIntent().getExtras().getInt("id_zona");
        nombreZ=getIntent().getExtras().getString("nombre");
        mapaven=(Button)findViewById(R.id.buttonmapa);
        getSupportActionBar().setTitle(nombreZ);

        listavendedoresdetalleszona=new ArrayList<>();
        txtnombrez=(TextView)findViewById(R.id.txtnombrezonadetalles1);
        txtClavezona=(TextView)findViewById(R.id.txtDocumentozonadetalles1);
        txtnombrez.setText(nombreZ);
        txtClavezona.setText(String.valueOf(claveZ));
        request = Volley.newRequestQueue(this);
      cargarwebservice();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void iralmapa(View v){
        Intent intent=new Intent(DetallesMapaZonaActivity.this, MapaActivity.class);
        if(claveZ==1) {
            latitudZona.add(17.062304);
            longitudzona.add(-96.726491);
            latitudZona.add(17.061726);
            longitudzona.add(-96.723616);
            latitudZona.add(17.058929);
            longitudzona.add(-96.724211);
            latitudZona.add(17.059518);
            longitudzona.add(-96.727082);

            intent.putExtra("lat", lat);
            intent.putExtra("log", log);
            intent.putExtra("latitudzona", latitudZona);
            intent.putExtra("longitudzona", longitudzona);
            intent.putExtra("nom",nom);
            intent.putExtra("nombre",nombreZ);
            intent.putExtra("name",name);
            startActivity(intent);
        } else if(claveZ==2) {
            latitudZona.add(17.062443);
            longitudzona.add(-96.722420);
            latitudZona.add(17.063610);
            longitudzona.add(-96.728265);
            latitudZona.add(17.057072);
            longitudzona.add(-96.729607);
            latitudZona.add(17.056330);
            longitudzona.add(-96.725784);
            latitudZona.add(17.055356);
            longitudzona.add(-96.725945);
            latitudZona.add(17.055193);
            longitudzona.add(-96.724967);
            latitudZona.add(17.056192);
            longitudzona.add(-96.724730);
            latitudZona.add(17.062633);
            longitudzona.add(-96.723390);
            intent.putExtra("lat", lat);
            intent.putExtra("log", log);
            intent.putExtra("latitudzona", latitudZona);
            intent.putExtra("longitudzona", longitudzona);
            intent.putExtra("nom",nom);
            intent.putExtra("nombre",nombreZ);
            intent.putExtra("name",name);
            startActivity(intent);
        }else if (claveZ==3) {
            latitudZona.add(17.061193);
            longitudzona.add( -96.730709);
            latitudZona.add(17.061376);
            longitudzona.add(-96.736053);
            latitudZona.add(17.061620);
            longitudzona.add(-96.737746);
           //
            latitudZona.add(17.059518);
            longitudzona.add(-96.727082);
            latitudZona.add(17.054456);
            longitudzona.add(-96.732132);
            latitudZona.add(17.061797);
            longitudzona.add(-96.734390);// penultimo
            latitudZona.add(17.057918);
            longitudzona.add(-96.731456);// ultimo
            intent.putExtra("lat", lat);
            intent.putExtra("log", log);
            intent.putExtra("latitudzona", latitudZona);
            intent.putExtra("longitudzona", longitudzona);
            intent.putExtra("nom",nom);
            intent.putExtra("nombre",nombreZ);
            intent.putExtra("name",name);
            startActivity(intent);
        }
    }

    private void cargarwebservice() {
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

    }

    @Override
    public void onResponse(JSONObject response) {
        Vendedor vendedor=null;
        JSONArray json=response.optJSONArray("zonasvend");
        try {
            for (int i=0;i<json.length();i++){
                vendedor=new Vendedor(this);
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
                lat.add(vendedor.getLatitud());
                log.add(vendedor.getLongitud());
                nom.add(vendedor.getNombrev());
                //   listauxiliar.add(vendedor);
            }
            //     progress.hide();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,"no se ha podido establecer conexion"+" "+response,Toast.LENGTH_LONG).show();
//            progress.hide();
        }
    }
}
