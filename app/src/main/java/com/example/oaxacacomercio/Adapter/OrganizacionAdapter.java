package com.example.oaxacacomercio.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oaxacacomercio.DetallesorganizacionActivity;
import com.example.oaxacacomercio.Modelos.Organizacion;
import com.example.oaxacacomercio.R;

import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

public class OrganizacionAdapter extends RecyclerView.Adapter<OrganizacionAdapter.OrganizacionHolder> {
    List<Organizacion> listaorganizaciones;
    Context mcontext;
    public OrganizacionAdapter(List<Organizacion> listaorganizaciones,Context mcontext) {
        this.listaorganizaciones = listaorganizaciones;
        this.mcontext=mcontext;
    }
    @Override
    public OrganizacionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.vistaorg ,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        final OrganizacionHolder viewHolder= new OrganizacionHolder(vista);
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mcontext,"vendedor seleccionado"+String.valueOf(viewHolder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
                Intent i=new Intent(mcontext, DetallesorganizacionActivity.class);
                i.putExtra("id_organizacion",listaorganizaciones.get(viewHolder.getAdapterPosition()).getDocumento());
                i.putExtra("nombre_organizacion",listaorganizaciones.get(viewHolder.getAdapterPosition()).getNombre());
                i.putExtra("nombre_dirigente",listaorganizaciones.get(viewHolder.getAdapterPosition()).getProfesion());
               mcontext.startActivity(i);

            }
        });

        return viewHolder;
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
        ConstraintLayout view_container;
        public OrganizacionHolder(View itemView) {
            super(itemView);
            view_container=itemView.findViewById(R.id.contenedord);
            txtDocumento=(TextView)itemView.findViewById(R.id.txtclave);
            txtNombre= (TextView) itemView.findViewById(R.id.txtNombre);
            txtProfesion= (TextView) itemView.findViewById(R.id.txtProfesion);
            imageView=(ImageView) itemView.findViewById(R.id.imageView2);
        }
    }
}
