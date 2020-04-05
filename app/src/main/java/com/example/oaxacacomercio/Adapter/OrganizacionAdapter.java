package com.example.oaxacacomercio.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oaxacacomercio.Organizacion;
import com.example.oaxacacomercio.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrganizacionAdapter extends RecyclerView.Adapter<OrganizacionAdapter.OrganizacionHolder> {
    List<Organizacion> listaorganizaciones;

    public OrganizacionAdapter(List<Organizacion> listaorganizaciones) {
        this.listaorganizaciones = listaorganizaciones;
    }
    @Override
    public OrganizacionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.vistaorg ,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new OrganizacionHolder(vista);
    }

    @Override
    public void onBindViewHolder(OrganizacionHolder holder, int position) {
        holder.txtDocumento.setText(listaorganizaciones.get(position).getDocumento().toString());
        holder.txtNombre.setText(listaorganizaciones.get(position).getNombre().toString());
        holder.txtProfesion.setText(listaorganizaciones.get(position).getProfesion().toString());

    }

    @Override
    public int getItemCount() {

        return listaorganizaciones.size();
    }

    public  class OrganizacionHolder extends RecyclerView.ViewHolder{
        TextView txtDocumento,txtNombre,txtProfesion;
        ImageView imageView;
        public OrganizacionHolder(View itemView) {
            super(itemView);
            txtDocumento=(TextView)itemView.findViewById(R.id.txtDocumento);
            txtNombre= (TextView) itemView.findViewById(R.id.txtNombre);
            txtProfesion= (TextView) itemView.findViewById(R.id.txtProfesion);
            imageView=(ImageView) itemView.findViewById(R.id.imageView2);
        }
    }
}
