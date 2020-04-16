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

import com.example.oaxacacomercio.MapaActivity;
import com.example.oaxacacomercio.Modelos.Vendedor;
import com.example.oaxacacomercio.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class VendedorAdapter extends  RecyclerView.Adapter<VendedorAdapter.VendedorHolder>{
    List<Vendedor>listavendedor;
    Dialog dialog;
    Context mcontext;
    public String opcion="vendedor";
    public VendedorAdapter(List<Vendedor> listavendedor,Context mcontext) {
        this.listavendedor = listavendedor;
        this.mcontext=mcontext;
    }

    @Override
    public VendedorHolder onCreateViewHolder( ViewGroup parent, int viewType) {
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
                dialogname.setText(listavendedor.get(vendedorHolder.getAdapterPosition()).getNombrev());
             dialogapp.setText(listavendedor.get(vendedorHolder.getAdapterPosition()).getApellido_paterno() +" "+ listavendedor.get(vendedorHolder.getAdapterPosition()).getApellido_materno() );
             girovend.setText(listavendedor.get(vendedorHolder.getAdapterPosition()).getGiro());
             nameorgani.setText(listavendedor.get(vendedorHolder.getAdapterPosition()).getNomorganizacion());
             nameactividad.setText(listavendedor.get(vendedorHolder.getAdapterPosition()).getActividad());
             namezona.setText(listavendedor.get(vendedorHolder.getAdapterPosition()).getNomzona());
             Toast.makeText(mcontext,"vendedor seleccionado"+String.valueOf(vendedorHolder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
                  dialog.show();

                  vermapa.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          Intent intent= new Intent(mcontext,MapaActivity.class);
                          intent.putExtra("vendedor",opcion);
                          intent.putExtra("latitud",String.valueOf(listavendedor.get(vendedorHolder.getAdapterPosition()).getLatitud()));
                          intent.putExtra("longitud",String.valueOf(listavendedor.get(vendedorHolder.getAdapterPosition()).getLongitud()));
                         intent.putExtra("name",listavendedor.get(vendedorHolder.getAdapterPosition()).getNombrev());
                          mcontext.startActivity(intent);
                      }
                  });

            }

        });

        return vendedorHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VendedorHolder holder, int position) {
        holder.txtclave.setText(listavendedor.get(position).getId().toString()) ;
        holder.txtnombrev.setText(listavendedor.get(position).getNombrev().toString());
        holder.textapellidos.setText(listavendedor.get(position).getApellido_paterno().toString());
    }

    @Override
    public int getItemCount() {
        return listavendedor.size();
    }

    public class VendedorHolder extends RecyclerView.ViewHolder {
        LinearLayout item;
        TextView txtclave, txtnombrev, textapellidos;
        Button vermapa;

        public VendedorHolder(View itemView) {
            super(itemView);
            item = (LinearLayout) itemView.findViewById(R.id.infovendedor);
            txtclave = (TextView) itemView.findViewById(R.id.txtclaveven);
            txtnombrev = (TextView) itemView.findViewById(R.id.txtNombrecalle);
            textapellidos = (TextView) itemView.findViewById(R.id.txtapellidos);

        }


    }
}
