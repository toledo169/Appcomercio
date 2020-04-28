package com.example.oaxacacomercio.Detalles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.oaxacacomercio.Mapas.MapaActActivity;
import com.example.oaxacacomercio.Mapas.MapaorganizacionActivity;
import com.example.oaxacacomercio.Modelos.Vendedor;
import com.example.oaxacacomercio.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetallesMapaActividaActivity extends AppCompatActivity  implements Response.Listener<JSONObject>,Response.ErrorListener {
    ArrayList<Vendedor> listavendedoresdetallesact;
    ArrayList<Double>lat=new ArrayList<>();
    ArrayList<Double>log=new ArrayList<>();
    // ArrayList<Vendedor> listauxiliar;
    ProgressDialog progress;
    JsonRequest jsonObjectRequest;
    RequestQueue request;
    TextView tvclave,tvnombre;
    String name;
    String nombrev;
    ArrayList<String>nom=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_mapa_activida);
        Toolbar toolbar = findViewById(R.id.toolbarract);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        name=getIntent().getExtras().getString("nombre_actividad");
        getSupportActionBar().setTitle(name);
        int claved=getIntent().getExtras().getInt("id_actividad");
        //-----------
        tvnombre=(TextView)findViewById(R.id.txtnombreactdetalles1);
        tvclave=(TextView)findViewById(R.id.txtDocumentoactdetalles1);

        tvnombre.setText(name);
        tvclave.setText(String.valueOf(claved));
        listavendedoresdetallesact=new ArrayList<>();
        //   listauxiliar=new ArrayList<>();
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

    private void cargarwebservice() {
            progress=new ProgressDialog(this);
            progress.setMessage("Consultando...");
            progress.show();
            String url="http://192.168.0.11/api/Usuario/listaractividadesvendedor/"+tvclave.getText().toString();
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
    public void iralmapaac(View view){
        Intent intent=new Intent(DetallesMapaActividaActivity.this, MapaActActivity.class);
        intent.putExtra("lat", lat);
        intent.putExtra("log", log);
        intent.putExtra("nom",nom);
        intent.putExtra("nombre_actividad",name);
        intent.putExtra("name",nombrev);
        startActivity(intent);
    }

    @Override
    public void onResponse(JSONObject response) {
        Vendedor vendedor=null;
        JSONArray json=response.optJSONArray("comerciales");
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
                listavendedoresdetallesact.add(vendedor);
                lat.add(vendedor.getLatitud());
                log.add(vendedor.getLongitud());
                nom.add(vendedor.getNombrev());
                //   listauxiliar.add(vendedor);
            }
            progress.hide();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,"no se ha podido establecer conexion"+" "+response,Toast.LENGTH_LONG).show();
            progress.hide();
        }

    }
    }

