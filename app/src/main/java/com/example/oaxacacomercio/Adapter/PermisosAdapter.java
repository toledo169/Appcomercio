package com.example.oaxacacomercio.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oaxacacomercio.Detalles.DetallesorganizacionActivity;
import com.example.oaxacacomercio.Mapas.MapaPermiso;
import com.example.oaxacacomercio.Modelos.Actividad;
import com.example.oaxacacomercio.Modelos.Permisos;
import com.example.oaxacacomercio.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class PermisosAdapter extends RecyclerView.Adapter<PermisosAdapter.PermisosHolder>{
    List<Permisos> listapermisos;
    Context mcontext;
    public PermisosAdapter(List<Permisos> listapermisos, Context mcontext) {
        this.listapermisos = listapermisos;
        this.mcontext = mcontext;
    }
    @NonNull
    @Override
    public PermisosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.vistapermiso, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        final PermisosHolder viewHolder = new PermisosHolder(vista);
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mcontext, "vendedor seleccionado" + String.valueOf(viewHolder.getAdapterPosition()),
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mcontext, MapaPermiso.class);
                intent.putExtra("latitud", String.valueOf(listapermisos.get(viewHolder.getAdapterPosition()).getLatitud()));
                intent.putExtra("longitud", String.valueOf(listapermisos.get(viewHolder.getAdapterPosition()).getLongitud()));
                intent.putExtra("latitud_fin", String.valueOf(listapermisos.get(viewHolder.getAdapterPosition()).getLatitudfinal()));
                intent.putExtra("longitud_fin", String.valueOf(listapermisos.get(viewHolder.getAdapterPosition()).getLongitudfinal()));
                intent.putExtra("giro",listapermisos.get(viewHolder.getAdapterPosition()).getGiro());
                mcontext.startActivity(intent);
            }
        });
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull PermisosHolder holder, int position) {
        holder.txtDocumento.setText(listapermisos.get(position).getGiro().toString());
        holder.txtNombre.setText(listapermisos.get(position).getHorainicio().toString());
    }

    @Override
    public int getItemCount() {
        return listapermisos.size();
    }

    public class PermisosHolder extends RecyclerView.ViewHolder {
        TextView txtDocumento, txtNombre;
        ImageView imageView;
        ConstraintLayout view_container;
        public PermisosHolder(@NonNull View itemView) {
            super(itemView);
            view_container = itemView.findViewById(R.id.contenedorpermiso);
            txtDocumento = (TextView) itemView.findViewById(R.id.txtgiro);
            txtNombre = (TextView) itemView.findViewById(R.id.txthorainicio);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewp);
        }
    }
}
