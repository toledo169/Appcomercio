package com.example.oaxacacomercio.ui.tools;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.example.oaxacacomercio.Adapter.OrganizacionAdapter;
import com.example.oaxacacomercio.Detalles.DetallesMapaOrganizacionActivity;
import com.example.oaxacacomercio.Detalles.DetallesorganizacionActivity;
import com.example.oaxacacomercio.Helper.MySwipeHelper;
import com.example.oaxacacomercio.Helper.MybuttonClickListener;
import com.example.oaxacacomercio.MainActivity;
import com.example.oaxacacomercio.Modelos.Organizacion;
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
import java.util.List;

public class ToolsFragment extends Fragment {
    private ToolsViewModel toolsViewModel;
    RecyclerView recyclerorganizaciones;
    ArrayList<Organizacion> listaorganizacion;
    ArrayList<Organizacion> listaauxiliar;
    JsonRequest jsonObjectRequest;
    RequestQueue request;
    private LinearLayoutManager layoutManager;
    OrganizacionAdapter adapter;
   // private EditText sercho;
    AlertDialog mDialog;
    SweetAlertDialog sweetAlertDialog;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        final View vista = inflater.inflate(R.layout.fragment_tools, container, false);
        listaorganizacion = new ArrayList<>();
        listaauxiliar = new ArrayList<>();
        recyclerorganizaciones = (RecyclerView) vista.findViewById(R.id.idRecycler);
     //   sercho = (EditText) vista.findViewById(R.id.bucarorganizaciones);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerorganizaciones.setLayoutManager(layoutManager);
        recyclerorganizaciones.setHasFixedSize(true);
        adapter = new OrganizacionAdapter(listaorganizacion, getContext());
        recyclerorganizaciones.setAdapter(adapter);
        request = Volley.newRequestQueue(getContext());
        final MySwipeHelper swipeHelper = new MySwipeHelper(getContext(), recyclerorganizaciones, 200) {
            @Override
            public void instanciateMyButton(final RecyclerView.ViewHolder viewHolder, final List<Mybutton> buffer) {
                buffer.add(new Mybutton(getContext(),
                        "Vendedores",
                        35,
                        0,
                        Color.parseColor("#5d2442"),
                        new MybuttonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Intent i = new Intent(getContext(), DetallesorganizacionActivity.class);
                                i.putExtra("id_organizacion", listaorganizacion.get(viewHolder.getAdapterPosition()).getDocumento());
                                i.putExtra("nombre_organizacion", listaorganizacion.get(viewHolder.getAdapterPosition()).getNombre());
                                i.putExtra("nombre_dirigente", listaorganizacion.get(viewHolder.getAdapterPosition()).getProfesion());
                                getContext().startActivity(i);
                            }
                        }
                ));
                buffer.add(new Mybutton(getContext(),
                        "Detalles",
                        35,
                        0,
                        Color.parseColor("#b34766"),
                        new MybuttonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Intent intent = new Intent(getContext(), DetallesMapaOrganizacionActivity.class);
                                intent.putExtra("id_organizacion", listaorganizacion.get(viewHolder.getAdapterPosition()).getDocumento());
                                intent.putExtra("nombre_organizacion", listaorganizacion.get(viewHolder.getAdapterPosition()).getNombre());
                                intent.putExtra("nombre_dirigente", listaorganizacion.get(viewHolder.getAdapterPosition()).getProfesion());
                                getContext().startActivity(intent);

                            }
                        }
                ));
            }
        };

        ejecutarservicio();
       /* sercho.addTextChangedListener(new TextWatcher() {
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
        */
        return vista;
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
        String url = "http://192.168.10.233/api/Usuario/listarorg/";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Organizacion organizacion = null;
                JSONArray json = response.optJSONArray("Organizacion");
                try {

                    for (int i = 0; i < json.length(); i++) {
                        organizacion = new Organizacion();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);

                        organizacion.setDocumento(jsonObject.optInt("id_organizacion"));
                        organizacion.setNombre(jsonObject.optString("nombre_organizacion"));
                        organizacion.setProfesion(jsonObject.optString("nombre_dirigente"));
                        listaorganizacion.add(organizacion);
                        listaauxiliar.add(organizacion);
                    }
                    mDialog.hide();
                    recyclerorganizaciones.setAdapter(adapter);
                    System.out.println(listaorganizacion);
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
                      //      intent.putExtra(HomeFragment.fotoperfil, user.getImage());
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
                    //    intent.putExtra(HomeFragment.fotoperfil, user.getImage());
                        startActivity(intent);
                        getActivity().finish();
                        sweetAlertDialog.dismiss();
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

    public void buscador(String texto) {
        listaorganizacion.clear();
        for (int i = 0; i < listaauxiliar.size(); i++) {
            if (listaauxiliar.get(i).getNombre().toLowerCase().contains(texto.toLowerCase()) || listaauxiliar.get(i).getProfesion().toLowerCase().contains(texto.toLowerCase())) {
                listaorganizacion.add(listaauxiliar.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }

}

