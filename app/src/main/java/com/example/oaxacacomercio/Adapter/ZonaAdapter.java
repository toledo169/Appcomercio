package com.example.oaxacacomercio.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oaxacacomercio.Detalles.DetallesZonaActivity;
import com.example.oaxacacomercio.R;
import com.example.oaxacacomercio.Modelos.Zona;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class ZonaAdapter extends RecyclerView.Adapter<ZonaAdapter.ZonaHolder> {
    List<Zona> listazonas;
    Context mcontext;
    public ZonaAdapter(List<Zona> listazonas,Context mcontext) {

        this.listazonas = listazonas;
        this.mcontext=mcontext;
    }

    @Override
    public ZonaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.vistazona,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        final ZonaHolder zonaHolder=new ZonaHolder(vista);

        zonaHolder.viewc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mcontext,"zona seleccionada"+String.valueOf(zonaHolder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mcontext, DetallesZonaActivity.class);
                intent.putExtra("id_zona",listazonas.get(zonaHolder.getAdapterPosition()).getId());
                intent.putExtra("nombre",listazonas.get(zonaHolder.getAdapterPosition()).getNombre());
                mcontext.startActivity(intent);
            }
        });

        return zonaHolder;
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
        ConstraintLayout viewc;
        public ZonaHolder( View itemView) {
            super(itemView);
            viewc=itemView.findViewById(R.id.contenedorzona);
            txtNombrezona=(TextView)itemView.findViewById(R.id.txtnombrezona);
            Datozona=(TextView)itemView.findViewById(R.id.datozona);
            datoclave=(TextView)itemView.findViewById(R.id.textViewzona);
            txtclave=(TextView)itemView.findViewById(R.id.txtDocumentozona);
        }
    }
}
