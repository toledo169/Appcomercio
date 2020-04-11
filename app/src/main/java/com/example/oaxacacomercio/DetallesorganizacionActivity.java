package com.example.oaxacacomercio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.example.oaxacacomercio.Adapter.VendedorAdapter;
import com.example.oaxacacomercio.Modelos.Vendedor;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetallesorganizacionActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    RecyclerView recyclerViewDetalles;
    ArrayList<Vendedor> listavendedoresdetalles;
    ProgressDialog progress;
    JsonRequest jsonObjectRequest;
    RequestQueue request;
    private LinearLayoutManager layoutManager;
    TextView tvclave,tvnombre;
    DetallesVendedorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallesorganizacion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        String name=getIntent().getExtras().getString("nombre_organizacion");
        String dirigente=getIntent().getExtras().getString("nombre_dirigente");
        int claved=getIntent().getExtras().getInt("id_organizacion");

        CollapsingToolbarLayout collapsingToolbarLayout=findViewById(R.id.collapsingtoolbar_id);
        collapsingToolbarLayout.setTitleEnabled(true);
         tvnombre=(TextView)findViewById(R.id.Nombrevendedordetalles);
        TextView tvdirigente=(TextView)findViewById(R.id.otxtProfesion);
        tvclave=(TextView)findViewById(R.id.otxclave);

        tvnombre.setText(name);
        tvdirigente.setText(dirigente);
        tvclave.setText(String.valueOf(claved));
        collapsingToolbarLayout.setTitle(name);
        listavendedoresdetalles=new ArrayList<>();

        recyclerViewDetalles= (RecyclerView) findViewById(R.id.idRecyclerdetallesvendedor);
        layoutManager= new LinearLayoutManager(this);
        recyclerViewDetalles.setLayoutManager(layoutManager);
        recyclerViewDetalles.setHasFixedSize(true);

        adapter=new DetallesVendedorAdapter(listavendedoresdetalles,this);
        recyclerViewDetalles.setAdapter(adapter);
        request = Volley.newRequestQueue(this);
        cargarwebservice();

    }

    private void cargarwebservice() {
        progress=new ProgressDialog(this);
        progress.setMessage("Consultando...");
        progress.show();
        String url="http://192.168.0.11/api/Usuario/deta/"+tvclave.getText().toString();
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
        JSONArray json=response.optJSONArray("permisos");
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
                listavendedoresdetalles.add(vendedor);
            }
            progress.hide();
            recyclerViewDetalles.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,"no se ha podido establecer conexion"+" "+response,Toast.LENGTH_LONG).show();
            progress.hide();
        }
    }
    }

