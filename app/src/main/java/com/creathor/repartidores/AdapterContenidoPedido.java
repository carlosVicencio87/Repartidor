package com.creathor.repartidores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterContenidoPedido extends RecyclerView.Adapter<AdapterContenidoPedido.ViewHolderRecycler>{
    private ArrayList<ListaContenidoPedidos> contenidoPedidorecycler;
    ViewHolderRecycler viewholderContenidoPedido;
    private  RecyclerView recyclerView;
    private Context context;
    private String id,nombre,cantidad,total,precio,extras,imagen,seccion,strNotaMesero;

    private EditText nota_mesero;

    private AdapterListaPedidos activity;


    public AdapterContenidoPedido(ArrayList<ListaContenidoPedidos> contenidoPedidoreycler )
    {
        this.contenidoPedidorecycler =contenidoPedidoreycler;
    }
    @Override
    public ViewHolderRecycler onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item2,parent,false);
        context=parent.getContext();
        vista.setFocusable(true);
        return new ViewHolderRecycler(vista);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterContenidoPedido.ViewHolderRecycler holder, int position) {
        viewholderContenidoPedido =holder;
        id = contenidoPedidorecycler.get(position).getId();
        nombre= contenidoPedidorecycler.get(position).getNombre();
        cantidad= contenidoPedidorecycler.get(position).getCantidad();
        total= contenidoPedidorecycler.get(position).getTotal();
        precio= contenidoPedidorecycler.get(position).getPrecio();
        extras= contenidoPedidorecycler.get(position).getExtras();
        imagen= contenidoPedidorecycler.get(position).getImagen();
        seccion= contenidoPedidorecycler.get(position).getSeccion();
        // fecha_final      = pedidosrecycler.get(position).getFecha_final();

        holder.id_content.setText(id);
        holder.name.setText(nombre);
        holder.cant.setText(cantidad);
        holder.totl.setText(total);
        holder.price.setText(precio);
        holder.extrs.setText(extras);
        holder.image.setText(imagen);
        holder.section.setText(seccion);
        holder.nota_meser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean tieneFoco) {
                    if (!tieneFoco){
                        Toast.makeText(context.getApplicationContext(),"ya tiene la nota",Toast.LENGTH_LONG).show();
                        strNotaMesero=holder.nota_meser.getText().toString();
                        Log.e("nota_de_pedido",strNotaMesero);
                        int indice_actual=holder.getAdapterPosition();
                        Log.e("indice_de_pedido", String.valueOf(indice_actual));
                        ((Estacion)context).enviarIndiceConNota(indice_actual,strNotaMesero);


                    }
            }
        });
        //holder.date_end.setText(fecha_final);
//        holder.send_kitchen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                holder.box_content_velo_mecero.setVisibility(View.VISIBLE);
//
//
//            }
//        });
//        holder.confirm_no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                holder.box_content_velo_mecero.setVisibility(View.GONE);
//
//            }
//        });
//       holder.confirm_yes.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               holder.box_content_velo_mecero.setVisibility(View.GONE);
//
//               int posicion=holder.getAdapterPosition();
//               id = contenidoPedidorecycler.get(posicion).getId();
//               Log.e("id","2"+id);
//
//               Log.e("NOTA DE MESERO",""+strNotaMesero);
//
//           }
//       });


        //holder.list_pedidos.setText(lista_pedidos);
    }
    public void pedidos_espera(String id_pedido){

        Log.e("id_activyty",""+id_pedido);
    }

    @Override
    public int getItemCount(){
        return contenidoPedidorecycler.size();

    }
    public class ViewHolderRecycler extends RecyclerView.ViewHolder {
        TextView id_content,name,cant,totl,price,extrs,image,section;
        EditText nota_meser;

        public ViewHolderRecycler(View itemView) {
            super(itemView);
            id_content =(TextView)itemView.findViewById(R.id.id);
            name =(TextView)itemView.findViewById(R.id.nombre);
            cant =(TextView)itemView.findViewById(R.id.cantidad);
            totl =(TextView)itemView.findViewById(R.id.total);
            price =(TextView)itemView.findViewById(R.id.precio);
            extrs =(TextView)itemView.findViewById(R.id.extras);
            image =(TextView)itemView.findViewById(R.id.imagen);


            section =(TextView)itemView.findViewById(R.id.seccion);
            nota_meser=itemView.findViewById(R.id.nota_mesero);



        }
    }

}
