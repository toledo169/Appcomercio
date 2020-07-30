package com.example.oaxacacomercio.ui.slideshow;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.oaxacacomercio.Adapter.VendedorAdapter;
import com.example.oaxacacomercio.MainActivity;
import com.example.oaxacacomercio.Modelos.Actividad;
import com.example.oaxacacomercio.Modelos.User;
import com.example.oaxacacomercio.Modelos.Vendedor;
import com.example.oaxacacomercio.R;
import com.example.oaxacacomercio.Ventanas;
import com.example.oaxacacomercio.ui.gallery.GalleryFragment;
import com.example.oaxacacomercio.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    RecyclerView recyclerViewvendedores;
    ArrayList<Vendedor> listavendedores;
    ArrayList<Vendedor> listauxiliar;
    JsonRequest jsonObjectRequest;
    RequestQueue request;
    private LinearLayoutManager layoutManager;
    VendedorAdapter adapter;
    private EditText search;
    AlertDialog mDialog;
    SweetAlertDialog sweetAlertDialog;
    FloatingActionButton totalvendedor;
    String fechatotal;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        listavendedores = new ArrayList<>();
        listauxiliar = new ArrayList<>();
        recyclerViewvendedores = (RecyclerView) root.findViewById(R.id.idRecyclerVendedor);
        search = (EditText) root.findViewById(R.id.searchusuario);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewvendedores.setLayoutManager(layoutManager);
        totalvendedor = (FloatingActionButton) root.findViewById(R.id.vendedorestotales);
        recyclerViewvendedores.setHasFixedSize(true);
        adapter = new VendedorAdapter(listavendedores, getContext());
        recyclerViewvendedores.setAdapter(adapter);
        request = Volley.newRequestQueue(getContext());
        ejecutarservicio();
        final Calendar c = Calendar.getInstance();
        int año = c.get(Calendar.YEAR);
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        fechatotal = formatter.format(c.getTime());
        search.addTextChangedListener(new TextWatcher() {
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
        return root;
    }

    public void ejecutarservicio() {
        mDialog = new SpotsDialog.Builder()
                .setContext(getContext())
                .setMessage("Espere un momento")
                .setCancelable(false).build();
        mDialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDialog.dismiss();

            }
        }, 3000);
        String url = "http://192.168.0.9/api/Usuario/vendedoreslistar/";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Vendedor vendedor = null;
                JSONArray json = response.optJSONArray("vendedores");
                try {
                    for (int i = 0; i < json.length(); i++) {
                        vendedor = new Vendedor(getContext());
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
                        listavendedores.add(vendedor);
                        listauxiliar.add(vendedor);
                    }
                    mDialog.hide();
                    //   sDialog.hide();
                    recyclerViewvendedores.setAdapter(adapter);
                    totalvendedor.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                            sweetAlertDialog.setTitleText("Total de vendedores");
                            sweetAlertDialog.setContentText("El dia " + fechatotal + " el número total de vendedores es " + listavendedores.size());
                            sweetAlertDialog.setCustomImage(R.drawable.vendedorestotal);
                            sweetAlertDialog.setContentTextSize(15);
                            sweetAlertDialog.setCancelable(true);
                            sweetAlertDialog.setCanceledOnTouchOutside(true);
                            sweetAlertDialog.show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    final User user = new User(getContext());
                    sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                    sweetAlertDialog.setTitleText("Lo sentimos");
                    sweetAlertDialog.setContentText("En este momento no se puede realizar su petición");
                    sweetAlertDialog.setContentTextSize(15);
                    sweetAlertDialog.setCancelable(false);
                    sweetAlertDialog.setConfirmText("Volver a intentarlo");
                    sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            Intent intent = new Intent(getContext(), Ventanas.class);
                            intent.putExtra(GalleryFragment.numexpediente, user.getAdminsecre());
                            intent.putExtra(GalleryFragment.correoe, user.getCorreoelectronico());
                            intent.putExtra(HomeFragment.apellido_paternos, user.getApellido_paterno());
                            intent.putExtra(HomeFragment.apellido_maternos, user.getApellido_materno());
                            intent.putExtra(HomeFragment.nombres, user.getNombre());
                            intent.putExtra(HomeFragment.correo, user.getCorreoelectronico());
                            intent.putExtra(HomeFragment.cargo, user.getCargo());
                            intent.putExtra(HomeFragment.municipio, user.getMunicipio());
                            intent.putExtra(HomeFragment.fotoperfil, user.getImage());
                            startActivity(intent);
                            getActivity().finish();
                            sweetAlertDialog.dismiss();
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
                final User user = new User(getContext());
                sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                sweetAlertDialog.setTitleText("Lo sentimos");
                sweetAlertDialog.setContentText("En este momento no se puede realizar su petición");
                sweetAlertDialog.setContentTextSize(15);
                sweetAlertDialog.setCancelable(false);
                sweetAlertDialog.setConfirmText("Volver a intentarlo");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Intent intent = new Intent(getContext(), Ventanas.class);
                        intent.putExtra(GalleryFragment.numexpediente, user.getAdminsecre());
                        intent.putExtra(GalleryFragment.correoe, user.getCorreoelectronico());
                        intent.putExtra(HomeFragment.apellido_paternos, user.getApellido_paterno());
                        intent.putExtra(HomeFragment.apellido_maternos, user.getApellido_materno());
                        intent.putExtra(HomeFragment.nombres, user.getNombre());
                        intent.putExtra(HomeFragment.correo, user.getCorreoelectronico());
                        intent.putExtra(HomeFragment.cargo, user.getCargo());
                        intent.putExtra(HomeFragment.municipio, user.getMunicipio());
                        intent.putExtra(HomeFragment.fotoperfil, user.getImage());
                        startActivity(intent);
                        getActivity().finish();
                        sweetAlertDialog.dismiss();

                    }


                });
                sweetAlertDialog.setCanceledOnTouchOutside(false);
                sweetAlertDialog.show();
                mDialog.hide();
                // sDialog.hide();

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

    public void buscador(String texto) {
        listavendedores.clear();
        for (int i = 0; i < listauxiliar.size(); i++) {
            if (listauxiliar.get(i).getNombrev().toLowerCase().contains(texto.toLowerCase()) || listauxiliar.get(i).getApellido_paterno().toLowerCase().contains(texto.toLowerCase())) {
                listavendedores.add(listauxiliar.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }
}

