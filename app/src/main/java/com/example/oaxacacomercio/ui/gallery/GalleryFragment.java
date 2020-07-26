package com.example.oaxacacomercio.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.example.oaxacacomercio.MainActivity;
import com.example.oaxacacomercio.Modelos.User;
import com.example.oaxacacomercio.Modelos.Vendedor;
import com.example.oaxacacomercio.R;
import com.example.oaxacacomercio.Ventanas;
import com.example.oaxacacomercio.inicio;
import com.example.oaxacacomercio.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GalleryFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    public static final String numexpediente = "id";
    public static final String correoe = "email";
    String mail;
    private GalleryViewModel galleryViewModel;
    int correo;
    ArrayList<User> listapermisos;
    RequestQueue requestQueue;
    JsonRequest jsonObjectRequest;
    RequestQueue request;
    private TextInputLayout inputCorreo1, inputcorreo, inputpassword1, inputpassword;
    Button btnpassword;
    private TextView correoactual;
    boolean bandera = false;
    SweetAlertDialog sweetAlertDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        correo = getActivity().getIntent().getExtras().getInt("id");
        System.out.println("La clave es, porfavor " + correo);

        mail = getActivity().getIntent().getExtras().getString("email");
        System.out.println("correo" + mail);
        inputCorreo1 = root.findViewById(R.id.email1edita);
        inputcorreo = root.findViewById(R.id.emailedita);
        correoactual = root.findViewById(R.id.correoactualed);
        correoactual.setText(" " + mail);
        inputpassword1 = root.findViewById(R.id.passwordeditar1);
        inputpassword = root.findViewById(R.id.passwordeditar);
        btnpassword = root.findViewById(R.id.btn_editarpassword);
        btnpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputpassword.getVisibility() == View.GONE) {
                    bandera = true;
                    inputpassword.setVisibility(View.VISIBLE);
                    inputpassword1.setVisibility(View.VISIBLE);
                } else {
                    bandera = false;
                    inputpassword.setVisibility(View.GONE);
                    inputpassword1.setVisibility(View.GONE);
                }
            }
        });
        FloatingActionButton fab = root.findViewById(R.id.fabeditaremailb);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputCorreo1.getEditText().getText().toString().isEmpty()) {
                    sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                    sweetAlertDialog.setTitleText("Datos incorrectos");
                    sweetAlertDialog.setContentText("Debe rellenar todos los campos");
                    sweetAlertDialog.setContentTextSize(15);
                    sweetAlertDialog.setCancelable(false);
                    sweetAlertDialog.setConfirmText("Volver a intentarlo");
                    sweetAlertDialog.setCanceledOnTouchOutside(false);
                    sweetAlertDialog.show();
                    //           Toast.makeText(getContext(), "Campos vacios", Toast.LENGTH_LONG).show();
                } else {
                    cargarwebservice();
                }
            }
        });
        request = Volley.newRequestQueue(getContext());
        listapermisos = new ArrayList<>();
        return root;
    }

    public void logOut() {
        new User(getContext()).removeuser();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private boolean validateemail(TextInputLayout correo) {
        String emailinput = correo.getEditText().getText().toString();
        Pattern pat = Pattern.compile("[^@]+@[^@]+\\.[a-zA-Z]{2,}");
        Matcher mat = pat.matcher(emailinput);
        if (mat.find()) {
            return true;
        } else {
             inputcorreo.setError("No es un correo valido");
             inputCorreo1.setError("No es un correo valido");
            //Toast.makeText(getContext(),"Email invalido",Toast.LENGTH_SHORT).show();
            sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setTitleText("Datos incorrectos");
            sweetAlertDialog.setContentText("Ingrese un correo electrónico valido");
            sweetAlertDialog.setContentTextSize(15);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setConfirmText("Volver a intentarlo");
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.show();
            return false;
        }

    }

    private boolean validatepassword(TextInputLayout password) {
        String emailinput = password.getEditText().getText().toString();
        ///^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&#.$($)$-$_])[A-Za-z\d$@$!%*?&#.$($)$-$_]{8,15}$/
        Pattern pat = Pattern.compile("^" +
                "(?=.*[0-9])" +
                "(?=.*[a-z])" +
                "(?=.*[A-Z])" +
                "(?=.*[@#$%^&+=!/()?'¡¿*+~{}<>.])" +
                "(?=\\S+$)" +
                ".{6,15}" +
                "$");
        Matcher mat = pat.matcher(emailinput);
        if (mat.find()) {
            //    Toast.makeText(getContext(),"Email valido",Toast.LENGTH_SHORT).show();
            return true;
        } else {
            inputpassword1.setError("La contraseña es demasiado debil");
            inputpassword.setError("La contraseña es demasiado debil");
            //Toast.makeText(getContext(),"Email invalido",Toast.LENGTH_SHORT).show();
            sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setTitleText("Datos incorrectos");
            sweetAlertDialog.setContentText("Ingrese una contraseña valida.");
            sweetAlertDialog.setContentTextSize(15);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setConfirmText("Volver a intentarlo");
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.show();
            return false;
        }

    }

    public boolean iguales(TextInputLayout correo, TextInputLayout correo1) {
        if ((correo.getEditText().getText().toString()).equals(correo1.getEditText().getText().toString())) {
            return true;
        } else {
            sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setTitleText("Datos incorrectos");
            sweetAlertDialog.setContentText("Los correos deben ser iguales");
            sweetAlertDialog.setContentTextSize(15);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setConfirmText("Volver a intentarlo");
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.show();
            return false;
        }
    }

    public boolean igualespassword(TextInputLayout password, TextInputLayout password1) {
        if ((password.getEditText().getText().toString()).equals(password1.getEditText().getText().toString())) {
            return true;
        } else {
            sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setTitleText("Datos incorrectos");
            sweetAlertDialog.setContentText("Las contraseñas deben ser iguales");
            sweetAlertDialog.setContentTextSize(15);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setConfirmText("Volver a intentarlo");
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.show();
            return false;
        }
    }

    public void ejecutarservicio(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getContext(),"Exito",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                final User user = new User(getContext());
                sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                sweetAlertDialog.setTitleText("Lo sentimos");
                sweetAlertDialog.setContentText("Ha ocurrido un error inesperado, intentelo ms tarde");
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
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("email", inputCorreo1.getEditText().getText().toString());
                //parametros.put("password",inputpassword1.getEditText().getText().toString());
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    //************************************************************************************************************
    public void ejecutarserviciotodo(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getContext(),"Exito",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                final User user = new User(getContext());
                sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                sweetAlertDialog.setTitleText("Lo sentimos");
                sweetAlertDialog.setContentText("Ha ocurrido un error inesperado, intentelo ms tarde");
                sweetAlertDialog.setContentTextSize(15);
                sweetAlertDialog.setCancelable(false);
                sweetAlertDialog.setConfirmText("Volver a intentarlo");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog Dialog) {
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
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("email", inputCorreo1.getEditText().getText().toString());
                parametros.put("password", inputpassword1.getEditText().getText().toString());
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void cargarwebservice() {
        String url = "http://192.168.0.9/api/Usuario/listarcorreo/" + inputCorreo1.getEditText().getText().toString();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
        sweetAlertDialog.setTitleText("Lo sentimos");
        sweetAlertDialog.setContentText("Ha ocurrido un error inesperado");
        sweetAlertDialog.setContentTextSize(15);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setConfirmText("Volver a intentarlo");
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                getActivity().finish();
                getActivity().overridePendingTransition(0, 0);
                startActivity(getActivity().getIntent());
                getActivity().overridePendingTransition(0, 0);
            }
        });
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.show();
    }

    @Override
    public void onResponse(JSONObject response) {
        User organizacion = null;
        JSONArray json = response.optJSONArray("correo");
        try {
            for (int i = 0; i < json.length(); i++) {
                organizacion = new User(getContext());
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                organizacion.setCorreoelectronico(jsonObject.optString("email"));
                listapermisos.add(organizacion);
            }
            if (listapermisos.isEmpty() == true) {
                if (bandera == true) {
                    if (inputCorreo1.getEditText().getText().toString().isEmpty() || inputcorreo.getEditText().getText().toString().isEmpty() || inputpassword1.getEditText().getText().toString().isEmpty() || inputpassword.getEditText().getText().toString().isEmpty()) {
                        sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                        sweetAlertDialog.setTitleText("Datos incorrectos");
                        sweetAlertDialog.setContentText("Debe rellenar todos los campos");
                        sweetAlertDialog.setContentTextSize(15);
                        sweetAlertDialog.setCancelable(false);
                        sweetAlertDialog.setConfirmText("Volver a intentarlo");
                        sweetAlertDialog.setCanceledOnTouchOutside(false);
                        sweetAlertDialog.show();
                    } else {
                        if (validateemail(inputCorreo1) == true && (validatepassword(inputpassword1) == true)) {
                            if ((iguales(inputCorreo1, inputcorreo) == true) && (igualespassword(inputpassword1, inputpassword) == true)) {
                                sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE);
                                sweetAlertDialog.setTitleText("¿Esta seguro?");
                                sweetAlertDialog.setContentText("Desea actualizar sus datos");
                                sweetAlertDialog.setCancelText("No, cancelar");
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
                                        getActivity().finish();
                                        getActivity().overridePendingTransition(0, 0);
                                        startActivity(getActivity().getIntent());
                                        getActivity().overridePendingTransition(0, 0);

                                    }
                                });
                                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.setTitleText("Actualizado").setContentText("Los cambios han sido guardados").setConfirmText("Cerrar sesión")
                                                .showCancelButton(false).setCancelClickListener(null).setConfirmClickListener(null).changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                        sDialog.setCancelable(false);
                                        sDialog.setCanceledOnTouchOutside(false);
                                        ejecutarserviciotodo("http://192.168.0.9/api/Usuario/editatodo?id=" + correo + "&email=" + inputCorreo1.getEditText().getText().toString() + "&password=" + inputpassword1.getEditText().getText().toString());
                                        logOut();
                                    }
                                });
                                sweetAlertDialog.setCanceledOnTouchOutside(false);
                                sweetAlertDialog.show();
                            }
                        }
                    }
                } else {
                    if (inputCorreo1.getEditText().getText().toString().isEmpty() || inputcorreo.getEditText().getText().toString().isEmpty()) {
                        sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                        sweetAlertDialog.setTitleText("Datos incorrectos");
                        sweetAlertDialog.setContentText("Debe rellenar todos los campos");
                        sweetAlertDialog.setContentTextSize(15);
                        sweetAlertDialog.setCancelable(false);
                        sweetAlertDialog.setConfirmText("Volver a intentarlo");
                        sweetAlertDialog.setCanceledOnTouchOutside(false);
                        sweetAlertDialog.show();
                    } else {
                        if (validateemail(inputCorreo1) == true) {
                            if (iguales(inputCorreo1, inputcorreo) == true) {
                                sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE);
                                sweetAlertDialog.setTitleText("¿Esta seguro?");
                                sweetAlertDialog.setContentText("Desea actualizar sus datos");
                                sweetAlertDialog.setCancelText("No, cancelar");
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
                                        inputCorreo1.getEditText().setText("");
                                        inputcorreo.getEditText().setText("");
                                    }
                                });
                                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.setTitleText("Actualizado").setContentText("Los cambios han sido guardados").setConfirmText("Cerrar sesión")
                                                .showCancelButton(false).setCancelClickListener(null).setConfirmClickListener(null).changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                        sDialog.setCancelable(false);
                                        sDialog.setCanceledOnTouchOutside(false);
                                        //      ejecutarservicio("http://192.168.0.2/api/Usuario/edita?id=" + correo + "&email=" + inputCorreo1.getEditText().getText().toString() + "&password=" + mail);
                                        ejecutarservicio("http://192.168.0.9/api/Usuario/edita?id=" + correo + "&email=" + inputCorreo1.getEditText().getText().toString());
                                        logOut();
                                    }
                                });
                                sweetAlertDialog.setCanceledOnTouchOutside(false);
                                sweetAlertDialog.show();
                            }
                        }
                    }
                }
            } else {
                inputcorreo.setError("Correo ya existente");
                inputCorreo1.setError("Correo ya existente");
                sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
                sweetAlertDialog.setTitleText("Lo sentimos");
                sweetAlertDialog.setContentText("El correo escrito ya esta registrado");
                sweetAlertDialog.setContentTextSize(15);
                sweetAlertDialog.setCancelable(false);
                sweetAlertDialog.setConfirmText("Volver a intentarlo");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        inputcorreo.getEditText().setText(" ");
                        inputCorreo1.getEditText().setText(" ");
                        inputpassword1.getEditText().setText(" ");
                        inputpassword.getEditText().setText(" ");

                    }
                });
                sweetAlertDialog.setCanceledOnTouchOutside(false);
                sweetAlertDialog.show();
            }
            System.out.println("la lista contiene" + listapermisos.size());
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
                    //  FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment());
                    // fragmentTransaction.commit();
                    // sweetAlertDialog.dismiss();
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
                }
            });
            sweetAlertDialog.setCanceledOnTouchOutside(false);
            sweetAlertDialog.show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sweetAlertDialog != null && sweetAlertDialog.isShowing()) {
            sweetAlertDialog.dismiss();
        }
    }
}