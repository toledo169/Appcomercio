package com.example.oaxacacomercio.Vendedor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.oaxacacomercio.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NotificacionesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);
        BottomNavigationView bottomNavigationView=findViewById(R.id.botom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_notven);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_notven:
                        return true;
                    case R.id.nav_homeven:
                        startActivity(new Intent(getApplicationContext(),VendedorActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_even:
                        startActivity(new Intent(getApplicationContext(),EventosActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}
