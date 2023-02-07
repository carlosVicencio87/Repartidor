package com.creathor.repartidores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterContenidoPedidoEspera extends RecyclerView.Adapter<AdapterContenidoPedidoEspera.ViewHolderRecycler>{
    private ArrayList<ListaContenidoPedidos> contenidoPedidorecycler;
    AdapterContenidoPedidoEspera.ViewHolderRecycler viewholderContenidoPedido;
    private  RecyclerView recyclerView;
    private Context context;
    private String id,nombre,cantidad,total,precio,extras,imagen,seccion,strNotaMesero,estatus;

    private EditText nota_mesero;

    private AdapterListaPedidos activity;


    public AdapterContenidoPedidoEspera(ArrayList<ListaContenidoPedidos> contenidoPedidoreycler )
    {
        this.contenidoPedidorecycler =contenidoPedidoreycler;
    }
    @Override
    public AdapterContenidoPedidoEspera.ViewHolderRecycler onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item4,parent,false);
        context=parent.getContext();
        vista.setFocusable(true);
        return new AdapterContenidoPedidoEspera.ViewHolderRecycler(vista);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterContenidoPedidoEspera.ViewHolderRecycler holder, int position) {
        viewholderContenidoPedido =holder;
        id = contenidoPedidorecycler.get(position).getId();
        nombre= contenidoPedidorecycler.get(position).getNombre();
        cantidad= contenidoPedidorecycler.get(position).getCantidad();
        total= contenidoPedidorecycler.get(position).getTotal();
        precio= contenidoPedidorecycler.get(position).getPrecio();
        extras= contenidoPedidorecycler.get(position).getExtras();
        imagen= contenidoPedidorecycler.get(position).getImagen();
        seccion= contenidoPedidorecycler.get(position).getSeccion();
        strNotaMesero=contenidoPedidorecycler.get(position).getNota_mesero();
        estatus=contenidoPedidorecycler.get(position).getEstatus();
        // fecha_final      = pedidosrecycler.get(position).getFecha_final();
        Log.e("estatus",estatus);
        holder.id_content.setText(id);
        holder.name.setText(nombre);
        holder.cant.setText(cantidad);
        holder.totl.setText(total);
        holder.price.setText(precio);
        holder.extrs.setText(extras);
        holder.image.setText(imagen);
        holder.section.setText(seccion);
        holder.nota_meser.setText(strNotaMesero);
        holder.status.setText(estatus);




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
        TextView id_content,name,cant,totl,price,extrs,image,section,status,nota_meser;

        public ViewHolderRecycler(View itemView) {
            super(itemView);
            id_content =(TextView)itemView.findViewById(R.id.id);
            name =(TextView)itemView.findViewById(R.id.nombre);
            cant =(TextView)itemView.findViewById(R.id.cantidad);
            totl =(TextView)itemView.findViewById(R.id.total);
            price =(TextView)itemView.findViewById(R.id.precio);
            extrs =(TextView)itemView.findViewById(R.id.extras);
            image =(TextView)itemView.findViewById(R.id.imagen);
            status=itemView.findViewById(R.id.estatus);

            section =(TextView)itemView.findViewById(R.id.seccion);
            nota_meser=itemView.findViewById(R.id.nota_mesero);
        }
    }

}
