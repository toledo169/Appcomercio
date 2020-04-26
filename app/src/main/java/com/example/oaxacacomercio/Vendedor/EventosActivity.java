package com.example.oaxacacomercio.Vendedor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.oaxacacomercio.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EventosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);
        BottomNavigationView bottomNavigationView=findViewById(R.id.botom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_even);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_notven:
                        startActivity(new Intent(getApplicationContext(),NotificacionesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_homeven:
                        startActivity(new Intent(getApplicationContext(),VendedorActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_even:
                        return true;
                }
                return false;
            }
        });
    }
}
