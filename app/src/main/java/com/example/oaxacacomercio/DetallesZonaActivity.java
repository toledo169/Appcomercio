package com.example.oaxacacomercio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.oaxacacomercio.Adapter.DetallesVendedorAdapter;
import com.example.oaxacacomercio.Adapter.DetallesZonaVendedorAdapter;
import com.example.oaxacacomercio.Modelos.Vendedor;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetallesZonaActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{
    RecyclerView recyclerViewDetalleszona;
    ArrayList<Vendedor> listavendedoresdetalleszona;
    ProgressDialog progress;
    JsonRequest jsonObjectRequest;
    RequestQueue request;
    private LinearLayoutManager layoutManager;
    DetallesZonaVendedorAdapter adapter;
    TextView txtnombrez,txtClavezona;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_zona);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        String nombreZ=getIntent().getExtras().getString("nombre");
        int claveZ=getIntent().getExtras().getInt("id_zona");

        CollapsingToolbarLayout collapsingToolbarLayout=findViewById(R.id.collapsingtoolbar_idzona);
        collapsingToolbarLayout.setTitleEnabled(true);
        txtnombrez=(TextView)findViewById(R.id.txtnombrezonadetalles);
        txtClavezona=(TextView)findViewById(R.id.txtDocumentozonadetalles);

        txtnombrez.setText(nombreZ);
        txtClavezona.setText(String.valueOf(claveZ));
        collapsingToolbarLayout.setTitle(nombreZ);
        listavendedoresdetalleszona=new ArrayList<>();

        recyclerViewDetalleszona= (RecyclerView) findViewById(R.id.idRecyclerdetalleszonavendedor);
        layoutManager= new LinearLayoutManager(this);
        recyclerViewDetalleszona.setLayoutManager(layoutManager);
        recyclerViewDetalleszona.setHasFixedSize(true);

        adapter=new DetallesZonaVendedorAdapter(listavendedoresdetalleszona,this);
        recyclerViewDetalleszona.setAdapter(adapter);
        request = Volley.newRequestQueue(this);
        cargarwebservice();
    }

    private void cargarwebservice() {
        progress=new ProgressDialog(this);
        progress.setMessage("Consultando...");
        progress.show();
        String url="http://192.168.0.11/api/Usuario/listarzonavendedor/"+txtClavezona.getText().toString();
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
                listavendedoresdetalleszona.add(vendedor);
            }
            progress.hide();
            recyclerViewDetalleszona.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,"no se ha podido establecer conexion"+" "+response,Toast.LENGTH_LONG).show();
            progress.hide();
        }
    }

    }

