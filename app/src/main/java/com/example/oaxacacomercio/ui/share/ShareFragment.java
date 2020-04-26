package com.example.oaxacacomercio.ui.share;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.oaxacacomercio.Modelos.Actividad;
import com.example.oaxacacomercio.Modelos.Zona;
import com.example.oaxacacomercio.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShareFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {
    private ShareViewModel shareViewModel;
    RecyclerView recyclerViewact;
    ArrayList<Actividad>listacti;
    ProgressDialog progress;
    JsonRequest jsonObjectRequest;
    RequestQueue request;
    private LinearLayoutManager layoutManager;
    ActividadAdapter adapter;
    NavController navController;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);
     //   final TextView textView = root.findViewById(R.id.text_share);
      //  shareViewModel.getText().observe(this, new Observer<String>() {
          //  @Override
          //  public void onChanged(@Nullable String s) {
        //        textView.setText(s);
       //     }
      //  });
        listacti=new ArrayList<>();
        recyclerViewact=(RecyclerView)root.findViewById(R.id.actividadescom);
        layoutManager= new LinearLayoutManager(getActivity());
        //   layoutManager= new GridLayoutManager(getActivity(),2);
        recyclerViewact.setLayoutManager(layoutManager);
        recyclerViewact.setHasFixedSize(true);

        adapter=new ActividadAdapter(listacti,getContext());
        recyclerViewact.setAdapter(adapter);
        request= Volley.newRequestQueue(getContext());
        MySwipeHelper swipeHelper= new MySwipeHelper(getContext(),recyclerViewact,200) {
            @Override
            public void instanciateMyButton(final RecyclerView.ViewHolder viewHolder, List<Mybutton> buffer) {
                buffer.add(new Mybutton(getContext(),
                        "Detalles",
                        40,
                        0,
                        Color.parseColor("#5d2442"),
                        new MybuttonClickListener(){
                            @Override
                            public void onClick(int pos) {
                                Intent intent=new Intent(getContext(), DetallesActividadActivity.class);
                                intent.putExtra("id_actividad",listacti.get(viewHolder.getAdapterPosition()).getId());
                                intent.putExtra("nombre_actividad",listacti.get(viewHolder.getAdapterPosition()).getNombre());
                                getContext().startActivity(intent);
                                Toast.makeText(getContext(),"Detalles", Toast.LENGTH_SHORT).show();
                            }
                        }
                ));
                buffer.add(new Mybutton(getContext(),
                        "Ver\n mapa",
                        40,
                        0,
                        Color.parseColor("#b34766"),
                        new MybuttonClickListener(){
                            @Override
                            public void onClick(int pos) {
                                Intent intent= new Intent(getContext(), DetallesMapaActividaActivity.class);
                                intent.putExtra("id_actividad",listacti.get(viewHolder.getAdapterPosition()).getId());
                                intent.putExtra("nombre_actividad",listacti.get(viewHolder.getAdapterPosition()).getNombre());
                                // i.putExtra("lat",lat);
                                // i.putExtra("lon",lon);
                                // i.putExtra("idZona",idZona);
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
        progress=new ProgressDialog(getContext());
        progress.setMessage("Consultando...");
        progress.show();
        String url="http://192.168.0.11/api/Usuario/listaractividades";
        jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR: ", error.toString());
        progress.hide();

    }

    @Override
    public void onResponse(JSONObject response) {

        Actividad actividad=null;
        JSONArray json=response.optJSONArray("actividades");
        try {
            for (int i=0;i<json.length();i++)
            {
                actividad= new Actividad();
                JSONObject jsonObject = null;
                jsonObject=json.getJSONObject(i);
                actividad.setNombre(jsonObject.optString("nombre_actividad"));
                actividad.setId(jsonObject.optInt("id_actividad"));
                listacti.add(actividad);
            }
            progress.hide();
            recyclerViewact.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"no se ha podido establecer conexion"+" "+response,Toast.LENGTH_LONG).show();
            progress.hide();
        }
    }
}
