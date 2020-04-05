package com.example.oaxacacomercio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.oaxacacomercio.ui.gallery.GalleryFragment;
import com.example.oaxacacomercio.ui.home.HomeFragment;
import com.example.oaxacacomercio.ui.send.SendFragment;
import com.example.oaxacacomercio.ui.share.ShareFragment;
import com.example.oaxacacomercio.ui.slideshow.SlideshowFragment;
import com.example.oaxacacomercio.ui.tools.ToolsFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity  implements Response.Listener<JSONObject>,Response.ErrorListener {
    private TextInputLayout inputCorreo,inputPassword;
    private Button usuarioadmi,vendedor;
    private ProgressDialog progreso;
    private RequestQueue request;
    private JsonRequest jsonRequest;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
       // FragmentManager fm = getSupportFragmentManager();
       // fm.beginTransaction().replace(R.id.escena, new HomeFragment()).commit();

        crearComponentes();
        request= Volley.newRequestQueue(this);
    }
    public void goMain(View v){

        iniciarsesion();
    }

    private void crearComponentes(){
        usuarioadmi=findViewById(R.id.btn_login);
        vendedor=findViewById(R.id.btn_vendedor);
        inputCorreo=findViewById(R.id.usuario);
        inputPassword=findViewById(R.id.password);
    }
    private void iniciarsesion(){
        progress=new ProgressDialog(this);
        progress.setMessage("Cargando...");
        progress.show();
        String url="http://192.168.0.23/api/Usuario/iniciar/"+inputCorreo.getEditText().getText().toString();
        jsonRequest= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,"No se encontro al usuario"+ error.toString(),Toast.LENGTH_SHORT).show();
        progress.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
        User usuario= new User();
        Toast.makeText(this,"Inicio de sesión con exito",Toast.LENGTH_SHORT).show();

        JSONArray jsonArray= response.optJSONArray("admin");
        JSONObject jsonObject=null;
        try {
            jsonObject= jsonArray.getJSONObject(0);
            usuario.setNombre(jsonObject.optString("name"));
            usuario.setApellido_paterno(jsonObject.optString("apellido_paterno"));
           usuario.setApellido_materno(jsonObject.optString("apellido_materno"));
            progress.hide();
        }

        catch (JSONException e) {
            e.printStackTrace();
            progress.hide();
        }
        Intent goMain = new Intent(MainActivity.this, Ventanas.class);
        goMain.putExtra(HomeFragment.apellido_paternos,usuario.getApellido_paterno());
        goMain.putExtra(HomeFragment.apellido_maternos,usuario.getApellido_materno());
        goMain.putExtra(HomeFragment.nombres,usuario.getNombre());
        startActivity(goMain);
    }


    }

