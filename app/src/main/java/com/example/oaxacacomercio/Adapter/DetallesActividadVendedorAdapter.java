package com.example.oaxacacomercio.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oaxacacomercio.Mapas.MapavendedorActivity;
import com.example.oaxacacomercio.Modelos.Vendedor;
import com.example.oaxacacomercio.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DetallesActividadVendedorAdapter extends RecyclerView.Adapter<DetallesActividadVendedorAdapter.ActividadHolder> {
    List<Vendedor> listavendedoresdetalleact;
    Dialog dialog;
    Context mcontext;

    public DetallesActividadVendedorAdapter(List<Vendedor> listavendedoresdetalleact, Context mcontext) {
        this.listavendedoresdetalleact = listavendedoresdetalleact;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ActividadHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.vistavendedor,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        final ActividadHolder actividadHolder=new ActividadHolder(vista);
        dialog=new Dialog(mcontext);
        dialog.setContentView(R.layout.detallesvendedorpop);
        actividadHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView dialogname=(TextView) dialog.findViewById(R.id.nombrevendedor);
                TextView dialogapp=(TextView)dialog.findViewById(R.id.apellidosvendedor);
                TextView girovend=(TextView)dialog.findViewById(R.id.giro);
                TextView nameorgani=(TextView)dialog.findViewById(R.id.nomorganizacion);
                TextView nameactividad=(TextView)dialog.findViewById(R.id.nomactividad);
                TextView namezona= (TextView)dialog.findViewById(R.id.zona);
                Button vermapa=(Button)dialog.findViewById(R.id.btn_mapa);
                dialogname.setText(listavendedoresdetalleact.get(actividadHolder.getAdapterPosition()).getNombrev());
                dialogapp.setText(listavendedoresdetalleact.get(actividadHolder.getAdapterPosition()).getApellido_paterno() +" "+ listavendedoresdetalleact.get(actividadHolder.getAdapterPosition()).getApellido_materno() );
                girovend.setText(listavendedoresdetalleact.get(actividadHolder.getAdapterPosition()).getGiro());
                nameorgani.setText(listavendedoresdetalleact.get(actividadHolder.getAdapterPosition()).getNomorganizacion());
                nameactividad.setText(listavendedoresdetalleact.get(actividadHolder.getAdapterPosition()).getActividad());
                namezona.setText(listavendedoresdetalleact.get(actividadHolder.getAdapterPosition()).getNomzona());
                Toast.makeText(mcontext,"vendedor seleccionado"+String.valueOf(actividadHolder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
                dialog.show();

                vermapa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent= new Intent(mcontext, MapavendedorActivity.class);
                        intent.putExtra("latitud",String.valueOf(listavendedoresdetalleact.get(actividadHolder.getAdapterPosition()).getLatitud()));
                        intent.putExtra("longitud",String.valueOf(listavendedoresdetalleact.get(actividadHolder.getAdapterPosition()).getLongitud()));
                        intent.putExtra("name",listavendedoresdetalleact.get(actividadHolder.getAdapterPosition()).getNombrev());
                        intent.putExtra("apellido_paterno",listavendedoresdetalleact.get(actividadHolder.getAdapterPosition()).getApellido_paterno());
                        intent.putExtra("apellido_materno",listavendedoresdetalleact.get(actividadHolder.getAdapterPosition()).getApellido_materno());
                        mcontext.startActivity(intent);
                    }
                });
            }
        });

        return actividadHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadHolder holder, int position) {
        holder.txtclave.setText(listavendedoresdetalleact.get(position).getId().toString()) ;
        holder.txtnombrev.setText(listavendedoresdetalleact.get(position).getNombrev().toString());
        holder.textapellidos.setText(listavendedoresdetalleact.get(position).getApellido_paterno().toString());
    }

    @Override
    public int getItemCount() {
        return listavendedoresdetalleact.size();
    }

    public class ActividadHolder extends RecyclerView.ViewHolder {
        LinearLayout item;
        TextView txtclave,txtnombrev,textapellidos;
        public ActividadHolder(@NonNull View itemView) {
            super(itemView);
            item=(LinearLayout) itemView.findViewById(R.id.infovendedor);
            txtclave=(TextView)itemView.findViewById(R.id.txtclaveven);
            txtnombrev=(TextView)itemView.findViewById(R.id.txtNombrecalle);
            textapellidos=(TextView)itemView.findViewById(R.id.txtapellidos);
        }
    }
}
