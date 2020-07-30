package com.example.oaxacacomercio.password;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.oaxacacomercio.Modelos.User;
import com.example.oaxacacomercio.Modelos.Vendedor;
import com.example.oaxacacomercio.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
        RequestQueue requestQueue;
        JsonRequest jsonObjectRequest;
        RequestQueue request;
private TextInputLayout inputCorreo1, inputcorreo;
        SweetAlertDialog sweetAlertDialog;
        ArrayList<User> listapermisos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        Toolbar toolbar = findViewById(R.id.password);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Recuperación de contraseña");
        inputCorreo1 = findViewById(R.id.emailforgot);
        inputcorreo = findViewById(R.id.emailforgot1);
        FloatingActionButton fab = findViewById(R.id.fabforgotemail);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputCorreo1.getEditText().getText().toString().isEmpty()|inputcorreo.getEditText().getText().toString().isEmpty()) {
                    inputcorreo.setError("Debe rellenar el campo correo");
                    inputCorreo1.setError("Debe rellenar el campo correo");
                    sweetAlertDialog = new SweetAlertDialog(PasswordActivity.this, SweetAlertDialog.ERROR_TYPE);
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
        request = Volley.newRequestQueue(this);
        listapermisos = new ArrayList<>();
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
            return false;
        }

    }
    public boolean iguales(TextInputLayout correo, TextInputLayout correo1) {
        if ((correo.getEditText().getText().toString()).equals(correo1.getEditText().getText().toString())) {
            return true;
        } else {
            inputcorreo.setError("Los correos no coinciden");
            inputCorreo1.setError("Los correos no coinciden");
            return false;
        }
    }
    private void cargarwebservice() {
        String url = "http://192.168.0.9/api/Usuario/listarcorreo/" + inputCorreo1.getEditText().getText().toString();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        sweetAlertDialog = new SweetAlertDialog(PasswordActivity.this, SweetAlertDialog.ERROR_TYPE);
        sweetAlertDialog.setTitleText("Lo sentimos");
        sweetAlertDialog.setContentText("Ha ocurrido un error inesperado");
        sweetAlertDialog.setContentTextSize(15);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setConfirmText("Volver a intentarlo");
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                finishAffinity();
                //   FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.viewpager, new PermisoFragment());
                // fragmentTransaction.commit();
                // FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment());
                // fragmentTransaction.commit();
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
                organizacion = new User(this);
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                organizacion.setCorreoelectronico(jsonObject.optString("email"));
                listapermisos.add(organizacion);
            }
            if (listapermisos.isEmpty() == true) {
                //inputcorreo.setError("Correo no encontrado");
                //inputCorreo1.setError("Correo no encontrado");
                sweetAlertDialog = new SweetAlertDialog(PasswordActivity.this, SweetAlertDialog.ERROR_TYPE);
                sweetAlertDialog.setTitleText("Lo sentimos");
                sweetAlertDialog.setContentText("El correo escrito no se encuentra registrado");
                sweetAlertDialog.setContentTextSize(15);
                sweetAlertDialog.setCancelable(false);
                sweetAlertDialog.setConfirmText("Volver a intentarlo");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        //getActivity().finish();
                        //getActivity().overridePendingTransition(0, 0);
                        //startActivity(getActivity().getIntent());
                        //getActivity().overridePendingTransition(0, 0);
                        inputcorreo.getEditText().setText(" ");
                        inputCorreo1.getEditText().setText(" ");

                        //getActivity().finishAffinity();
                        // FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment());
                        // fragmentTransaction.commit();
                    }
                });
                sweetAlertDialog.setCanceledOnTouchOutside(false);
                sweetAlertDialog.show();
            }
            else {
                if (inputCorreo1.getEditText().getText().toString().isEmpty() || inputcorreo.getEditText().getText().toString().isEmpty()) {
                    inputCorreo1.setError("Debe rellenar el campo correo");
                    inputcorreo.setError("Debe rellenar el campo correo");
                } else {
                    if (validateemail(inputCorreo1) == true) {
                        if (iguales(inputCorreo1, inputcorreo) == true) {
                            sweetAlertDialog = new SweetAlertDialog(PasswordActivity.this, SweetAlertDialog.WARNING_TYPE);
                            sweetAlertDialog.setTitleText("¿Esta seguro?");
                            sweetAlertDialog.setContentText("¿Desea continuar?");
                            sweetAlertDialog.setCancelText("No, Cancelar");
                            sweetAlertDialog.setConfirmText("Si, Continuar");
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
                                    // FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment());
                                    // fragmentTransaction.commit();
                                }
                            });
                            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.setTitleText("Actualizado").setContentText("Los cambios han sido guardados").setConfirmText("Cerrar sesión")
                                            .showCancelButton(false).setCancelClickListener(null).setConfirmClickListener(null).changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                    sDialog.setCancelable(false);
                                    sDialog.setCanceledOnTouchOutside(false);
                                    Intent intent= new Intent(PasswordActivity.this,ConfirmarPasswordActivity.class);
                                    intent.putExtra("email",inputCorreo1.getEditText().getText().toString());
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    //      ejecutarservicio("http://192.168.0.2/api/Usuario/edita?id=" + correo + "&email=" + inputCorreo1.getEditText().getText().toString() + "&password=" + mail);
                                    // ejecutarservicio("http://192.168.0.9/api/Usuario/edita?id=" + correo + "&email=" + inputCorreo1.getEditText().getText().toString());
                                    //logOut();
                                }
                            });
                            sweetAlertDialog.setCanceledOnTouchOutside(false);
                            sweetAlertDialog.show();
                        }
                    }
                }

            }
            System.out.println("la lista contiene" + listapermisos.size());
        } catch (JSONException e) {
            e.printStackTrace();
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(PasswordActivity.this, SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setTitleText("Lo sentimos");
            sweetAlertDialog.setContentText("En este momento no se puede realizar su petición");
            sweetAlertDialog.setContentTextSize(15);
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.setConfirmText("Volver a intentarlo");
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    finishAffinity();
                    //  FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.viewpager, new PermisoFragment());
                    //fragmentTransaction.commit();
                    //  FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment());
                    // fragmentTransaction.commit();
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}