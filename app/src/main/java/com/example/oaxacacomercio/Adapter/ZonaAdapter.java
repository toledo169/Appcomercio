package com.example.oaxacacomercio.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oaxacacomercio.R;
import com.example.oaxacacomercio.Modelos.Zona;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ZonaAdapter extends RecyclerView.Adapter<ZonaAdapter.ZonaHolder> {
    List<Zona> listazonas;

    public ZonaAdapter(List<Zona> listazonas) {

        this.listazonas = listazonas;
    }

    @Override
    public ZonaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.vistazona,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new ZonaHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ZonaHolder holder, int position) {
        holder.txtNombrezona.setText(listazonas.get(position).getNombre().toString());
        holder.txtclave.setText(listazonas.get(position).getId().toString());
    }

    @Override
    public int getItemCount() {
        return listazonas.size();
    }

    public class ZonaHolder extends RecyclerView.ViewHolder {
        TextView txtNombrezona,Datozona,txtclave,datoclave;

        public ZonaHolder( View itemView) {
            super(itemView);
            txtNombrezona=(TextView)itemView.findViewById(R.id.txtnombrezona);
            Datozona=(TextView)itemView.findViewById(R.id.datozona);
            datoclave=(TextView)itemView.findViewById(R.id.textViewzona);
            txtclave=(TextView)itemView.findViewById(R.id.txtDocumentozona);
        }
    }
}
