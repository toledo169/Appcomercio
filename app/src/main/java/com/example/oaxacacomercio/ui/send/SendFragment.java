package com.example.oaxacacomercio.ui.send;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
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
import com.example.oaxacacomercio.Adapter.ZonaAdapter;
import com.example.oaxacacomercio.Detalles.DetallesActividadActivity;
import com.example.oaxacacomercio.Detalles.DetallesMapaZonaActivity;
import com.example.oaxacacomercio.Detalles.DetallesZonaActivity;
import com.example.oaxacacomercio.Helper.MySwipeHelper;
import com.example.oaxacacomercio.Helper.MybuttonClickListener;
import com.example.oaxacacomercio.MainActivity;
import com.example.oaxacacomercio.Modelos.User;
import com.example.oaxacacomercio.R;
import com.example.oaxacacomercio.Modelos.Zona;
import com.example.oaxacacomercio.Ventanas;
import com.example.oaxacacomercio.inicio;
import com.example.oaxacacomercio.ui.gallery.GalleryFragment;
import com.example.oaxacacomercio.ui.home.HomeFragment;
import com.mapbox.services.android.navigation.ui.v5.NavigationContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;
    RecyclerView recyclerViewzonas;
    ArrayList<Zona> listazona;
    JsonRequest jsonObjectRequest;
    RequestQueue request;
    private ArrayList<Double> lat = new ArrayList<>();
    private ArrayList<Double> lon = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private String idZona;
    private String opcion = "zonas";
    ZonaAdapter adapter;
    AlertDialog mDialog;
    SweetAlertDialog sweetAlertDialog;
    NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        listazona = new ArrayList<>();
        recyclerViewzonas = (RecyclerView) root.findViewById(R.id.idRecyclerzona);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewzonas.setLayoutManager(layoutManager);
        recyclerViewzonas.setHasFixedSize(true);

        adapter = new ZonaAdapter(listazona, getContext());
        recyclerViewzonas.setAdapter(adapter);
        request = Volley.newRequestQueue(getContext());
        MySwipeHelper swipeHelper = new MySwipeHelper(getContext(), recyclerViewzonas, 200) {
            @Override
            public void instanciateMyButton(final RecyclerView.ViewHolder viewHolder, List<Mybutton> buffer) {
                buffer.add(new Mybutton(getContext(),
                        "Vendedores",
                        35,
                        0,
                        Color.parseColor("#5d2442"),
                        new MybuttonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Intent intent = new Intent(getContext(), DetallesZonaActivity.class);
                                intent.putExtra("id_zona", listazona.get(viewHolder.getAdapterPosition()).getId());
                                intent.putExtra("nombre", listazona.get(viewHolder.getAdapterPosition()).getNombre());
                                getContext().startActivity(intent);
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
                                Intent intent = new Intent(getContext(), DetallesMapaZonaActivity.class);
                                intent.putExtra("id_zona", listazona.get(viewHolder.getAdapterPosition()).getId());
                                intent.putExtra("nombre", listazona.get(viewHolder.getAdapterPosition()).getNombre());
                                getContext().startActivity(intent);

                            }
                        }
                ));
            }
        };
        ejecutarservicio();
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
        String url = "http://192.168.10.233/api/Usuario/listarzona/";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Zona zona = null;
                JSONArray json = response.optJSONArray("zonas");
                try {
                    for (int i = 0; i < json.length(); i++) {
                        zona = new Zona();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
                        zona.setNombre(jsonObject.optString("nombre"));
                        zona.setId(jsonObject.optInt("id_zona"));
                        listazona.add(zona);
                    }
                    mDialog.hide();
                    recyclerViewzonas.setAdapter(adapter);

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
                    //        intent.putExtra(HomeFragment.fotoperfil, user.getImage());
                            startActivity(intent);
                            getActivity().finish();
                            // FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment());
                            // fragmentTransaction.commit();
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
                      //  intent.putExtra(HomeFragment.fotoperfil, user.getImage());
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
}