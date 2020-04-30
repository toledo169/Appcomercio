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
import com.example.oaxacacomercio.Mapas.MapaorganizacionActivity;
import com.example.oaxacacomercio.Modelos.Vendedor;
import com.example.oaxacacomercio.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetallesMapaOrganizacionActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{
    ArrayList<Vendedor> listavendedoresdetalles;
    ArrayList<Double>lat=new ArrayList<>();
    ArrayList<Double>log=new ArrayList<>();
    ArrayList<String>nom=new ArrayList<>();
    // ArrayList<Vendedor> listauxiliar;
    ProgressDialog progress;
    JsonRequest jsonObjectRequest;
    RequestQueue request;
    TextView tvclave,tvnombre;
    String name;
    String nombre;
    //private EditText serchvo;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_mapa_organizacion);
        Toolbar toolbar = findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        name=getIntent().getExtras().getString("nombre_organizacion");
        getSupportActionBar().setTitle(name);
        String dirigente=getIntent().getExtras().getString("nombre_dirigente");
        int claved=getIntent().getExtras().getInt("id_organizacion");
        //-----------
        tvnombre=(TextView)findViewById(R.id.txtnombrezonadetalles2);
        TextView tvdirigente=(TextView)findViewById(R.id.otxtProfesion2);
        tvclave=(TextView)findViewById(R.id.txtDocumentozonadetalles2);

        tvnombre.setText(name);
        tvdirigente.setText(dirigente);
        tvclave.setText(String.valueOf(claved));
        listavendedoresdetalles=new ArrayList<>();
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
        String url="http://192.168.0.11/api/Usuario/deta/"+tvclave.getText().toString();
        // cuarto xoxo http://192.168.0.11/api/Usuario/listarorg
        //casa angel 192.168.0.23
        jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }
    public void iralmapao(View view){
        Intent intent=new Intent(DetallesMapaOrganizacionActivity.this, MapaorganizacionActivity.class);
        intent.putExtra("lat", lat);
        intent.putExtra("log", log);
        intent.putExtra("nom",nom);
        intent.putExtra("nombre_organizacion",name);
        intent.putExtra("name",nombre);

        startActivity(intent);
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
                listavendedoresdetalles.add(vendedor);
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
