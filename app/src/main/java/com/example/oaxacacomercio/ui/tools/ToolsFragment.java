package com.example.oaxacacomercio.ui.tools;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.oaxacacomercio.Adapter.OrganizacionAdapter;
import com.example.oaxacacomercio.Helper.MySwipeHelper;
import com.example.oaxacacomercio.Helper.MybuttonClickListener;
import com.example.oaxacacomercio.Modelos.Organizacion;
import com.example.oaxacacomercio.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ToolsFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {
    private ToolsViewModel toolsViewModel;
    RecyclerView recyclerorganizaciones;
    ArrayList<Organizacion>listaorganizacion;
    ProgressDialog progress;
    JsonRequest jsonObjectRequest;
    RequestQueue request;
    private LinearLayoutManager layoutManager;
    OrganizacionAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View vista = inflater.inflate(R.layout.fragment_tools, container, false);
       // final TextView textView = root.findViewById(R.id.text_tools);
      //  toolsViewModel.getText().observe(this, new Observer<String>() {
      //      @Override
        //    public void onChanged(@Nullable String s) {
        //        textView.setText(s);
       //     }
       // });
        listaorganizacion=new ArrayList<>();

        recyclerorganizaciones= (RecyclerView) vista.findViewById(R.id.idRecycler);
        layoutManager= new LinearLayoutManager(getActivity());
        recyclerorganizaciones.setLayoutManager(layoutManager);
        recyclerorganizaciones.setHasFixedSize(true);

        adapter= new OrganizacionAdapter(listaorganizacion);
        recyclerorganizaciones.setAdapter(adapter);
        request = Volley.newRequestQueue(getContext());

        final MySwipeHelper swipeHelper= new MySwipeHelper(getContext(),recyclerorganizaciones,200) {
            @Override
            public void instanciateMyButton(RecyclerView.ViewHolder viewHolder, List<Mybutton> buffer) {
                buffer.add(new Mybutton(getContext(),
                        "Detalles",
                        40,
                        0,
                        Color.parseColor("#5d2442"),
                        new MybuttonClickListener(){
                            @Override
                            public void onClick(int pos) {
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

                                Toast.makeText(getContext(),"Eliminar", Toast.LENGTH_SHORT).show();
                            }
                        }
                ));
            }
        };

        cargarwebservice();
        return vista;
    }
    private void cargarwebservice(){
        progress=new ProgressDialog(getContext());
        progress.setMessage("Consultando...");
        progress.show();
        String url="http://192.168.0.11/api/Usuario/listarorg/";
        // cuarto xoxo http://192.168.0.11/api/Usuario/listarorg
        //casa angel 192.168.0.23
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
        Organizacion organizacion=null;
        JSONArray json=response.optJSONArray("Organizacion");
        try {

            for (int i = 0; i < json.length(); i++) {
                organizacion = new Organizacion();
                JSONObject jsonObject = null;
                jsonObject=json.getJSONObject(i);

                organizacion.setDocumento(jsonObject.optInt("id_organizacion"));
                organizacion.setNombre(jsonObject.optString("nombre_organizacion"));
                organizacion.setProfesion(jsonObject.optString("nombre_dirigente"));
                listaorganizacion.add(organizacion);
            }
            progress.hide();
            recyclerorganizaciones.setAdapter(adapter);
            System.out.println(listaorganizacion);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"no se ha podido establecer conexion"+" "+response,Toast.LENGTH_LONG).show();
            progress.hide();
        }

    }
}