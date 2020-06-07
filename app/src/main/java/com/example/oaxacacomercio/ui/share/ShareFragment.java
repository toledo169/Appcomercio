package com.example.oaxacacomercio.ui.share;

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
import com.example.oaxacacomercio.Adapter.ActividadAdapter;
import com.example.oaxacacomercio.Adapter.ZonaAdapter;
import com.example.oaxacacomercio.Detalles.DetallesActividadActivity;
import com.example.oaxacacomercio.Detalles.DetallesMapaActividaActivity;
import com.example.oaxacacomercio.Detalles.DetallesMapaZonaActivity;
import com.example.oaxacacomercio.Detalles.DetallesZonaActivity;
import com.example.oaxacacomercio.Helper.MySwipeHelper;
import com.example.oaxacacomercio.Helper.MybuttonClickListener;
import com.example.oaxacacomercio.MainActivity;
import com.example.oaxacacomercio.Modelos.Actividad;
import com.example.oaxacacomercio.Modelos.Zona;
import com.example.oaxacacomercio.R;
import com.example.oaxacacomercio.Ventanas;
import com.example.oaxacacomercio.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShareFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    private ShareViewModel shareViewModel;
    RecyclerView recyclerViewact;
    ArrayList<Actividad> listacti;
    JsonRequest jsonObjectRequest;
    RequestQueue request;
    private LinearLayoutManager layoutManager;
    ActividadAdapter adapter;
    NavController navController;
    AlertDialog mDialog;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);
        listacti = new ArrayList<>();
        recyclerViewact = (RecyclerView) root.findViewById(R.id.actividadescom);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewact.setLayoutManager(layoutManager);
        recyclerViewact.setHasFixedSize(true);

        adapter = new ActividadAdapter(listacti, getContext());
        recyclerViewact.setAdapter(adapter);
        request = Volley.newRequestQueue(getContext());
        MySwipeHelper swipeHelper = new MySwipeHelper(getContext(), recyclerViewact, 200) {
            @Override
            public void instanciateMyButton(final RecyclerView.ViewHolder viewHolder, List<Mybutton> buffer) {
                buffer.add(new Mybutton(getContext(),
                        "Vendedores",
                        40,
                        0,
                        Color.parseColor("#5d2442"),
                        new MybuttonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Intent intent = new Intent(getContext(), DetallesActividadActivity.class);
                                intent.putExtra("id_actividad", listacti.get(viewHolder.getAdapterPosition()).getId());
                                intent.putExtra("nombre_actividad", listacti.get(viewHolder.getAdapterPosition()).getNombre());
                                getContext().startActivity(intent);
                                Toast.makeText(getContext(), "Detalles", Toast.LENGTH_SHORT).show();
                            }
                        }
                ));
                buffer.add(new Mybutton(getContext(),
                        "Detalles",
                        40,
                        0,
                        Color.parseColor("#b34766"),
                        new MybuttonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Intent intent = new Intent(getContext(), DetallesMapaActividaActivity.class);
                                intent.putExtra("id_actividad", listacti.get(viewHolder.getAdapterPosition()).getId());
                                intent.putExtra("nombre_actividad", listacti.get(viewHolder.getAdapterPosition()).getNombre());
                                getContext().startActivity(intent);
                            }
                        }
                ));
            }
        };
        cargarwebservice();

        return root;
    }

    private void cargarwebservice() {
        mDialog=new SpotsDialog.Builder()
                .setContext(getContext())
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
        String url = "http://192.168.0.8/api/Usuario/listaractividades";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        SweetAlertDialog sweetAlertDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE);
        sweetAlertDialog.setTitleText("Lo sentimos");
        sweetAlertDialog.setContentText("En este momento no se puede realizar su petición");
        sweetAlertDialog.setContentTextSize(15);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setConfirmText("volver a intentarlo");
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment());
                fragmentTransaction.commit();
            }
        });
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.show();
        mDialog.hide();
    }

    @Override
    public void onResponse(JSONObject response) {

        Actividad actividad = null;
        JSONArray json = response.optJSONArray("actividades");
        try {
            for (int i = 0; i < json.length(); i++) {
                actividad = new Actividad();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                actividad.setNombre(jsonObject.optString("nombre_actividad"));
                actividad.setId(jsonObject.optInt("id_actividad"));
                listacti.add(actividad);
            }
            mDialog.hide();
            recyclerViewact.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
            SweetAlertDialog sweetAlertDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setTitleText("Lo sentimos");
            sweetAlertDialog.setContentText("En este momento no se puede realizar su petición");
            sweetAlertDialog.setContentTextSize(15);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setConfirmText("volver a intentarlo");
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment());
                    fragmentTransaction.commit();
                }
            });
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.show();
            mDialog.hide();
        }
    }
}
