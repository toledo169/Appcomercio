package com.example.oaxacacomercio.FragmentDetalles;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.oaxacacomercio.Adapter.DetallesVendedorAdapter;
import com.example.oaxacacomercio.Modelos.Vendedor;
import com.example.oaxacacomercio.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetallesOrganizacionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetallesOrganizacionFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    RecyclerView recyclerViewDetallesf;
    ArrayList<Vendedor> listavendedoresdetallesf;
    ArrayList<Vendedor> listauxiliar;
    ArrayList<Double>lat=new ArrayList<>();
    ArrayList<Double>log=new ArrayList<>();
    ProgressDialog progress;
    JsonRequest jsonObjectRequest;
    RequestQueue request;
    private LinearLayoutManager layoutManager;
    //TextView tvclave,tvnombre;
    private EditText serchvof;
    int claved;
    DetallesVendedorAdapter adapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetallesOrganizacionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetallesOrganizacionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetallesOrganizacionFragment newInstance(String param1, String param2) {
        DetallesOrganizacionFragment fragment = new DetallesOrganizacionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_detalles_organizacion, container, false);
       getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        Bundle datos=getArguments();
        claved=datos.getInt("id_organizacion");
       String name=datos.getString("nombre_organizacion");
       // getSupportActionBar().setTitle(name);
        listavendedoresdetallesf=new ArrayList<>();
        listauxiliar=new ArrayList<>();
        recyclerViewDetallesf= (RecyclerView)view.findViewById(R.id.idRecyclerdetallesvendedorf);
        serchvof=(EditText)view.findViewById(R.id.buscarvendedororgaf);
        layoutManager= new LinearLayoutManager(getContext());
        recyclerViewDetallesf.setLayoutManager(layoutManager);
        recyclerViewDetallesf.setHasFixedSize(true);

        adapter=new DetallesVendedorAdapter(listavendedoresdetallesf,getContext());
        recyclerViewDetallesf.setAdapter(adapter);
        request = Volley.newRequestQueue(getContext());
        cargarwebservice();
        serchvof.addTextChangedListener(new TextWatcher() {
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
        return view;
    }

    private void cargarwebservice() {
        progress=new ProgressDialog(getContext());
        progress.setMessage("Consultando...");
        progress.show();
        String url="http://192.168.0.11/api/Usuario/deta/"+claved;
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
        Vendedor vendedor=null;
        JSONArray json=response.optJSONArray("permisos");
        try {
            for (int i=0;i<json.length();i++){
                vendedor=new Vendedor();
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
                listavendedoresdetallesf.add(vendedor);
                listauxiliar.add(vendedor);
            }
            progress.hide();
            recyclerViewDetallesf.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"no se ha podido establecer conexion"+" "+response,Toast.LENGTH_LONG).show();
            progress.hide();
        }
    }
    public void buscador(String texto){
        listavendedoresdetallesf.clear();
        for (int i=0;i<listauxiliar.size();i++){
            if (listauxiliar.get(i).getNombrev().toLowerCase().contains(texto.toLowerCase())||listauxiliar.get(i).getApellido_paterno().toLowerCase().contains(texto.toLowerCase())){
                listavendedoresdetallesf.add(listauxiliar.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }
}

