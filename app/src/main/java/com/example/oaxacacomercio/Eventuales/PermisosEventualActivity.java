package com.example.oaxacacomercio.Eventuales;

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
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.oaxacacomercio.Adapter.PermisosAdapter;
import com.example.oaxacacomercio.Detalles.DetallesZonaActivity;
import com.example.oaxacacomercio.Helper.MySwipeHelper;
import com.example.oaxacacomercio.Helper.MybuttonClickListener;
import com.example.oaxacacomercio.MainActivity;
import com.example.oaxacacomercio.Mapas.MapaPermiso;
import com.example.oaxacacomercio.Modelos.Permisos;
import com.example.oaxacacomercio.Modelos.User;
import com.example.oaxacacomercio.R;
import com.example.oaxacacomercio.Ventanas;
import com.example.oaxacacomercio.ui.gallery.GalleryFragment;
import com.example.oaxacacomercio.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class PermisosEventualActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
    RecyclerView recyclerViewzonas;
    ArrayList<Permisos> listapermisos;
    ArrayList<Permisos> listaauxiliar;
    AlertDialog mDialog;
    int i=-1;
    JsonRequest jsonObjectRequest;
    RequestQueue request;
    private LinearLayoutManager layoutManager;
    PermisosAdapter adapter;
    private EditText sercho;
    SweetAlertDialog sweetAlertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permisos_eventual);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        listapermisos = new ArrayList<>();
        listaauxiliar = new ArrayList<>();
        recyclerViewzonas = (RecyclerView)findViewById(R.id.idRecyclerpermisosnotif);
        sercho = (EditText) findViewById(R.id.bucarpermisosnotif);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewzonas.setLayoutManager(layoutManager);
        recyclerViewzonas.setHasFixedSize(true);
        adapter = new PermisosAdapter(listapermisos, this);
        recyclerViewzonas.setAdapter(adapter);
        Toolbar toolbar = findViewById(R.id.toolbarpermnotif);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Eventos del dia");
        request = Volley.newRequestQueue(this);
        cargarwebservice();
        final MySwipeHelper swipeHelper = new MySwipeHelper(this, recyclerViewzonas, 200) {
            @Override
            public void instanciateMyButton(final RecyclerView.ViewHolder viewHolder, final List<Mybutton> buffer) {
                buffer.add(new Mybutton(PermisosEventualActivity.this,
                        "Detalles",
                        40,
                        0,
                        Color.parseColor("#b34766"),
                        new MybuttonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Intent intent = new Intent(PermisosEventualActivity.this, MapaPermiso.class);
                                intent.putExtra("latitud", String.valueOf(listapermisos.get(viewHolder.getAdapterPosition()).getLatitud()));
                                intent.putExtra("longitud", String.valueOf(listapermisos.get(viewHolder.getAdapterPosition()).getLongitud()));
                                intent.putExtra("latitud_fin", String.valueOf(listapermisos.get(viewHolder.getAdapterPosition()).getLatitudfinal()));
                                intent.putExtra("longitud_fin", String.valueOf(listapermisos.get(viewHolder.getAdapterPosition()).getLongitudfinal()));
                                intent.putExtra("giro",listapermisos.get(viewHolder.getAdapterPosition()).getGiro());
                                startActivity(intent);
                            }
                        }
                ));
            }
        };
        sercho.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                buscador("" + charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
    private void cargarwebservice() {
        mDialog=new SpotsDialog.Builder()
                .setContext(PermisosEventualActivity.this)
                .setMessage("Espere un momento")
                .setCancelable(false).build();
        mDialog.show();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDialog.dismiss();
            }
        },3000);
        String url = "http://192.168.0.8/api/Usuario/permisomapa/";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            if (item.getItemId() == android.R.id.home) {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        sweetAlertDialog=new SweetAlertDialog(PermisosEventualActivity.this,SweetAlertDialog.ERROR_TYPE);
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

    @Override
    public void onResponse(JSONObject response) {
        Permisos organizacion = null;
        JSONArray json = response.optJSONArray("anuales");
        try {
            for (int i = 0; i < json.length(); i++) {
                organizacion = new Permisos();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                organizacion.setGiro(jsonObject.optString("giro"));
                organizacion.setHorainicio(Time.valueOf(jsonObject.optString("hora_inicio")));
                organizacion.setHorafin(Time.valueOf(jsonObject.optString("hora_fin")));
                organizacion.setLatitud(jsonObject.optDouble("latitud"));
                organizacion.setLongitud(jsonObject.optDouble("longitud"));
                organizacion.setLatitudfinal(jsonObject.optDouble("latitud_fin"));
                organizacion.setLongitudfinal(jsonObject.optDouble("longitud_fin"));
                listapermisos.add(organizacion);
                listaauxiliar.add(organizacion);
            }
            mDialog.hide();
            recyclerViewzonas.setAdapter(adapter);
            System.out.println("la lista contiene"+listapermisos.size());
        } catch (JSONException e) {
            e.printStackTrace();
            final User user=new User(PermisosEventualActivity.this);
            sweetAlertDialog=new SweetAlertDialog(PermisosEventualActivity.this,SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setTitleText("Lo sentimos");
            sweetAlertDialog.setContentText("En este momento no se puede realizar su petición");
            sweetAlertDialog.setContentTextSize(15);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setConfirmText("volver a intentarlo");
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    Intent intent = new Intent(PermisosEventualActivity.this, Ventanas.class);
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
                 //   FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment());
                  //  fragmentTransaction.commit();
                }
            });
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.show();
            mDialog.hide();
        }
    }
    public void buscador(String texto) {
        listapermisos.clear();
        for (int i = 0; i < listaauxiliar.size(); i++) {
            if (listaauxiliar.get(i).getGiro().toLowerCase().contains(texto.toLowerCase())) {
                listapermisos.add(listaauxiliar.get(i));
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


