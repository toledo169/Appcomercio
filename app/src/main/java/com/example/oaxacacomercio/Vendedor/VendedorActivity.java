package com.example.oaxacacomercio.Vendedor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.example.oaxacacomercio.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class VendedorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendedor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        ViewPager2 viewPager2= findViewById(R.id.viewpager);
        viewPager2.setAdapter(new OrderPagerAdapter(this));

        TabLayout tabLayout=findViewById(R.id.tablalayout);
        TabLayoutMediator tabLayoutMediator=new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0: {
                        tab.setText("Inicio");
                        tab.setIcon(R.drawable.ic_home24dp);
                        break;
                    }
                    case 1: {
                        tab.setText("Eventos");
                        tab.setIcon(R.drawable.ic_event_24dp);
                        break;
                    }
                    case 2: {
                        tab.setText("Recibidos");
                        tab.setIcon(R.drawable.ic_notifications_24dp);
                        break;
                    }
                }
            }
        });tabLayoutMediator.attach();
    }

}
