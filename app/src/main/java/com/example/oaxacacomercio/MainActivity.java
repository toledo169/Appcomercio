package com.example.oaxacacomercio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.example.oaxacacomercio.Modelos.Vendedor;
import com.example.oaxacacomercio.Vendedor.PermisoFragment;
import com.example.oaxacacomercio.Vendedor.VendedorActivity;
import com.example.oaxacacomercio.ui.gallery.GalleryFragment;
import com.example.oaxacacomercio.ui.home.HomeFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    private TextInputLayout inputCorreo, inputPassword;
    private Button usuarioadmi;// vendedor;
   // private ProgressDialog progreso;
    private RequestQueue request;
    private JsonRequest jsonRequest;
    SweetAlertDialog sDialog;
    private int i=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        Button buttonshow=findViewById(R.id.btn_ayuda);
        buttonshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(
                        MainActivity.this,R.style.BottomSheetDialogTheme);
                View bottomsheetview=LayoutInflater.from(getApplicationContext())
                        .inflate(
                                R.layout.layout_bottom_sheet,
                                (LinearLayout)findViewById(R.id.bottomSheetContainer)
                        );
                bottomsheetview.findViewById(R.id.helpag).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.municipiodeoaxaca.gob.mx"));
                        startActivity(intent);
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bottomsheetview);
                bottomSheetDialog.show();
            }

        });
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
                        iniciarsesion();
    }
 //   public void loginvendedor(View view){
   //   loginvendedor();
   // }
    /* private void setCredentialifexists(){
         String email=getUserMailPrefer();
         if(TextUtils.isEmpty(email)){
             inputCorreo.getEditText().setText(email);
         }
     }*/
    private void crearComponentes(){
        usuarioadmi=findViewById(R.id.btn_login);
        inputCorreo=findViewById(R.id.usuarios);
        inputPassword=findViewById(R.id.password);
        //switchremeber=findViewById(R.id.switchremember);
    }
    private void iniciarsesion(){
        sDialog=new SweetAlertDialog(MainActivity.this,SweetAlertDialog.PROGRESS_TYPE).setTitleText("cargando...");
        sDialog.show();
        sDialog.setCancelable(false);
        new CountDownTimer(800 * 7,800){

            @Override
            public void onTick(long l) {
                i++;
                switch (i){
                    case 0:
                        sDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue_btn_bg_color));
                        break;
                    case 1:
                        sDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_50));
                        break;
                    case 2:
                        sDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                        break;
                    case 3:
                        sDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_deep_teal_20));
                        break;
                    case 4:
                        sDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.material_blue_grey_80));
                        break;
                    case 5:
                        sDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.warning_stroke_color));
                        break;
                    case 6:
                        sDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.success_stroke_color));
                        break;
                }
            }
            @Override
            public void onFinish() {
                i=-1;
                sDialog.setTitleText("Bienvenido").setConfirmClickListener(null).changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            }
        }.start();
        String url="http://192.168.0.8/api/Usuario/loginv?email="+inputCorreo.getEditText().getText().toString()+"&password="+inputPassword.getEditText().getText().toString();
        jsonRequest= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonRequest);

    }
    @Override
    public void onErrorResponse(VolleyError error) {
        SweetAlertDialog sweetAlertDialog=new SweetAlertDialog(MainActivity.this,SweetAlertDialog.ERROR_TYPE);
        sweetAlertDialog.setTitleText("Datos incorrectos");
        sweetAlertDialog.setContentText("usuario o contraseña incorrecto");
        sweetAlertDialog.setContentTextSize(15);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setConfirmText("volver a intentarlo");
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.show();
        sDialog.hide();
  //      progress.hide();
    }
    @Override
    public void onResponse(JSONObject response) {
        User usuario= new User(this);
     //   Toast.makeText(this,"Inicio de sesión con exito",Toast.LENGTH_SHORT).show();
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
            usuario.setAdminsecre(jsonObject.optInt("id"));
            sDialog.hide();
//            progress.hide();
        }
        catch (JSONException e) {
            e.printStackTrace();
            sDialog.hide();
//            progress.hide();
        }
        User user=new User(MainActivity.this);
        user.setCorreoelectronico(usuario.getCorreoelectronico());
        user.setNombre(usuario.getNombre());
        user.setApellido_paterno(usuario.getApellido_paterno());
        user.setApellido_materno(usuario.getApellido_materno());
        user.setCargo(usuario.getCargo());
        user.setMunicipio(usuario.getMunicipio());
        user.setAdminsecre(usuario.getAdminsecre());
        Intent goMain = new Intent(MainActivity.this, Ventanas.class);
        goMain.putExtra(GalleryFragment.numexpediente,usuario.getAdminsecre());
        goMain.putExtra(GalleryFragment.correoe,usuario.getCorreoelectronico());
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

