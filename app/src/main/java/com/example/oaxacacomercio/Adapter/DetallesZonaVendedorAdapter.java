package com.example.oaxacacomercio.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oaxacacomercio.Mapas.MapaActivity;
import com.example.oaxacacomercio.Mapas.MapavendedorActivity;
import com.example.oaxacacomercio.Modelos.Vendedor;
import com.example.oaxacacomercio.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DetallesZonaVendedorAdapter extends RecyclerView.Adapter<DetallesZonaVendedorAdapter.ZonaHolder> {
    List<Vendedor> listavendedoresdetallezona;
    Dialog dialog;
    Context mcontext;

    public DetallesZonaVendedorAdapter(List<Vendedor> listavendedoresdetallezona, Context mcontext) {
        this.listavendedoresdetallezona = listavendedoresdetallezona;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ZonaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.vistavendedor, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        final ZonaHolder zonaHolder = new ZonaHolder(vista);
        dialog = new Dialog(mcontext);
        dialog.setContentView(R.layout.detallesvendedorpop);
        zonaHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView dialogname = (TextView) dialog.findViewById(R.id.nombrevendedor);
                TextView dialogapp = (TextView) dialog.findViewById(R.id.apellidosvendedor);
                TextView girovend = (TextView) dialog.findViewById(R.id.giro);
                TextView nameorgani = (TextView) dialog.findViewById(R.id.nomorganizacion);
                TextView nameactividad = (TextView) dialog.findViewById(R.id.nomactividad);
                TextView namezona = (TextView) dialog.findViewById(R.id.zona);
                Button vermapa = (Button) dialog.findViewById(R.id.btn_mapa);
                dialogname.setText(listavendedoresdetallezona.get(zonaHolder.getAdapterPosition()).getNombrev());
                dialogapp.setText(listavendedoresdetallezona.get(zonaHolder.getAdapterPosition()).getApellido_paterno() + " " + listavendedoresdetallezona.get(zonaHolder.getAdapterPosition()).getApellido_materno());
                girovend.setText(listavendedoresdetallezona.get(zonaHolder.getAdapterPosition()).getGiro());
                nameorgani.setText(listavendedoresdetallezona.get(zonaHolder.getAdapterPosition()).getNomorganizacion());
                nameactividad.setText(listavendedoresdetallezona.get(zonaHolder.getAdapterPosition()).getActividad());
                namezona.setText(listavendedoresdetallezona.get(zonaHolder.getAdapterPosition()).getNomzona());
                //     Toast.makeText(mcontext,"vendedor seleccionado"+String.valueOf(zonaHolder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
                dialog.show();
                vermapa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mcontext, MapavendedorActivity.class);
                        intent.putExtra("latitud", String.valueOf(listavendedoresdetallezona.get(zonaHolder.getAdapterPosition()).getLatitud()));
                        intent.putExtra("longitud", String.valueOf(listavendedoresdetallezona.get(zonaHolder.getAdapterPosition()).getLongitud()));
                        intent.putExtra("name", listavendedoresdetallezona.get(zonaHolder.getAdapterPosition()).getNombrev());
                        intent.putExtra("apellido_paterno", listavendedoresdetallezona.get(zonaHolder.getAdapterPosition()).getApellido_paterno());
                        intent.putExtra("apellido_materno", listavendedoresdetallezona.get(zonaHolder.getAdapterPosition()).getApellido_materno());
                        mcontext.startActivity(intent);
                    }
                });
            }
        });
        return zonaHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ZonaHolder holder, int position) {
        holder.txtclave.setText(listavendedoresdetallezona.get(position).getId().toString());
        holder.txtnombrev.setText("Nombre: " + listavendedoresdetallezona.get(position).getNombrev().toString());
        holder.textapellidos.setText("Apellidos: " + listavendedoresdetallezona.get(position).getApellido_paterno().toString() + listavendedoresdetallezona.get(position).getApellido_materno().toString());
    }

    @Override
    public int getItemCount() {
        return listavendedoresdetallezona.size();
    }

    public class ZonaHolder extends RecyclerView.ViewHolder {
        LinearLayout item;
        TextView txtclave, txtnombrev, textapellidos;

        public ZonaHolder(@NonNull View itemView) {
            super(itemView);
            item = (LinearLayout) itemView.findViewById(R.id.infovendedor);
            txtclave = (TextView) itemView.findViewById(R.id.txtclaveven);
            txtnombrev = (TextView) itemView.findViewById(R.id.txtNombrecalle);
            textapellidos = (TextView) itemView.findViewById(R.id.txtapellidos);
        }
    }
}
