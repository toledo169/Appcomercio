package com.example.oaxacacomercio.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oaxacacomercio.Detalles.DetallesActividadActivity;
import com.example.oaxacacomercio.Detalles.DetallesZonaActivity;
import com.example.oaxacacomercio.Modelos.Actividad;
import com.example.oaxacacomercio.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class ActividadAdapter extends RecyclerView.Adapter<ActividadAdapter.ActividadHolder> {
    Context mcontext;
    List<Actividad> listaactividades;

    public ActividadAdapter(List<Actividad> listaactividades, Context mcontext) {
        this.mcontext = mcontext;
        this.listaactividades = listaactividades;
    }

    @NonNull
    @Override
    public ActividadHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.vistaact, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        final ActividadHolder actividadHolder = new ActividadHolder(vista);
        actividadHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   Toast.makeText(mcontext, "act seleccionada" + String.valueOf(actividadHolder.getAdapterPosition()),
                //         Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mcontext, DetallesActividadActivity.class);
                intent.putExtra("id_actividad", listaactividades.get(actividadHolder.getAdapterPosition()).getId());
                intent.putExtra("nombre_actividad", listaactividades.get(actividadHolder.getAdapterPosition()).getNombre());
                mcontext.startActivity(intent);
            }
        });

        return actividadHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadHolder holder, int position) {
        holder.txtNombrezona.setText("Nombre:" + listaactividades.get(position).getNombre().toString());
        holder.txtclave.setText(listaactividades.get(position).getId().toString());
    }

    @Override
    public int getItemCount() {
        return listaactividades.size();
    }

    public class ActividadHolder extends RecyclerView.ViewHolder {
        TextView txtNombrezona, Datozona, txtclave, datoclave;
        LinearLayout view_container;
        public ActividadHolder(@NonNull View itemView) {
            super(itemView);
            view_container = itemView.findViewById(R.id.contenedoract);
            txtNombrezona = (TextView) itemView.findViewById(R.id.txtnombreact);
            //          Datozona = (TextView) itemView.findViewById(R.id.datoact);
            datoclave = (TextView) itemView.findViewById(R.id.textViewact);
            txtclave = (TextView) itemView.findViewById(R.id.txtDocumentoact);
        }
    }
}
