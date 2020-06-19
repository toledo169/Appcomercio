package com.example.oaxacacomercio.Detalles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.example.oaxacacomercio.MainActivity;
import com.example.oaxacacomercio.Modelos.User;
import com.example.oaxacacomercio.Modelos.Vendedor;
import com.example.oaxacacomercio.R;
import com.example.oaxacacomercio.Ventanas;
import com.example.oaxacacomercio.ui.gallery.GalleryFragment;
import com.example.oaxacacomercio.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetallesZonaActivity extends AppCompatActivity  {
    RecyclerView recyclerViewDetalleszona;
    ArrayList<Vendedor> listavendedoresdetalleszona;
    ArrayList<Vendedor>listauxiliar;
    AlertDialog mDialog;
    JsonRequest jsonObjectRequest;
    RequestQueue request;
    private LinearLayoutManager layoutManager;
    DetallesZonaVendedorAdapter adapter;
    int claveZ;
    // TextView txtnombrez,txtClavezona;
    private EditText searchzv;
    SweetAlertDialog sweetAlertDialog;
    Context context=this;
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
        ejecutarservicio();
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
    public void ejecutarservicio(){
        mDialog=new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Espere un momento")
                .setCancelable(false).build();
        mDialog.show();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!DetallesZonaActivity.this.isFinishing()&&mDialog!=null) {
                    mDialog.dismiss();
                }
            }
        },3000);
        String url="http://192.168.0.8/api/Usuario/listarzonavendedor/"+claveZ;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Vendedor vendedor=null;
                JSONArray json=response.optJSONArray("zonasvend");
                try {
                    for (int i=0;i<json.length();i++){
                        vendedor=new Vendedor(context);
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
                    mDialog.hide();
                    //     progress.hide();
                    recyclerViewDetalleszona.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    final User user=new User(DetallesZonaActivity.this);
                    //    Toast.makeText(this,"no se ha podido establecer conexion"+" "+response,Toast.LENGTH_LONG).show();
                    sweetAlertDialog=new SweetAlertDialog(DetallesZonaActivity.this,SweetAlertDialog.ERROR_TYPE);
                    sweetAlertDialog.setTitleText("Lo sentimos");
                    sweetAlertDialog.setContentText("En este momento no se puede realizar su petición");
                    sweetAlertDialog.setContentTextSize(15);
                    sweetAlertDialog.setCancelable(false);
                    sweetAlertDialog.setConfirmText("volver a intentarlo");
                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent intent = new Intent(DetallesZonaActivity.this, Ventanas.class);
                            intent.putExtra(GalleryFragment.numexpediente,user.getAdminsecre());
                            intent.putExtra(GalleryFragment.correoe,user.getCorreoelectronico());
                            intent.putExtra(HomeFragment.apellido_paternos,user.getApellido_paterno());
                            intent.putExtra(HomeFragment.apellido_maternos,user.getApellido_materno());
                            intent.putExtra(HomeFragment.nombres,user.getNombre());
                            intent.putExtra(HomeFragment.correo,user.getCorreoelectronico());
                            intent.putExtra(HomeFragment.cargo,user.getCargo());
                            intent.putExtra(HomeFragment.municipio,user.getMunicipio());
                            startActivity(intent);
                            finish();
                        }
                    });
                    sweetAlertDialog.setCanceledOnTouchOutside(false);
                    sweetAlertDialog.show();
                    mDialog.hide();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                sweetAlertDialog=new SweetAlertDialog(DetallesZonaActivity.this,SweetAlertDialog.ERROR_TYPE);
                sweetAlertDialog.setTitleText("Lo sentimos");
                sweetAlertDialog.setContentText("En este momento no se puede realizar su petición");
                sweetAlertDialog.setContentTextSize(15);
                sweetAlertDialog.setCancelable(false);
                sweetAlertDialog.setConfirmText("volver a intentarlo");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment());
                        fragmentTransaction.commit();
                    }
                });
                sweetAlertDialog.setCanceledOnTouchOutside(false);
                sweetAlertDialog.show();
                mDialog.hide();
            }
        });
        request.add(jsonObjectRequest);
    }
/*
    private void cargarwebservice() {

        // cuarto xoxo http://192.168.0.11/api/Usuario/listarorg
        //casa angel 192.168.0.23
        jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

        }
    }*/
    public void buscador(String texto){
        listavendedoresdetalleszona.clear();

        for (int i=0;i<listauxiliar.size();i++){
            if (listauxiliar.get(i).getNombrev().toLowerCase().contains(texto.toLowerCase())||listauxiliar.get(i).getApellido_paterno().toLowerCase().contains(texto.toLowerCase())){
                listavendedoresdetalleszona.add(listauxiliar.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if ( sweetAlertDialog!=null &&sweetAlertDialog.isShowing() ){
            sweetAlertDialog.dismiss();
        }
    }
}

