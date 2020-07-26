package com.example.oaxacacomercio.password;

import androidx.appcompat.app.AppCompatActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.oaxacacomercio.MainActivity;
import com.example.oaxacacomercio.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfirmarPasswordActivity extends AppCompatActivity {
    private TextInputLayout inputpassword1, inputpassword;
    SweetAlertDialog sweetAlertDialog;
    RequestQueue requestQueue;
    String email;
    FloatingActionButton guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_password);
        inputpassword1 = findViewById(R.id.passworforgot);
        inputpassword = findViewById(R.id.passwordforgoot);
        email= getIntent().getExtras().getString("email");
        guardar=(FloatingActionButton)findViewById(R.id.fabforgotpassword);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputpassword1.getEditText().getText().toString().isEmpty() || inputpassword.getEditText().getText().toString().isEmpty()) {
                    inputpassword.setError("Campo vacio");
                    inputpassword1.setError("Campo vacio");
                    sweetAlertDialog = new SweetAlertDialog(ConfirmarPasswordActivity.this, SweetAlertDialog.ERROR_TYPE);
                    sweetAlertDialog.setTitleText("Datos incorrectos");
                    sweetAlertDialog.setContentText("Debe rellenar todos los campos");
                    sweetAlertDialog.setContentTextSize(15);
                    sweetAlertDialog.setCancelable(false);
                    sweetAlertDialog.setConfirmText("Volver a intentarlo");
                    sweetAlertDialog.setCanceledOnTouchOutside(false);
                    sweetAlertDialog.show();
                } else {
                    if ((validatepassword(inputpassword1) == true)) {
                        if ((igualespassword(inputpassword1, inputpassword) == true)) {
                            sweetAlertDialog = new SweetAlertDialog(ConfirmarPasswordActivity.this, SweetAlertDialog.WARNING_TYPE);
                            sweetAlertDialog.setTitleText("¿Esta seguro?");
                            sweetAlertDialog.setContentText("Desea actualizar sus datos");
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
                                    finish();
                                    overridePendingTransition(0, 0);
                                    startActivity(getIntent());
                                    overridePendingTransition(0, 0);
                                    //  FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment());
                                    //  fragmentTransaction.commit();
                                }
                            });
                            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.setTitleText("Actualizado").setContentText("Los cambios han sido guardados").setConfirmText("Cerrar sesión")
                                            .showCancelButton(false).setCancelClickListener(null).setConfirmClickListener(null).changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                    sDialog.setCancelable(false);
                                    sDialog.setCanceledOnTouchOutside(false);
                                    ejecutarserviciotodo("http://192.168.0.9/api/Usuario/editapass?email=" + email + "&password=" + inputpassword1.getEditText().getText().toString());
                                    Intent intent= new Intent(ConfirmarPasswordActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            });
                            sweetAlertDialog.setCanceledOnTouchOutside(false);
                            sweetAlertDialog.show();
                        }
                    }
                }
            }
        });
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
            sweetAlertDialog = new SweetAlertDialog(ConfirmarPasswordActivity.this, SweetAlertDialog.ERROR_TYPE);
            sweetAlertDialog.setTitleText("Datos incorrectos");
            sweetAlertDialog.setContentText("Ingrese una contraseña valida");
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
            inputpassword.setError("Las contraseñas no coinciden");
            inputpassword1.setError("Las contraseñas no coinciden");
            sweetAlertDialog = new SweetAlertDialog(ConfirmarPasswordActivity.this, SweetAlertDialog.ERROR_TYPE);
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

    public void ejecutarserviciotodo(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getContext(),"Exito",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(ConfirmarPasswordActivity.this, SweetAlertDialog.ERROR_TYPE);
                sweetAlertDialog.setTitleText("Lo sentimos");
                sweetAlertDialog.setContentText("Ha ocurrido un error inesperado, intentelo ms tarde");
                sweetAlertDialog.setContentTextSize(15);
                sweetAlertDialog.setCancelable(false);
                sweetAlertDialog.setConfirmText("Volver a intentarlo");
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        finishAffinity();
                        //        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment());
                        //        fragmentTransaction.commit();
                    }
                });
                sweetAlertDialog.setCanceledOnTouchOutside(false);
                sweetAlertDialog.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                //  parametros.put("email", inputCorreo1.getEditText().getText().toString());
                parametros.put("password", inputpassword1.getEditText().getText().toString());
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(ConfirmarPasswordActivity.this);
        requestQueue.add(stringRequest);
    }
}