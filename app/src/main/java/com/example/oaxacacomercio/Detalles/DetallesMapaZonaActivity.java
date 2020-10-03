package com.example.oaxacacomercio.Detalles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.transition.Transition;
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
import com.example.oaxacacomercio.Mapas.MapaActivity;
import com.example.oaxacacomercio.Modelos.User;
import com.example.oaxacacomercio.Modelos.Vendedor;
import com.example.oaxacacomercio.R;
import com.example.oaxacacomercio.Ventanas;
import com.example.oaxacacomercio.ui.gallery.GalleryFragment;
import com.example.oaxacacomercio.ui.home.HomeFragment;
import com.example.oaxacacomercio.ui.send.SendFragment;
import com.example.oaxacacomercio.ui.send.SendViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetallesMapaZonaActivity extends AppCompatActivity {
    ArrayList<Vendedor> listavendedoresdetalleszona;
    ArrayList<Double> lat = new ArrayList<>();
    ArrayList<Double> log = new ArrayList<>();
    ArrayList<Double> latitudZona = new ArrayList<>();
    ArrayList<Double> longitudzona = new ArrayList<>();
    ArrayList<String> nom = new ArrayList<>();
    // ArrayList<Vendedor>listauxiliar;
    AlertDialog mDialog;
    JsonRequest jsonObjectRequest;
    RequestQueue request;
    int claveZ;
    String nombreZ;
    String name;
    Button mapaven;
    TextView txtnombrez, txtClavezona,tvtotal;
    private Transition transition;
    Toolbar toolbar;
    SweetAlertDialog sweetAlertDialog;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_mapa_zona);
        toolbar = findViewById(R.id.toolbarrzonabien);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        claveZ = getIntent().getExtras().getInt("id_zona");
        nombreZ = getIntent().getExtras().getString("nombre");
        mapaven = (Button) findViewById(R.id.buttonmapa);
        getSupportActionBar().setTitle(nombreZ);
        listavendedoresdetalleszona = new ArrayList<>();
        txtnombrez = (TextView) findViewById(R.id.txtnombrezonadetalles1);
        txtClavezona = (TextView) findViewById(R.id.txtDocumentozonadetalles1);
        tvtotal=(TextView) findViewById(R.id.txttotalvzonadetalles1);
        txtnombrez.setText(nombreZ);
        txtClavezona.setText(String.valueOf(claveZ));
        request = Volley.newRequestQueue(this);

        ejecutarservicio();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void iralmapa(View v) {
        Intent intent = new Intent(DetallesMapaZonaActivity.this, MapaActivity.class);
        if (claveZ == 1) {
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
            intent.putExtra("nom", nom);
            intent.putExtra("nombre", nombreZ);
            intent.putExtra("name", name);
            intent.putExtra("id_zona", claveZ);
            startActivity(intent);
        } else if (claveZ == 2) {
            //17.062413, -96.722457
            //17.063644, -96.728261
            //17.057080, -96.729589
            //17.056316, -96.725745
            //17.055361, -96.725945
            //17.055185, -96.724953
            //17.056178, -96.724727
            //17.056046, -96.723841
            //17.062413, -96.722457
            latitudZona.add(17.062413);
            longitudzona.add(-96.722457);
            latitudZona.add(17.063644);
            longitudzona.add(-96.728261);
            latitudZona.add(17.057080);
            longitudzona.add(-96.729589);
            latitudZona.add(17.056316);
            longitudzona.add(-96.725745);
            latitudZona.add(17.055361);
            longitudzona.add(-96.725945);
            latitudZona.add(17.055185);
            longitudzona.add(-96.724953);
            latitudZona.add(17.056178);
            longitudzona.add(-96.724727);
            latitudZona.add(17.056046);
            longitudzona.add(-96.723841);
            latitudZona.add(17.062413);
            longitudzona.add(-96.722457);
            intent.putExtra("lat", lat);
            intent.putExtra("log", log);
            intent.putExtra("latitudzona", latitudZona);
            intent.putExtra("longitudzona", longitudzona);
            intent.putExtra("nom", nom);
            intent.putExtra("nombre", nombreZ);
            intent.putExtra("name", name);
            intent.putExtra("id_zona", claveZ);
            startActivity(intent);
        } else if (claveZ == 3) {
            //17.057917, -96.731441
            //17.061186, -96.730719
            //17.061151, -96.733687
            ////17.057917, -96.731441
            latitudZona.add(17.057917);
            longitudzona.add(-96.731441);
            latitudZona.add(17.061186);
            longitudzona.add(-96.730719);
            latitudZona.add(17.061151);
            longitudzona.add(-96.733687);
            latitudZona.add(17.057917);
            longitudzona.add(-96.731441);
            intent.putExtra("lat", lat);
            intent.putExtra("log", log);
            intent.putExtra("latitudzona", latitudZona);
            intent.putExtra("longitudzona", longitudzona);
            intent.putExtra("nom", nom);
            intent.putExtra("nombre", nombreZ);
            intent.putExtra("name", name);
            intent.putExtra("id_zona", claveZ);
            startActivity(intent);
        } else if (claveZ == 4) {
            intent.putExtra("latitudzona", latitudZona);
            intent.putExtra("longitudzona", longitudzona);
            intent.putExtra("lat", lat);
            intent.putExtra("log", log);
            intent.putExtra("name", name);
            intent.putExtra("id_zona", claveZ);
            startActivity(intent);
        }
    }

    public void ejecutarservicio() {
        mDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Espere un momento")
                .setCancelable(false).build();
        mDialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!DetallesMapaZonaActivity.this.isFinishing() && mDialog != null) {
                    mDialog.dismiss();
                }
            }
        }, 3000);
        String url = "http://192.168.10.233/api/Usuario/listarzonavendedor/" + claveZ;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Vendedor vendedor = null;
                JSONArray json = response.optJSONArray("zonasvend");
                try {
                    for (int i = 0; i < json.length(); i++) {
                        vendedor = new Vendedor(context);
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
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
                    }
                    mDialog.hide();
                    tvtotal.setText(" "+ listavendedoresdetalleszona.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                    final User user = new User(DetallesMapaZonaActivity.this);
                    //    Toast.makeText(this,"no se ha podido establecer conexion"+" "+response,Toast.LENGTH_LONG).show();
                    sweetAlertDialog = new SweetAlertDialog(DetallesMapaZonaActivity.this, SweetAlertDialog.ERROR_TYPE);
                    sweetAlertDialog.setTitleText("Lo sentimos");
                    sweetAlertDialog.setContentText("En este momento no se puede realizar su petición");
                    sweetAlertDialog.setContentTextSize(15);
                    sweetAlertDialog.setCancelable(false);
                    sweetAlertDialog.setConfirmText("Volver a intentarlo");
                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent intent = new Intent(DetallesMapaZonaActivity.this, Ventanas.class);
                            intent.putExtra(GalleryFragment.numexpediente, user.getAdminsecre());
                            intent.putExtra(GalleryFragment.correoe, user.getCorreoelectronico());
                            intent.putExtra(HomeFragment.apellido_paternos, user.getApellido_paterno());
                            intent.putExtra(HomeFragment.apellido_maternos, user.getApellido_materno());
                            intent.putExtra(HomeFragment.nombres, user.getNombre());
                            intent.putExtra(HomeFragment.correo, user.getCorreoelectronico());
                            intent.putExtra(HomeFragment.cargo, user.getCargo());
                            intent.putExtra(HomeFragment.municipio, user.getMunicipio());
                           // intent.putExtra(HomeFragment.fotoperfil, user.getImage());
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
                final User user = new User(DetallesMapaZonaActivity.this);
                sweetAlertDialog = new SweetAlertDialog(DetallesMapaZonaActivity.this, SweetAlertDialog.ERROR_TYPE);
                sweetAlertDialog.setTitleText("Lo sentimos");
                sweetAlertDialog.setContentText("En este momento no se puede realizar su petición");
                sweetAlertDialog.setContentTextSize(15);
                sweetAlertDialog.setCancelable(false);
                sweetAlertDialog.setConfirmText("Volver a intentarlo");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Intent intent = new Intent(DetallesMapaZonaActivity.this, Ventanas.class);
                        intent.putExtra(GalleryFragment.numexpediente, user.getAdminsecre());
                        intent.putExtra(GalleryFragment.correoe, user.getCorreoelectronico());
                        intent.putExtra(HomeFragment.apellido_paternos, user.getApellido_paterno());
                        intent.putExtra(HomeFragment.apellido_maternos, user.getApellido_materno());
                        intent.putExtra(HomeFragment.nombres, user.getNombre());
                        intent.putExtra(HomeFragment.correo, user.getCorreoelectronico());
                        intent.putExtra(HomeFragment.cargo, user.getCargo());
                        intent.putExtra(HomeFragment.municipio, user.getMunicipio());
                       // intent.putExtra(HomeFragment.fotoperfil, user.getImage());
                        startActivity(intent);
                        finish();
                    }
                });
                sweetAlertDialog.setCanceledOnTouchOutside(false);
                sweetAlertDialog.show();
                mDialog.hide();
            }
        });
        request.add(jsonObjectRequest);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sweetAlertDialog != null && sweetAlertDialog.isShowing()) {
            sweetAlertDialog.dismiss();
        }
    }
}
