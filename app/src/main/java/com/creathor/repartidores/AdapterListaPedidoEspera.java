package com.creathor.repartidores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterListaPedidoEspera extends RecyclerView.Adapter<AdapterListaPedidoEspera.ViewHolderRecycler>{
    private ArrayList<ListaPedidosRecycler> pedidosrecycler;
    AdapterListaPedidoEspera.ViewHolderRecycler viewholderListaPedidos;
    private  RecyclerView recyclerView;
    private Context context;
    private String id,mesa,comanda,precio,fecha_ingreso,mecero_asignado,nota_mesero,fecha_entrega,fecha_final,estadoPedido,id_mesero;
    private TextView aceptar_pedido;
    private AdapterListaPedidoEspera activity;
    private LinearLayout caja_mecero_asignado;


    public AdapterListaPedidoEspera(ArrayList<ListaPedidosRecycler> pedidosrecycler )
    {
        this.pedidosrecycler =pedidosrecycler;
    }
    @Override
    public AdapterListaPedidoEspera.ViewHolderRecycler onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item3,parent,false);
        context=parent.getContext();
        vista.setFocusable(true);
        return new AdapterListaPedidoEspera.ViewHolderRecycler(vista);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListaPedidoEspera.ViewHolderRecycler holder, int position) {
        viewholderListaPedidos =holder;
        id = pedidosrecycler.get(position).getId();
        mesa= pedidosrecycler.get(position).getMesa();
        comanda= pedidosrecycler.get(position).getComanda();
        precio= pedidosrecycler.get(position).getPrecio();
        fecha_ingreso= pedidosrecycler.get(position).getFecha_ingreso();
        mecero_asignado=pedidosrecycler.get(position).getMecero_asignado();
        estadoPedido=pedidosrecycler.get(position).getEstadoPedido();
        nota_mesero=pedidosrecycler.get(position).getNota_mecero();
        id_mesero=pedidosrecycler.get(position).getId_mesero();
        /*fecha_entrega= pedidosrecycler.get(position).getFecha_entrega();
        fecha_final= pedidosrecycler.get(position).getFecha_final();*/




        holder.id_food.setText(id);
        holder.mes.setText(mesa);
        holder.comand.setText(comanda);
        holder.price.setText(precio);
        holder.date_star.setText(fecha_ingreso);
        holder.statePedido.setText(estadoPedido);
        holder.mecer_asigned.setText(mecero_asignado);
        holder.note_meser.setText(nota_mesero);

        holder.box_detail_pedidos_recycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int posiTion2=holder.getAdapterPosition();
                id = pedidosrecycler.get(posiTion2).getId();
                fecha_ingreso=pedidosrecycler.get(posiTion2).getFecha_ingreso();
                mesa=pedidosrecycler.get(posiTion2).getMesa();
                precio=pedidosrecycler.get(posiTion2).getPrecio();
                comanda=pedidosrecycler.get(posiTion2).getComanda();
                mecero_asignado=pedidosrecycler.get(posiTion2).getMecero_asignado();
                estadoPedido=pedidosrecycler.get(posiTion2).getEstadoPedido();
                Log.e("id","2"+id);
                ((Estacion)context).mostrarDetalleEspera(id,fecha_ingreso,mesa,precio,comanda,mecero_asignado,id_mesero,estadoPedido);


            }
        });
        Log.e("meceero","1"+mecero_asignado);


        //holder.list_pedidos.setText(lista_pedidos);
    }

    @Override
    public int getItemCount(){
        return pedidosrecycler.size();

    }
    public class ViewHolderRecycler extends RecyclerView.ViewHolder {
        TextView id_food,mes,comand,price,date_star,mecer_asigned,date_entrega,date_end,acept_pedido,rech_pedido,statePedido,note_meser;
        LinearLayout box_mecero_asignado,box_decisiones,box_detail_pedidos_recycler,caja_detail_pedidos_espera;


        public ViewHolderRecycler(View itemView) {
            super(itemView);
            id_food =(TextView)itemView.findViewById(R.id.id);
            acept_pedido =(TextView)itemView.findViewById(R.id.aceptar_pedido);
            rech_pedido =(TextView)itemView.findViewById(R.id.rechazar_pedido);
            mes =(TextView)itemView.findViewById(R.id.mesa);
            comand =(TextView)itemView.findViewById(R.id.comanda);
            price =(TextView)itemView.findViewById(R.id.precio);
            date_star =(TextView)itemView.findViewById(R.id.fecha_ingreso);
            date_entrega =(TextView)itemView.findViewById(R.id.fecha_entrega);
            date_end =(TextView)itemView.findViewById(R.id.fecha_final);
            mecer_asigned =(TextView)itemView.findViewById(R.id.mecero_asignado);
            box_mecero_asignado = (LinearLayout) itemView.findViewById(R.id.caja_mecero_asignado);
            statePedido = (TextView) itemView.findViewById(R.id.estadoPedido);

            box_decisiones = (LinearLayout) itemView.findViewById(R.id.caja_decisiones);
            note_meser=itemView.findViewById(R.id.nota_mesero);
            box_detail_pedidos_recycler=itemView.findViewById(R.id.caja_detalle_pedidos_espera);
        }
    }

}
