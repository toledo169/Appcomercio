package com.example.oaxacacomercio.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oaxacacomercio.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView nombre;
    public static final String apellido_paternos="apellido_paterno";
    public static final String apellido_maternos="apellido_materno";
    public static final String nombres="name";
    public static final String correo="email";
    public static final String cargo="cargo";
    public static final String municipio="nombre";
    TextView apellidop;
    TextView nomb;
    TextView correoelect;
    TextView puesto;
    TextView lugarn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


       nomb=(TextView)root.findViewById(R.id.nombreusuario);
        String nomusuario=getActivity().getIntent().getStringExtra("name");
        nomb.setText( " " +nomusuario);

        apellidop=(TextView) root.findViewById(R.id.apellido);
       String usuario=getActivity().getIntent().getStringExtra("apellido_paterno");
        String apmusuario=getActivity().getIntent().getStringExtra("apellido_materno");
       apellidop.setText( " " +usuario +" " + apmusuario);

       correoelect=(TextView)root.findViewById(R.id.correo);
       String coreo=getActivity().getIntent().getStringExtra("email");
       correoelect.setText( " " +coreo);

       puesto=(TextView)root.findViewById(R.id.cargo);
       String puestocargo=getActivity().getIntent().getStringExtra("cargo");
       puesto.setText( " " +puestocargo);
       lugarn=(TextView)root.findViewById(R.id.lugar);
       String nacimiento=getActivity().getIntent().getStringExtra("nombre");
       lugarn.setText(" "+ nacimiento );
        //final TextView textView = root.findViewById(R.id.text_home);
       // homeViewModel.getText().observe(this, new Observer<String>() {
       //     @Override
       //     public void onChanged(@Nullable String s) {
          //      textView.setText(s);
       //     }
       // });

        return root;
    }


}