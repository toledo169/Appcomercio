package com.example.oaxacacomercio.ui.gallery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import cn.pedant.SweetAlert.SweetAlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.oaxacacomercio.Detalles.DetallesActividadActivity;
import com.example.oaxacacomercio.MainActivity;
import com.example.oaxacacomercio.Modelos.User;
import com.example.oaxacacomercio.Modelos.Vendedor;
import com.example.oaxacacomercio.R;
import com.example.oaxacacomercio.Vendedor.PermisoFragment;
import com.example.oaxacacomercio.Vendedor.VendedorActivity;
import com.example.oaxacacomercio.Ventanas;
import com.example.oaxacacomercio.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class GalleryFragment extends Fragment {
    public static final String numexpediente="id";
    public static final String correoe = "email";
    private GalleryViewModel galleryViewModel;
    int correo;
    RequestQueue requestQueue;
    private TextInputLayout inputCorreo1, inputcorreo;
    private TextView correoactual;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        correo = getActivity().getIntent().getExtras().getInt("id");
        System.out.println("La clave es, porfavor "+correo);
       // Toast.makeText(getContext(),correo,Toast.LENGTH_SHORT).show();
        String mail=getActivity().getIntent().getExtras().getString("email");
       // System.out.println("correo"+mail);
        inputCorreo1=root.findViewById(R.id.emailedita1);
        inputcorreo=root.findViewById(R.id.emailedita);
        correoactual=root.findViewById(R.id.correoactualed);
        correoactual.setText(" "+ mail);
        FloatingActionButton fab= root.findViewById(R.id.fabeditaremailb);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputCorreo1.getEditText().getText().toString().isEmpty()||inputcorreo.getEditText().getText().toString().isEmpty()) {
                    SweetAlertDialog sweetAlertDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE);
                    sweetAlertDialog.setTitleText("Datos incorrectos");
                    sweetAlertDialog.setContentText("Debe rellenar todos los campos");
                    sweetAlertDialog.setContentTextSize(15);
                    sweetAlertDialog.setCancelable(false);
                    sweetAlertDialog.setConfirmText("volver a intentarlo");
                    sweetAlertDialog.setCanceledOnTouchOutside(false);
                    sweetAlertDialog.show();
                }
                else {
                    if (validateemail(inputCorreo1) == true) {
                        if (iguales(inputCorreo1,inputcorreo)==true) {
                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE);
                            sweetAlertDialog.setTitleText("¿Esta seguro");
                            sweetAlertDialog.setContentText("Desea actualizar sus datos");
                            sweetAlertDialog.setCancelText("No,Cancelar");
                            sweetAlertDialog.setConfirmText("Si, continuar");
                            sweetAlertDialog.showCancelButton(true);
                            sweetAlertDialog.setCancelable(false);
                            sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.setTitleText("Cancelado").setContentText("Los cambios han sido descartados").setConfirmText("Ir al inicio").showCancelButton(false)
                                            .setCancelClickListener(null).setConfirmClickListener(null).changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                    sDialog.setCancelable(false);
                                    sDialog.setCanceledOnTouchOutside(false);
                                   FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment());
                                    fragmentTransaction.commit();
                                }
                            });
                            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.setTitleText("Actualizado").setContentText("Los cambios han sido guardados").setConfirmText("Cerrar sesión")
                                            .showCancelButton(false).setCancelClickListener(null).setConfirmClickListener(null).changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                    sDialog.setCancelable(false);
                                    sDialog.setCanceledOnTouchOutside(false);
                                    ejecutarservicio("http://192.168.0.8/api/Usuario/edita?id=" + correo + "&email=" + inputCorreo1.getEditText().getText().toString());
                                    logOut();
                                }
                            });
                            sweetAlertDialog.setCanceledOnTouchOutside(false);
                            sweetAlertDialog.show();
                        }
                    }
                }
                }
        });
      return root;
    }
    public void logOut() {
        new User(getContext()).removeuser();
        Intent intent= new Intent(getContext(),MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
    private boolean validateemail(TextInputLayout correo) {
        String emailinput = correo.getEditText().getText().toString();
        if (!emailinput.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailinput).matches()) {
            return true;
        }
        else {
            SweetAlertDialog sweetAlertDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setTitleText("Datos incorrectos");
            sweetAlertDialog.setContentText("Ingrese un correo electronico valido");
            sweetAlertDialog.setContentTextSize(15);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setConfirmText("volver a intentarlo");
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.show();
            return false;
        }
    }
    public boolean iguales(TextInputLayout correo, TextInputLayout correo1){
        if ((correo.getEditText().getText().toString()).equals(correo1.getEditText().getText().toString())){
            return  true;
        }else {
            SweetAlertDialog sweetAlertDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setTitleText("Datos incorrectos");
            sweetAlertDialog.setContentText("Los correos deben ser iguales");
            sweetAlertDialog.setContentTextSize(15);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setConfirmText("volver a intentarlo");
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.show();
            return false;
        }

    }
    public void ejecutarservicio(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              Toast.makeText(getContext(),"Exito",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                SweetAlertDialog sweetAlertDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE);
                sweetAlertDialog.setTitleText("Datos incorrectos");
                sweetAlertDialog.setContentText("Ingrese un correo electronico valido");
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
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>parametros=new HashMap<>();
                parametros.put("email",inputCorreo1.getEditText().getText().toString());
            //    parametros.put("email",inputCorreo1.getEditText().getText().toString());
                return parametros;
            }
        };
        requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}