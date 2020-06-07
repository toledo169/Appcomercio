package com.example.oaxacacomercio.Detalles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.example.oaxacacomercio.MainActivity;
import com.example.oaxacacomercio.Mapas.MapaActActivity;
import com.example.oaxacacomercio.Mapas.MapaorganizacionActivity;
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

public class DetallesMapaActividaActivity extends AppCompatActivity  implements Response.Listener<JSONObject>,Response.ErrorListener {
    ArrayList<Vendedor> listavendedoresdetallesact;
    ArrayList<Double>lat=new ArrayList<>();
    ArrayList<Double>log=new ArrayList<>();
    // ArrayList<Vendedor> listauxiliar;
    AlertDialog mDialog;
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
        tvnombre=(TextView)findViewById(R.id.txtnombreactdetalles1);
        tvclave=(TextView)findViewById(R.id.txtDocumentoactdetalles1);
        tvnombre.setText(name);
        tvclave.setText(String.valueOf(claved));
        listavendedoresdetallesact=new ArrayList<>();
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
        mDialog=new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Espere un momento")
                .setCancelable(false).build();
        mDialog.show();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                if (!DetallesMapaActividaActivity.this.isFinishing()&&mDialog!=null) {
                    mDialog.dismiss();
                }
            }
        },3000);
            String url="http://192.168.0.8/api/Usuario/listaractividadesvendedor/"+tvclave.getText().toString();
            jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url,
                    null,this,this);
            request.add(jsonObjectRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        SweetAlertDialog sweetAlertDialog=new SweetAlertDialog(DetallesMapaActividaActivity.this,SweetAlertDialog.ERROR_TYPE);
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
                listavendedoresdetallesact.add(vendedor);
                lat.add(vendedor.getLatitud());
                log.add(vendedor.getLongitud());
                nom.add(vendedor.getNombrev());
            }
            mDialog.hide();

        } catch (JSONException e) {
            e.printStackTrace();
            final User user=new User(DetallesMapaActividaActivity.this);
            //    Toast.makeText(this,"no se ha podido establecer conexion"+" "+response,Toast.LENGTH_LONG).show();
            SweetAlertDialog sweetAlertDialog=new SweetAlertDialog(DetallesMapaActividaActivity.this,SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setTitleText("Lo sentimos");
            sweetAlertDialog.setContentText("En este momento no se puede realizar su petición");
            sweetAlertDialog.setContentTextSize(15);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setConfirmText("volver a intentarlo");
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    Intent intent = new Intent(DetallesMapaActividaActivity.this, Ventanas.class);
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
    }

