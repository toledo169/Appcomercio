package com.example.oaxacacomercio.Vendedor;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class OrderPagerAdapter extends FragmentStateAdapter {
    public OrderPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
       switch (position){
           case 0:
               return new PermisoFragment();
           case 1:
               return new EventosvendFragment();
           default:
               return new NotificacionesFragment();
       }
    }

    @Override
    public int getItemCount() {
        return 3 ;
    }
}
