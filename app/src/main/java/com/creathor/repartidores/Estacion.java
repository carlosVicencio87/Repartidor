package com.creathor.repartidores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Estacion extends AppCompatActivity {

    private ExecutorService executorService;

    private LinearLayout caja_lista_pedidos_recycler,caja_pedir_pedidos,caja_lista_pedidos,caja_pedidos_espera_l,caja_recycler_pedidos,caja_lista_espera_recycler;
    private ConstraintLayout caja_pedido_cliente,caja_confirmar_mecero,caja_velo_mecero;
    private TextView pedir_pedidos,confirmar_mecero,confirmar_no,confirmar_si,texto_asignador,enviar_pedido_cocina;
    public ArrayList<SpinnerModel> listaMeceros = new ArrayList<>();
    private AdapterMeceros adapterMeceros;
    private String seleccion_mecero,selector_pedidos,strCadena,
            id_pedido_actual,id_encontrada,comanda_encontrada,mesa_encontrada,precio_encontrado,fecha_encontrada,
            id_mesero,idSesion,meseroAsignado,strContenido,notaMesero,notaMesero_actual,strId_mesero,strId,strNotaMesero;
    private Estacion activity;
    private RecyclerView lista_pedidos_recycler,lista_espera_recycler,contenido_pedido;
    private AdapterListaPedidos adapterListaPedidos,adapterListaEspera;
    private ArrayList <ListaPedidosRecycler> listaPedidosRecyclers, listaPedidosAsignados;
    private  AdapterContenidoPedido adapterContenidoPedido;
    private ArrayList <ListaContenidoPedidos> listaContenidoPedidos;
   /* private ArrayList <ListaMeserosDisponibles> listaMeserosDisponibles;
    private AdapterMeserosDisponibles adapterMeserosDisponibles;*/
    private JSONArray json_pedido,json_contenido_pedido;
    private Context context;
    private static String SERVIDOR_CONTROLADOR;
    private SharedPreferences id_SesionSher,idSher,nombreMeseroSher;
    private SharedPreferences.Editor editorNombreMesero;
    int id_contenido_actual;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_estacion);

        activity = this;
        context=this;
        SERVIDOR_CONTROLADOR = new Servidor().getIplocal();
        caja_lista_pedidos_recycler=findViewById(R.id.caja_lista_pedidos_recycler);
        contenido_pedido=findViewById(R.id.contenido_pedido);
        lista_pedidos_recycler=findViewById(R.id.lista_pedidos_recycler);
        caja_pedido_cliente =findViewById(R.id.caja_pedido_cliente);
        caja_confirmar_mecero=findViewById(R.id.caja_confirmar_mecero);
        caja_velo_mecero=findViewById(R.id.caja_velo_mecero);
        confirmar_no=findViewById(R.id.confirmar_no);
        confirmar_si=findViewById(R.id.confirmar_si);
        texto_asignador=findViewById(R.id.texto_asignador);
        lista_espera_recycler=findViewById(R.id.lista_espera_recycler);
        executorService= Executors.newSingleThreadExecutor();
        enviar_pedido_cocina=findViewById(R.id.enviar_pedido_cocina);
       /* setListaMeceros();*/

        caja_lista_pedidos=findViewById(R.id.caja_lista_pedidos);
        caja_pedidos_espera_l=findViewById(R.id.caja_pedidos_espera_l);
        caja_recycler_pedidos=findViewById(R.id.caja_recycler_pedidos);
        Log.e("meseros2",""+editorNombreMesero);
        caja_lista_espera_recycler=findViewById(R.id.caja_lista_espera_recycler);

        /* checkModel=modeloSHER.getString("modelo","no");*/
        // pedir_pedidos_meceros();

        listaPedidosRecyclers=new ArrayList<>();
        lista_pedidos_recycler.setLayoutManager(new LinearLayoutManager(Estacion.this, LinearLayoutManager.VERTICAL, false));
        listaPedidosAsignados=new ArrayList<>();
        lista_espera_recycler.setLayoutManager(new LinearLayoutManager(Estacion.this,LinearLayoutManager.VERTICAL,false));

        listaContenidoPedidos=new ArrayList<>();
        contenido_pedido.setLayoutManager(new LinearLayoutManager(Estacion.this,LinearLayoutManager.VERTICAL,false));
     /*   listaMeserosDisponibles=new ArrayList<>();
        meceros_disponibles.setLayoutManager(new LinearLayoutManager(Estacion.this,LinearLayoutManager.VERTICAL,false));


*/

        id_SesionSher=getSharedPreferences("Usuario",this.MODE_PRIVATE);
        idSesion= id_SesionSher.getString("idSesion","no hay");
        idSher=getSharedPreferences("Usuario",this.MODE_PRIVATE);
        id_mesero = idSher.getString("id","no hay");
        nombreMeseroSher=getSharedPreferences("Usuario",this.MODE_PRIVATE);
        meseroAsignado = idSher.getString("nombre","no hay");
        pedir_pedidos();




        enviar_pedido_cocina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caja_velo_mecero.setVisibility(view.VISIBLE);
                caja_confirmar_mecero.setVisibility(View.VISIBLE);

            }
        });


        confirmar_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caja_velo_mecero.setVisibility(view.GONE);
                caja_confirmar_mecero.setVisibility(View.GONE);
            }
        });
        confirmar_si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                caja_pedido_cliente.setVisibility(View.GONE);
                caja_lista_pedidos_recycler.setVisibility(View.VISIBLE);
                for (int i=0;i<listaPedidosRecyclers.size();i++){
                    String id_tmp=listaPedidosRecyclers.get(i).getId();

                    if(id_tmp.equals(id_pedido_actual)){
                        id_encontrada=listaPedidosRecyclers.get(i).getId();
                        listaPedidosRecyclers.remove(i);


                        executorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                enviar_pedido_cocina();
                                Log.e("se envio a cocina","revisar cocina");
                            }
                        });
                    }
                }
                lista_pedidos_recycler.setAdapter(adapterListaPedidos);
                adapterListaEspera=new AdapterListaPedidos(listaPedidosAsignados);
                lista_espera_recycler.setAdapter(adapterListaEspera);

            }
        });


        caja_pedidos_espera_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caja_recycler_pedidos.setVisibility(View.GONE);
                caja_lista_espera_recycler.setVisibility(View.VISIBLE);
            }
        });
        caja_lista_pedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caja_recycler_pedidos.setVisibility(View.VISIBLE);
                caja_lista_espera_recycler.setVisibility(View.GONE);
            }
        });

    }
    public void pedir_pedidos()
    {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST,  SERVIDOR_CONTROLADOR+"pedidosMeseroEstacion.php",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        String limpio=response;
                        Log.e("jsonObject33333:",""+response);
                        Log.e("jsonObject333333:",""+limpio);



                        try {

                            json_pedido=new JSONArray(response);
                            for (int i=0;i<json_pedido.length();i++){
                                JSONObject jsonObject = json_pedido.getJSONObject(i);

                                //Log.e("nombreMovies", String.valueOf(jsonObject));

                                strId = jsonObject.getString("id");
                                String strMesa = jsonObject.getString("mesa");
                                String strComanda = jsonObject.getString("comanda");
                                String strPrecio= jsonObject.getString("precio");
                                String strFecha_ingreso = jsonObject.getString("fecha_ingreso");
                                 strId_mesero=jsonObject.getString("id_mesero");
                                String strMecero=jsonObject.getString("meseroAsignado");

                                String strEstado=jsonObject.getString("estado");
                                strContenido=jsonObject.getString("contenido");
                                String strNotaMesero=jsonObject.getString("nota_mesero");

                                String strFecha_entrega = jsonObject.getString("fecha_entrega");
                                String strFecha_final = jsonObject.getString("fecha_final");

                                listaPedidosRecyclers.add(new ListaPedidosRecycler(strId,strMesa,strComanda,strPrecio,strFecha_ingreso,strId_mesero,strMecero,strEstado,strContenido,strNotaMesero));
                                Log.e("idm",strId);
                                Log.e("idmesero",strId_mesero);
                                Log.e("contenidos",strContenido);

                            }

                            adapterListaPedidos=new AdapterListaPedidos(listaPedidosRecyclers);
                            lista_pedidos_recycler.setAdapter(adapterListaPedidos);
                            //recycler_movies.scrollToPosition(0);



                            //recycler_movies.getChildAt(0).findViewById(R.id.contenedor).requestFocus();
                            //bloquearMenu();


                        } catch (JSONException e) {
                            Log.e("errorRespuestaMovies", String.valueOf(e));
                        }
                        Log.e("jsonapedidos:",""+json_pedido);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }



                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("id_mesero", id_mesero);
                map.put("meseroAsignado",meseroAsignado);
                return map;
            }
        };
        requestQueue.add(request);
    }




    public void enviarIndiceConNota(int indice_contenido,String notaMesero){
        id_contenido_actual=indice_contenido;
        strNotaMesero=notaMesero;
        Log.e("id_contenido_actual", String.valueOf(id_contenido_actual));
        Log.e("strNotaMesero",strNotaMesero);
        try {
            JSONObject jsonObject=json_contenido_pedido.getJSONObject(indice_contenido);
            jsonObject.put("nota_mesero",strNotaMesero);
            jsonObject=json_contenido_pedido.getJSONObject(indice_contenido);
            Log.e("aaaaa",jsonObject.get("nota_mesero").toString());
            Log.e("nuevo_json_PEDIDOS", String.valueOf(json_contenido_pedido));
            strContenido= String.valueOf(json_contenido_pedido);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    public void aceptar_pedido(String id_pedido){
        id_pedido_actual=id_pedido;
        caja_pedido_cliente.setVisibility(View.VISIBLE);
        caja_lista_pedidos_recycler.setVisibility(View.GONE);
        Log.e("id_activyty",""+id_pedido);
        try {
            json_contenido_pedido=new JSONArray(strContenido);
            for(int i=0;i<json_contenido_pedido.length();i++){
                JSONObject jsonObject = json_contenido_pedido.getJSONObject(i);
                Log.e("jsonObject2:",""+jsonObject);
                String strId = jsonObject.getString("id");
                String strNombre = jsonObject.getString("nombre");
                String strCantidad = jsonObject.getString("cantidad");
                String strTotal= jsonObject.getString("total");
                String strPrecio = jsonObject.getString("precio");
                String strExtras=jsonObject.getString("extras");
                String strImagen=jsonObject.getString("imagen");
                String strSeccion=jsonObject.getString("seccion");
                strNotaMesero=jsonObject.getString("nota_mesero");
                Log.e("ID",strId);
                Log.e("NOMBRE",strNombre);
                Log.e("CANTIDAD",strCantidad);
                Log.e("TOTAL",strTotal);
                Log.e("PRECIO",strPrecio);
                Log.e("EXTRAS",strExtras);
                Log.e("IMAGEN",strImagen);
                Log.e("SECCION",strSeccion);
                listaContenidoPedidos.add(new ListaContenidoPedidos(strId,strNombre,strCantidad,strTotal,strPrecio,strExtras,strImagen,strSeccion,notaMesero));


            }
            adapterContenidoPedido=new AdapterContenidoPedido(listaContenidoPedidos);
            contenido_pedido.setAdapter(adapterContenidoPedido);
            Log.e("jsonContendio",""+json_contenido_pedido);

        } catch (JSONException e) {
            Log.e("errorRespuesJSON", String.valueOf(e));
        }


    }
  public void enviar_pedido_cocina()
    {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST,  SERVIDOR_CONTROLADOR+"enviar_pedido_cocina.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("respuesta4:",response + "sal");
                        if(response.equals("success")){
                            //Intent intent = new Intent(Registro.this,RegistroExitoso.class);
                            //startActivity(intent);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("respuesta4Error:",error + "error");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("id",  id_encontrada);
                map.put("id_mesero", strId_mesero);
                map.put("meseroAsignado",meseroAsignado);
                map.put("contenido",strContenido);
                Log.e("id", id_encontrada);
                Log.e("idmesero",strId_mesero);
                Log.e("mesero",meseroAsignado);
//                Log.e("nota",notaMesero_actual);

                return map;
            }
        };
        requestQueue.add(request);
    }
}
