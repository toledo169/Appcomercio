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

import com.example.oaxacacomercio.Mapas.MapaActivity;
import com.example.oaxacacomercio.Mapas.MapavendedorActivity;
import com.example.oaxacacomercio.Modelos.Vendedor;
import com.example.oaxacacomercio.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DetallesVendedorAdapter extends RecyclerView.Adapter<DetallesVendedorAdapter.VendedorHolder> {
    List<Vendedor> listavendedoresdetalle;
    Dialog dialog;
    Context mcontext;
    public DetallesVendedorAdapter(List<Vendedor> listavendedoresdetalle, Context mcontext) {
        this.listavendedoresdetalle = listavendedoresdetalle;
        this.mcontext=mcontext;
    }

    @NonNull
    @Override
    public VendedorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.vistavendedor,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        final VendedorHolder vendedorHolder= new VendedorHolder(vista);
        dialog=new Dialog(mcontext);
        dialog.setContentView(R.layout.detallesvendedorpop);
        vendedorHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView dialogname=(TextView) dialog.findViewById(R.id.nombrevendedor);
                TextView dialogapp=(TextView)dialog.findViewById(R.id.apellidosvendedor);
                TextView girovend=(TextView)dialog.findViewById(R.id.giro);
                TextView nameorgani=(TextView)dialog.findViewById(R.id.nomorganizacion);
                TextView nameactividad=(TextView)dialog.findViewById(R.id.nomactividad);
                TextView namezona= (TextView)dialog.findViewById(R.id.zona);
                Button vermapa=(Button)dialog.findViewById(R.id.btn_mapa);
                dialogname.setText(listavendedoresdetalle.get(vendedorHolder.getAdapterPosition()).getNombrev());
                dialogapp.setText(listavendedoresdetalle.get(vendedorHolder.getAdapterPosition()).getApellido_paterno() +" "+ listavendedoresdetalle.get(vendedorHolder.getAdapterPosition()).getApellido_materno() );
                girovend.setText(listavendedoresdetalle.get(vendedorHolder.getAdapterPosition()).getGiro());
                nameorgani.setText(listavendedoresdetalle.get(vendedorHolder.getAdapterPosition()).getNomorganizacion());
                nameactividad.setText(listavendedoresdetalle.get(vendedorHolder.getAdapterPosition()).getActividad());
                namezona.setText(listavendedoresdetalle.get(vendedorHolder.getAdapterPosition()).getNomzona());
                Toast.makeText(mcontext,"vendedor seleccionado"+String.valueOf(vendedorHolder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
                dialog.show();

                vermapa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent= new Intent(mcontext, MapavendedorActivity.class);
                        intent.putExtra("latitud",String.valueOf(listavendedoresdetalle.get(vendedorHolder.getAdapterPosition()).getLatitud()));
                        intent.putExtra("longitud",String.valueOf(listavendedoresdetalle.get(vendedorHolder.getAdapterPosition()).getLongitud()));
                        intent.putExtra("name",listavendedoresdetalle.get(vendedorHolder.getAdapterPosition()).getNombrev());
                        intent.putExtra("apellido_paterno",listavendedoresdetalle.get(vendedorHolder.getAdapterPosition()).getApellido_paterno());
                        intent.putExtra("apellido_materno",listavendedoresdetalle.get(vendedorHolder.getAdapterPosition()).getApellido_materno());
                        mcontext.startActivity(intent);
                    }
                });
            }
        });


        return vendedorHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VendedorHolder holder, int position) {
        holder.txtclave.setText(listavendedoresdetalle.get(position).getId().toString()) ;
        holder.txtnombrev.setText(listavendedoresdetalle.get(position).getNombrev().toString());
        holder.textapellidos.setText(listavendedoresdetalle.get(position).getApellido_paterno().toString());
    }

    @Override
    public int getItemCount() {
        return listavendedoresdetalle.size();
    }

    public class VendedorHolder extends RecyclerView.ViewHolder {
        LinearLayout item;
        TextView txtclave,txtnombrev,textapellidos;
        public VendedorHolder(@NonNull View itemView) {
            super(itemView);
            item=(LinearLayout) itemView.findViewById(R.id.infovendedor);
            txtclave=(TextView)itemView.findViewById(R.id.txtclaveven);
            txtnombrev=(TextView)itemView.findViewById(R.id.txtNombrecalle);
            textapellidos=(TextView)itemView.findViewById(R.id.txtapellidos);
        }
    }
}
