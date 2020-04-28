package com.example.oaxacacomercio.Detalles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.oaxacacomercio.Adapter.DetallesZonaVendedorAdapter;
import com.example.oaxacacomercio.Modelos.Vendedor;
import com.example.oaxacacomercio.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetallesZonaActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{
    RecyclerView recyclerViewDetalleszona;
    ArrayList<Vendedor> listavendedoresdetalleszona;
    ArrayList<Vendedor>listauxiliar;
    ProgressDialog progress;
    JsonRequest jsonObjectRequest;
    RequestQueue request;
    private LinearLayoutManager layoutManager;
    DetallesZonaVendedorAdapter adapter;
    int claveZ;
   // TextView txtnombrez,txtClavezona;
   private EditText searchzv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_zona);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        Toolbar toolbar = findViewById(R.id.toolbarprueba);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        String nombreZ=getIntent().getExtras().getString("nombre");
        getSupportActionBar().setTitle(nombreZ);
        claveZ=getIntent().getExtras().getInt("id_zona");

//        CollapsingToolbarLayout collapsingToolbarLayout=findViewById(R.id.collapsingtoolbar_idzona);
  //      collapsingToolbarLayout.setTitleEnabled(true);
     //   txtnombrez=(TextView)findViewById(R.id.txtnombrezonadetalles);
     //   txtClavezona=(TextView)findViewById(R.id.txtDocumentozonadetalles);

     //   txtnombrez.setText(nombreZ);
     //   txtClavezona.setText(String.valueOf(claveZ));
    //    collapsingToolbarLayout.setTitle(nombreZ);
        listavendedoresdetalleszona=new ArrayList<>();
        listauxiliar=new ArrayList<>();

        recyclerViewDetalleszona= (RecyclerView) findViewById(R.id.idRecyclerdetalleszonavendedor);
        searchzv=(EditText)findViewById(R.id.serchvenzona);
        layoutManager= new LinearLayoutManager(this);
        recyclerViewDetalleszona.setLayoutManager(layoutManager);
        recyclerViewDetalleszona.setHasFixedSize(true);

        adapter=new DetallesZonaVendedorAdapter(listavendedoresdetalleszona,this);
        recyclerViewDetalleszona.setAdapter(adapter);
        request = Volley.newRequestQueue(this);
        cargarwebservice();
       searchzv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                buscador(""+charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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
 //       progress=new ProgressDialog(this);
   //     progress.setMessage("Consultando...");
   //     progress.show();
        String url="http://192.168.0.11/api/Usuario/listarzonavendedor/"+claveZ;
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
     //   progress.hide();

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
                listauxiliar.add(vendedor);
            }
       //     progress.hide();
            recyclerViewDetalleszona.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,"no se ha podido establecer conexion"+" "+response,Toast.LENGTH_LONG).show();
//            progress.hide();
        }
    }
    public void buscador(String texto){
        listavendedoresdetalleszona.clear();

        for (int i=0;i<listauxiliar.size();i++){
            if (listauxiliar.get(i).getNombrev().toLowerCase().contains(texto.toLowerCase())||listauxiliar.get(i).getApellido_paterno().toLowerCase().contains(texto.toLowerCase())){
                listavendedoresdetalleszona.add(listauxiliar.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }

    }

