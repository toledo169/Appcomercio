package com.example.oaxacacomercio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.oaxacacomercio.Modelos.User;
import com.example.oaxacacomercio.ui.home.HomeFragment;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity  implements Response.Listener<JSONObject>,Response.ErrorListener {
    private TextInputLayout inputCorreo,inputPassword;
    private Button usuarioadmi,vendedor;
    private Switch switchremeber;
   // private SharedPreferences prefs;
    private ProgressDialog progreso;
    private RequestQueue request;
    private JsonRequest jsonRequest;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
       // prefs=getSharedPreferences("Preferences", Context.MODE_PRIVATE);
       // setCredentialifexists();
       // FragmentManager fm = getSupportFragmentManager();
       // fm.beginTransaction().replace(R.id.escena, new HomeFragment()).commit();

        crearComponentes();
        request= Volley.newRequestQueue(this);
    }
   /* private void SaveOnPreferences(String email){
        if(switchremeber.isChecked()){
            SharedPreferences.Editor editor= prefs.edit();
            editor.putString("email",email);
          //  editor.commit();
            editor.apply();
        }
    }*/
    //private String getUserMailPrefer() {
      //  return prefs.getString("email","");
    //}
    public void goMain(View v){
     //   String email=inputCorreo.getEditText().getText().toString() ;
        iniciarsesion();
     //   SaveOnPreferences(email);

    }
   /* private void setCredentialifexists(){
        String email=getUserMailPrefer();
        if(TextUtils.isEmpty(email)){
            inputCorreo.getEditText().setText(email);
        }
    }*/
    private void crearComponentes(){
        usuarioadmi=findViewById(R.id.btn_login);
        vendedor=findViewById(R.id.btn_vendedor);
        inputCorreo=findViewById(R.id.usuario);
        inputPassword=findViewById(R.id.password);
        //switchremeber=findViewById(R.id.switchremember);
    }
    private void iniciarsesion(){
        progress=new ProgressDialog(this);
        progress.setMessage("Cargando...");
        progress.show();
        String url="http://192.168.0.11/api/Usuario/loginv?email="+inputCorreo.getEditText().getText().toString()+"&password="+inputPassword.getEditText().getText().toString();
        jsonRequest= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
      //  Toast.makeText(this,"No se encontro al usuario"+ error.toString(),Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater= getLayoutInflater();
        View view= inflater.inflate(R.layout.dialog_desconocido,null);
        builder.setView(view);
        final AlertDialog dialog=builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        Button btnno=view.findViewById(R.id.btnok);
        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
        progress.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
        User usuario= new User(this);
        Toast.makeText(this,"Inicio de sesión con exito",Toast.LENGTH_SHORT).show();
        JSONArray jsonArray= response.optJSONArray("admin");
        JSONObject jsonObject=null;

        try {
            jsonObject= jsonArray.getJSONObject(0);
        System.out.println("los datos son"+jsonArray.length());
            usuario.setNombre(jsonObject.optString("name"));
            usuario.setApellido_paterno(jsonObject.optString("apellido_paterno"));
           usuario.setApellido_materno(jsonObject.optString("apellido_materno"));
           usuario.setCorreoelectronico(jsonObject.optString("email"));
            usuario.setCargo(jsonObject.optString("cargo"));
            usuario.setMunicipio(jsonObject.optString("nombre"));
            progress.hide();
        }

        catch (JSONException e) {
            e.printStackTrace();
            progress.hide();
        }
       User user=new User(MainActivity.this);
        user.setCorreoelectronico(usuario.getCorreoelectronico());
        user.setNombre(usuario.getNombre());
        user.setApellido_paterno(usuario.getApellido_paterno());
        user.setApellido_materno(usuario.getApellido_materno());
        user.setCargo(usuario.getCargo());
        user.setMunicipio(usuario.getMunicipio());
        Intent goMain = new Intent(MainActivity.this, Ventanas.class);
        goMain.putExtra(HomeFragment.apellido_paternos,usuario.getApellido_paterno());
        goMain.putExtra(HomeFragment.apellido_maternos,usuario.getApellido_materno());
        goMain.putExtra(HomeFragment.nombres,usuario.getNombre());
        goMain.putExtra(HomeFragment.correo,usuario.getCorreoelectronico());
        goMain.putExtra(HomeFragment.cargo,usuario.getCargo());
        goMain.putExtra(HomeFragment.municipio,usuario.getMunicipio());
       goMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(goMain);
    }

    }

