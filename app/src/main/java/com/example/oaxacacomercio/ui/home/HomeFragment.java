package com.example.oaxacacomercio.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.oaxacacomercio.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private TextView nombre;
    private FloatingActionButton fab;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        Bundle args = new Bundle();
        String valornombre=args.getString("valor");
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        nombre= root.findViewById(R.id.nombreusuario);
        nombre.setText(valornombre);
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