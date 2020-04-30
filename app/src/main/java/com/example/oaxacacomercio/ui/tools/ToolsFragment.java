package com.example.oaxacacomercio.ui.tools;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.example.oaxacacomercio.Detalles.DetallesMapaOrganizacionActivity;
import com.example.oaxacacomercio.Detalles.DetallesorganizacionActivity;
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
    ArrayList<Organizacion>listaauxiliar;
    ProgressDialog progress;
    JsonRequest jsonObjectRequest;
    RequestQueue request;
    private LinearLayoutManager layoutManager;
    OrganizacionAdapter adapter;
    private EditText sercho;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        final View vista = inflater.inflate(R.layout.fragment_tools, container, false);
       // final TextView textView = root.findViewById(R.id.text_tools);
      //  toolsViewModel.getText().observe(this, new Observer<String>() {
      //      @Override
        //    public void onChanged(@Nullable String s) {
        //        textView.setText(s);
       //     }
       // });
        listaorganizacion=new ArrayList<>();
        listaauxiliar=new ArrayList<>();

        recyclerorganizaciones= (RecyclerView) vista.findViewById(R.id.idRecycler);
        sercho=(EditText)vista.findViewById(R.id.bucarorganizaciones);
        layoutManager= new LinearLayoutManager(getActivity());
        recyclerorganizaciones.setLayoutManager(layoutManager);
        recyclerorganizaciones.setHasFixedSize(true);

        adapter= new OrganizacionAdapter(listaorganizacion,getContext());
        recyclerorganizaciones.setAdapter(adapter);
        request = Volley.newRequestQueue(getContext());

        final MySwipeHelper swipeHelper= new MySwipeHelper(getContext(),recyclerorganizaciones,200) {

            @Override
            public void instanciateMyButton(final RecyclerView.ViewHolder viewHolder, final List<Mybutton> buffer) {
                buffer.add(new Mybutton(getContext(),
                        "Detalles",
                        40,
                        0,
                        Color.parseColor("#5d2442"),
                        new MybuttonClickListener(){
                            @Override
                            public void onClick(int pos) {
                               Intent i=new Intent(getContext(), DetallesorganizacionActivity.class);
                                i.putExtra("id_organizacion",listaorganizacion.get(viewHolder.getAdapterPosition()).getDocumento());
                                i.putExtra("nombre_organizacion",listaorganizacion.get(viewHolder.getAdapterPosition()).getNombre());
                                i.putExtra("nombre_dirigente",listaorganizacion.get(viewHolder.getAdapterPosition()).getProfesion());
                                getContext().startActivity(i);
                              //  Toast.makeText(getContext(),"Detalles", Toast.LENGTH_SHORT).show();
                                /*Bundle datos=new Bundle();
                                datos.putInt("id_organizacion",listaorganizacion.get(viewHolder.getAdapterPosition()).getDocumento());
                                datos.putString("nombre_organizacion",listaorganizacion.get(viewHolder.getAdapterPosition()).getNombre());
                                Fragment fragmento= new DetallesOrganizacionFragment();
                                fragmento.setArguments(datos);
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.nav_host_fragment, fragmento);
                                fragmentTransaction.commit();*/
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
                                Intent intent=new Intent(getContext(), DetallesMapaOrganizacionActivity.class);
                                intent.putExtra("id_organizacion",listaorganizacion.get(viewHolder.getAdapterPosition()).getDocumento());
                                intent.putExtra("nombre_organizacion",listaorganizacion.get(viewHolder.getAdapterPosition()).getNombre());
                                intent.putExtra("nombre_dirigente",listaorganizacion.get(viewHolder.getAdapterPosition()).getProfesion());
                                getContext().startActivity(intent);

                            }
                        }
                ));
            }
        };

        cargarwebservice();
        sercho.addTextChangedListener(new TextWatcher() {
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
                listaauxiliar.add(organizacion);
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
    public void buscador(String texto){
        listaorganizacion.clear();
        for (int i=0;i<listaauxiliar.size();i++) {
            if (listaauxiliar.get(i).getNombre().toLowerCase().contains(texto.toLowerCase())||listaauxiliar.get(i).getProfesion().toLowerCase().contains(texto.toLowerCase())) {
            listaorganizacion.add(listaauxiliar.get(i));
            }
        }
        adapter.notifyDataSetChanged();
        }

    }

