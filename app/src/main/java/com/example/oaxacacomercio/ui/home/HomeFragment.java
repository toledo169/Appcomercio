package com.example.oaxacacomercio.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import cn.pedant.SweetAlert.SweetAlertDialog;

import com.example.oaxacacomercio.Eventuales.ServicioEventos;
import com.example.oaxacacomercio.MainActivity;
import com.example.oaxacacomercio.Modelos.User;
import com.example.oaxacacomercio.R;
import com.example.oaxacacomercio.Ventanas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment implements View.OnClickListener {//implements Response.Listener<JSONObject>, Response.ErrorListener{

    private HomeViewModel homeViewModel;
    private TextInputLayout nombre;
    public static final String apellido_paternos = "apellido_paterno";
    public static final String apellido_maternos = "apellido_materno";
    public static final String nombres = "name";
    public static final String correo = "email";
    public static final String cargo = "cargo";
    public static final String municipio = "nombre";
    //public static final String fotoperfil = "foto_perfil";

    TextInputLayout apellidop, nomb, correoelect, puesto, lugarn, apellidom;
    ImageView fotoper;
    //ArrayList<Permisos> listapermisos;
    //ArrayList<Permisos> listaauxiliar;
    //JsonRequest jsonObjectRequest;
    //RequestQueue request;
    //private PendingIntent pendingIntent;
    // private final static String CHANNEL_ID="NOTIFICACION";
    // private final static int NOTIFICACION_ID=0;
    TextView cerrarsesiontext, reglamentotext;
    FloatingActionButton fabMain, fabOne, fabtwo;
    Float translationY = 100f;
    OvershootInterpolator interpolator = new OvershootInterpolator();
    SweetAlertDialog sweetAlertDialog;
    private static final String TAG = "MainActivity";

    Boolean isMenuOpen = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        nomb = (TextInputLayout) root.findViewById(R.id.nombreusuario);
        String nomusuario = getActivity().getIntent().getStringExtra("name");
        nomb.getEditText().setText(" " + nomusuario);
        apellidop = (TextInputLayout) root.findViewById(R.id.apellido);
        apellidom = (TextInputLayout) root.findViewById(R.id.apellidom);
        String usuario = getActivity().getIntent().getStringExtra("apellido_paterno");
        String apmusuario = getActivity().getIntent().getStringExtra("apellido_materno");
        apellidop.getEditText().setText(" " + usuario);
        apellidom.getEditText().setText(" " + apmusuario);
        correoelect = (TextInputLayout) root.findViewById(R.id.correo);
        String coreo = getActivity().getIntent().getStringExtra("email");
        correoelect.getEditText().setText(" " + coreo);

        puesto = (TextInputLayout) root.findViewById(R.id.cargo);
        String puestocargo = getActivity().getIntent().getStringExtra("cargo");
        puesto.getEditText().setText(" " + puestocargo);

        lugarn = (TextInputLayout) root.findViewById(R.id.lugar);
        String nacimiento = getActivity().getIntent().getStringExtra("nombre");
        lugarn.getEditText().setText(" " + nacimiento);
        //   request = Volley.newRequestQueue(getContext());
        //  listapermisos = new ArrayList<>();
        //  cargarwebservice();
      //  fotoper = (ImageView) root.findViewById(R.id.fotoperfil);
        //   fotoper.setAdjustViewBounds(true);
       // String foto = getActivity().getIntent().getStringExtra("foto_perfil");
       // Picasso.get().load(foto).into(fotoper);
        fabMain = root.findViewById(R.id.fabcerrar);
        fabOne = root.findViewById(R.id.sitio);
        fabtwo = root.findViewById(R.id.cerrarsesion);
        cerrarsesiontext = root.findViewById(R.id.cerrarsesiontext);
        reglamentotext = root.findViewById(R.id.reglamentotext);

        fabOne.setAlpha(0f);
        reglamentotext.setAlpha(0f);
        fabtwo.setAlpha(0f);
        cerrarsesiontext.setAlpha(0f);
        fabOne.setTranslationY(translationY);
        reglamentotext.setTranslationY(translationY);
        fabtwo.setTranslationY(translationY);
        cerrarsesiontext.setTranslationY(translationY);
        fabMain.setOnClickListener(this);
        fabOne.setOnClickListener(this);
        fabtwo.setOnClickListener(this);
        return root;
    }

    private void openMenu() {
        isMenuOpen = !isMenuOpen;
        fabMain.animate().setInterpolator(interpolator).rotation(45f).setDuration(300).start();
        fabOne.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        reglamentotext.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        fabtwo.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        cerrarsesiontext.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
    }

    private void closeMenu() {
        isMenuOpen = !isMenuOpen;
        fabMain.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start();
        fabOne.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        reglamentotext.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        fabtwo.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        cerrarsesiontext.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
    }

    private void handleFabOne() {
        Log.i(TAG, "handleFabOne: ");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fabcerrar:
                // Toast.makeText(getContext(),"Ir a la pagina", Toast.LENGTH_SHORT).show();
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
            case R.id.sitio:

                // getActivity().startService(new Intent(getContext(), Serviciodatos.class));
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://transparencia.municipiodeoaxaca.gob.mx/t/LGTAIP/70/I/Reglamento_para_el_Control_de_Actividades_Comerciales_en_Via_Publica.pdf"));
                startActivity(intent);
                handleFabOne();
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
            case R.id.cerrarsesion:
                sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE);
                sweetAlertDialog.setTitleText("¿Esta seguro");
                sweetAlertDialog.setContentText("¿Usted desea salir de la aplicación");
                sweetAlertDialog.setCancelText("Cancelar");
                sweetAlertDialog.setConfirmText("Continuar");
                sweetAlertDialog.showCancelButton(true);
                sweetAlertDialog.setCancelable(false);
                sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.setTitleText("Cancelado").setContentText("Los cambios han sido descartados").showCancelButton(false)
                                .setCancelClickListener(null).setConfirmClickListener(null).changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        sDialog.setCancelable(false);
                        sDialog.setCanceledOnTouchOutside(false);
                    }
                });
                sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.setTitleText("Actualizado").setContentText("Usted esta cerrando cesión")
                                .showCancelButton(false).setCancelClickListener(null).setConfirmClickListener(null).changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        sDialog.setCancelable(false);
                        sDialog.setCanceledOnTouchOutside(false);
                        getActivity().stopService(new Intent(getContext(), ServicioEventos.class));
                        logOut();
                    }
                });
                sweetAlertDialog.setCanceledOnTouchOutside(false);
                sweetAlertDialog.show();
                //  getActivity().stopService(new Intent(getContext(), Serviciodatos.class));
                break;
        }
    }

    public void logOut() {
        new User(getContext()).removeuser();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sweetAlertDialog != null && sweetAlertDialog.isShowing()) {
            sweetAlertDialog.dismiss();
        }
    }
}
