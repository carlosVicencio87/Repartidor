package com.creathor.repartidores;


import androidx.appcompat.app.AppCompatActivity;

public class ListaPedidosRecycler extends AppCompatActivity {

    private String id,mesa,comanda,precio,fecha_ingreso,fecha_entrega,
            fecha_final, mecero_asignado,id_mesero,estadoPedido,nota_mecero,contenido;



    public String getId() {
        return id;
    }
    public String getMesa() {
        return mesa;
    }
    public String getComanda() {
        return comanda;
    }
    public String getPrecio() {
        return precio;
    }
    public String getFecha_ingreso() { return fecha_ingreso;}
    public String getId_mesero() { return id_mesero; }
    public String getNota_mecero() { return nota_mecero; }

    public String getMecero_asignado() { return mecero_asignado; }
    public String getEstadoPedido() { return estadoPedido; }
    public String getContenido() { return contenido; }

   /* public String getFecha_entrega() { return fecha_entrega; }
    public String getFecha_final() { return fecha_final;}*/



    public void setId(String id)                               {
        this.id = id;
    }
    public void setMesa(String mesa) {
        this.mesa = mesa;
    }
    public void setComanda(String comanda) {
        this.comanda = comanda;
    }
    public void setPrecio(String precio) { this.precio = precio; }
    public void setFecha_ingreso(String fecha_ingreso) {this.fecha_ingreso = fecha_ingreso;}
    public void setId_mesero(String id_mesero) { this.id_mesero = id_mesero;}
    public void setMecero_asignado(String mecero_asignado) { this.mecero_asignado = mecero_asignado;}
    public void setEstadoPedido(String estadoPedido) { this.estadoPedido = estadoPedido;}
    public void setContenido(String contenido) { this.contenido = contenido;}
    public void setNota_mecero(String nota_mecero) { this.nota_mecero = nota_mecero;}

   /* public void setFecha_entrega(String fecha_entrega) { this.fecha_entrega = fecha_entrega;}
    public void setFecha_final(String fecha_final) {this.fecha_final = fecha_final;}*/



    public ListaPedidosRecycler(String id_food, String mes, String comand, String price, String date_star,String mecer_asigned,
                                String statePed,String id_meser,String content,String note_mecer){

        this.id =id_food;
        this.mesa=mes;
        this.comanda=comand;
        this.precio=price;
        this.fecha_ingreso=date_star;
        this.nota_mecero=note_mecer;

        this.id_mesero=id_meser;
        this.mecero_asignado =mecer_asigned;
        this.estadoPedido=statePed;
        this.contenido=content;
       /* this.fecha_entrega=date_entrega;
        this.fecha_final=date_end;*/

    }
}