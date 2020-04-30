package com.example.oaxacacomercio.Vendedor;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.example.oaxacacomercio.MainActivity;
import com.example.oaxacacomercio.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PermisoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PermisoFragment extends Fragment implements View.OnClickListener {
    public static final String apellido_paternos = "apellido_paterno";
    public static final String apellido_maternos = "apellido_materno";
    public static final String nombres = "name";
    public static final String numexpediente="numero_expediente";
    public static final String numcuenta="numerocuenta";
    TextView apellidop;
    TextView nomb;
    TextView correoelect;
    TextView numexpedie;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FloatingActionButton fabMain, fabOne,fabtwo;
    Float translationY = 100f;

    OvershootInterpolator interpolator = new OvershootInterpolator();

    private static final String TAG = "MainActivity";

    Boolean isMenuOpen = false;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PermisoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PermisoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PermisoFragment newInstance(String param1, String param2) {
        PermisoFragment fragment = new PermisoFragment();
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
        View vista= inflater.inflate(R.layout.fragment_permiso, container, false);
        nomb = (TextView) vista.findViewById(R.id.nombreusuario);
        String nomusuario = getActivity().getIntent().getStringExtra("name");
        nomb.setText(" " + nomusuario);

        apellidop = (TextView) vista.findViewById(R.id.apellido);
        String usuario = getActivity().getIntent().getStringExtra("apellido_paterno");
        String apmusuario = getActivity().getIntent().getStringExtra("apellido_materno");
        apellidop.setText(" " + usuario + " " + apmusuario);

        correoelect = (TextView) vista.findViewById(R.id.correo);
        int coreo = getActivity().getIntent().getExtras().getInt("numerocuenta");
        correoelect.setText(String.valueOf(" " + coreo));
        numexpedie = (TextView) vista.findViewById(R.id.numexpediente);
        int numexpedient = getActivity().getIntent().getExtras().getInt("numero_expediente");
        numexpedie.setText(String.valueOf(" " + numexpedient));

        fabMain =vista.findViewById(R.id.fabcerrar);
        fabOne = vista.findViewById(R.id.sitio);
        fabtwo=vista.findViewById(R.id.cerrarsesion);
      //  CardView cardView=vista.findViewById(R.id.cardpermiso);
        fabOne.setAlpha(0f);
        fabtwo.setAlpha(0f);
        fabOne.setTranslationY(translationY);
        fabtwo.setTranslationY(translationY);
        fabMain.setOnClickListener(this);
        fabOne.setOnClickListener(this);
        fabtwo.setOnClickListener(this);
        return vista;
    }

    private void openMenu() {
        isMenuOpen = !isMenuOpen;
        fabMain.animate().setInterpolator(interpolator).rotation(45f).setDuration(300).start();
        fabOne.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        fabtwo.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
    }

    private void closeMenu() {
        isMenuOpen = !isMenuOpen;
        fabMain.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start();
        fabOne.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        fabtwo.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
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
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.municipiodeoaxaca.gob.mx"));
                startActivity(intent);
                handleFabOne();
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
            case R.id.cerrarsesion:
                AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
                LayoutInflater inflater= getLayoutInflater();
                View vis= inflater.inflate(R.layout.dialog_cerrar,null);
                builder.setView(vis);
                final AlertDialog dialog=builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button btnsi= vis.findViewById(R.id.btnsi);
                btnsi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        logOut();
                        //finishAffinity();
                    }
                });
                Button btnno=vis.findViewById(R.id.btnno);
                btnno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
        }
    }
    private void logOut() {
        //new User(Ventanas.thi).removeuser();
        Intent intent= new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
