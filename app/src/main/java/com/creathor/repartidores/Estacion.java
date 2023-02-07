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

    private LinearLayout caja_lista_pedidos_recycler,caja_pedir_pedidos,caja_lista_pedidos,caja_pedidos_espera_l,
            caja_recycler_pedidos,caja_lista_espera_recycler,caja_pedidos_listo,caja_lista_preparados_recycler;
    private ConstraintLayout caja_pedido_cliente,caja_confirmar_mecero,caja_velo_mecero,caja_pedido_cliente_espera;
    private TextView pedir_pedidos,confirmar_mecero,confirmar_no,confirmar_si,texto_asignador,enviar_pedido_cocina;
    public ArrayList<SpinnerModel> listaMeceros = new ArrayList<>();
    private AdapterMeceros adapterMeceros;
    private String seleccion_mecero,selector_pedidos,strCadena,adonde_vas,
            id_pedido_actual,id_encontrada,comanda_encontrada,mesa_encontrada,precio_encontrado,fecha_encontrada,
            id_mesero,idSesion,meseroAsignado,strContenido,notaMesero,
            notaMesero_actual,strId_mesero,strId,strNotaMesero,strEstatus;
    private Estacion activity;
    private RecyclerView lista_pedidos_recycler,lista_espera_recycler,contenido_pedido,contenido_pedido_espera,lista_preparados_recycler;
    private AdapterListaPedidos adapterListaPedidos;
    private AdapterListaPedidoEspera adapterListaPedidoEspera;
    private ArrayList <ListaPedidosRecycler> listaPedidosRecyclers, listaPedidosEspera;
    private  AdapterContenidoPedido adapterContenidoPedido;
    private AdapterContenidoPedidoEspera adapterContenidoPedidoEspera;
    private ArrayList <ListaContenidoPedidos> listaContenidoPedidos,listaContenidoPedidosEspera,listaContenidosPreparado;
    private AdapterListaPreparados adapterListaPreparados;
   /* private ArrayList <ListaMeserosDisponibles> listaMeserosDisponibles;
    private AdapterMeserosDisponibles adapterMeserosDisponibles;*/
    private JSONArray json_pedido,json_contenido_pedido,arregloObjeto,json_pedidos_preparados,json_contenido_pedido_preparado;
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
        caja_pedido_cliente_espera=findViewById(R.id.caja_pedido_cliente_espera);
        contenido_pedido_espera=findViewById(R.id.contenido_pedido_espera);
        caja_pedidos_listo=findViewById(R.id.caja_pedidos_listo);
        caja_lista_preparados_recycler=findViewById(R.id.caja_lista_preparados_recycler);
        lista_preparados_recycler=findViewById(R.id.lista_preparados_recycler);
        /* checkModel=modeloSHER.getString("modelo","no");*/
        // pedir_pedidos_meceros();

        listaPedidosRecyclers=new ArrayList<>();
        lista_pedidos_recycler.setLayoutManager(new LinearLayoutManager(Estacion.this, LinearLayoutManager.VERTICAL, false));
        listaPedidosEspera=new ArrayList<>();
        lista_espera_recycler.setLayoutManager(new LinearLayoutManager(Estacion.this,LinearLayoutManager.VERTICAL,false));

        listaContenidoPedidos=new ArrayList<>();
        contenido_pedido.setLayoutManager(new LinearLayoutManager(Estacion.this,LinearLayoutManager.VERTICAL,false));
        listaContenidoPedidosEspera=new ArrayList<>();
        contenido_pedido_espera.setLayoutManager(new LinearLayoutManager(Estacion.this,LinearLayoutManager.VERTICAL,false));
        /*listaMeserosDisponibles=new ArrayList<>();
        meceros_disponibles.setLayoutManager(new LinearLayoutManager(Estacion.this,LinearLayoutManager.VERTICAL,false));*/
        listaContenidosPreparado=new ArrayList<>();
        lista_preparados_recycler.setLayoutManager(new LinearLayoutManager(Estacion.this,LinearLayoutManager.VERTICAL,false));
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


            }
        });


        caja_pedidos_espera_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caja_recycler_pedidos.setVisibility(View.GONE);
                caja_lista_preparados_recycler.setVisibility(View.GONE);
                caja_lista_espera_recycler.setVisibility(View.VISIBLE);
                pedir_pedidos_espera();
                adonde_vas="pedidosEspera";
                Log.e("adonde",adonde_vas);


            }
        });
        caja_lista_pedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caja_recycler_pedidos.setVisibility(View.VISIBLE);
                caja_lista_espera_recycler.setVisibility(View.GONE);
                caja_lista_preparados_recycler.setVisibility(View.GONE);

                pedir_pedidos();
            }
        });
        caja_pedidos_listo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caja_recycler_pedidos.setVisibility(View.GONE);
                caja_lista_espera_recycler.setVisibility(View.GONE);
                caja_lista_preparados_recycler.setVisibility(View.VISIBLE);
                pedir_pedidos_preparados();
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
                        listaPedidosRecyclers.clear();
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
                            adonde_vas="principal";
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

    public void pedir_pedidos_espera()
    {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST,  SERVIDOR_CONTROLADOR+"pedidosMeseroEstacionEspera.php",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        listaPedidosEspera.clear();
                        String limpio=response;
                        Log.e("jsonObject33333:",""+response);
                        Log.e("jsonObject333333:",""+limpio);



                        try {

                            json_pedido=new JSONArray(response);
                            for (int i=0;i<json_pedido.length();i++){
                                JSONObject jsonObject = json_pedido.getJSONObject(i);
                                Log.e("objetoEspera", String.valueOf(jsonObject));

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

                                listaPedidosEspera.add(new ListaPedidosRecycler(strId,strMesa,strComanda,strPrecio,strFecha_ingreso,strId_mesero,strMecero,strEstado,strContenido,strNotaMesero));
                                Log.e("idm",strId);
                                Log.e("idmesero",strId_mesero);
                                Log.e("contenidos",strContenido);

                            }

                            adapterListaPedidoEspera=new AdapterListaPedidoEspera(listaPedidosEspera);
                            lista_espera_recycler.setAdapter(adapterListaPedidoEspera);
                            adonde_vas="principal";
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
    public  void mostrarDetalleEspera(String id_pedido,String fecha_ingreso,String mesa,String precio, String comanda, String mecero_asignado, String id_mesero,String estado){
        id_pedido_actual=id_pedido;
        caja_pedido_cliente_espera.setVisibility(View.VISIBLE);
        caja_lista_espera_recycler.setVisibility(View.GONE);
        caja_lista_pedidos_recycler.setVisibility(View.GONE);

        adonde_vas="detallePedidoEspera";
        Log.e("adonde",adonde_vas);
        pedir_contenido_pedido();

    }
    public void aceptar_pedido(String id_pedido){
        id_pedido_actual=id_pedido;
        caja_pedido_cliente.setVisibility(View.VISIBLE);
        caja_lista_pedidos_recycler.setVisibility(View.GONE);
        adonde_vas="detallePedido";
        listaContenidoPedidos.clear();
        Log.e("adonde",adonde_vas);
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
                strEstatus=jsonObject.getString("estatus");

                Log.e("ID",strId);
                Log.e("NOMBRE",strNombre);
                Log.e("CANTIDAD",strCantidad);
                Log.e("TOTAL",strTotal);
                Log.e("PRECIO",strPrecio);
                Log.e("EXTRAS",strExtras);
                Log.e("IMAGEN",strImagen);
                Log.e("SECCION",strSeccion);
                listaContenidoPedidos.add(new ListaContenidoPedidos(strId,strNombre,strCantidad,strTotal,strPrecio,strExtras,strImagen,strSeccion,notaMesero,strEstatus));


            }
            adapterContenidoPedido=new AdapterContenidoPedido(listaContenidoPedidos);
            contenido_pedido.setAdapter(adapterContenidoPedido);
            Log.e("jsonContendio",""+json_contenido_pedido);



        } catch (JSONException e) {
            Log.e("errorRespuesJSON", String.valueOf(e));
        }


    }
    public  void pedir_contenido_pedido(){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST,  SERVIDOR_CONTROLADOR+"contenido_pedido.php",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            listaContenidoPedidosEspera.clear();
                            json_contenido_pedido=new JSONArray(response);
                            for (int i=0;i<json_contenido_pedido.length();i++){
                                JSONObject jsonObject = json_contenido_pedido.getJSONObject(i);
                                Log.e("jsonObjectcotenidEspera",""+jsonObject);
                                String strContenido = jsonObject.getString("contenido");
                                Log.e("objetoContenido",""+strContenido);
                                arregloObjeto=new JSONArray(strContenido);
                                Log.e("arreglo",""+arregloObjeto);
                                for (int i2=0;i2<arregloObjeto.length();i2++){
                                    JSONObject jsonObjectContenido2=arregloObjeto.getJSONObject(i2);
                                    String strId = jsonObjectContenido2.getString("id");
                                    String strNombre = jsonObjectContenido2.getString("nombre");
                                    String strCantidad = jsonObjectContenido2.getString("cantidad");
                                    String strTotal = jsonObjectContenido2.getString("total");
                                    String strPrecio2 = jsonObjectContenido2.getString("precio");
                                    String strExtras = jsonObjectContenido2.getString("extras");
                                    String strNota_mesero=jsonObjectContenido2.getString("nota_mesero");
                                    String strEstatus=jsonObjectContenido2.getString("estatus");
                                    String strImagen=jsonObjectContenido2.getString("imagen");
                                    String strSeccion=jsonObjectContenido2.getString("seccion");


                                    listaContenidoPedidosEspera.add(new ListaContenidoPedidos(strId,strNombre,strCantidad,strTotal,strPrecio2,strExtras,strImagen,strSeccion,strNota_mesero,strEstatus));

                                }


                            }

                            adapterContenidoPedidoEspera=new AdapterContenidoPedidoEspera(listaContenidoPedidosEspera);
                            contenido_pedido_espera.setAdapter(adapterContenidoPedidoEspera);

                        } catch (JSONException e) {
                            Log.e("errorRespuestaMovies", String.valueOf(e));
                        }
                        Log.e("jsonPedidos:",""+json_pedido);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("errorDeContenido:",error + "error");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();

                Log.e("idcontenido",id_pedido_actual);
                map.put("id",id_pedido_actual);
                map.put("idSesion",idSesion);

                return map;
            }
        };
        requestQueue.add(request);
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
    public  void pedir_pedidos_preparados(){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST,  SERVIDOR_CONTROLADOR+"pedidosPreparadosEstacion.php",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        String limpio=response;
                        Log.e("jsonObject5555:",""+response);
                        try {
                            json_pedidos_preparados=new JSONArray(response);
                            for (int i=0;i<json_pedidos_preparados.length();i++){
                                JSONObject jsonObject = json_pedidos_preparados.getJSONObject(i);
                                Log.e("objetoPREparados", String.valueOf(jsonObject));

                                //Log.e("nombreMovies", String.valueOf(jsonObject));

                                String strIdPedido = jsonObject.getString("id");
                                String strMesa = jsonObject.getString("mesa");
                                String strComanda = jsonObject.getString("comanda");
                                String strPrecio= jsonObject.getString("precio");
                                String strFecha_ingreso = jsonObject.getString("fecha_ingreso");
                                String strId_mesero=jsonObject.getString("id_mesero");
                                String strMecero=jsonObject.getString("meseroAsignado");
                                String strEstado=jsonObject.getString("estado");
                                strContenido=jsonObject.getString("contenido");
                                Log.e("contenido",strContenido);

                                json_contenido_pedido_preparado=new JSONArray(strContenido);
                                Log.e("jsonContenido", String.valueOf(json_contenido_pedido_preparado));

                                for (int i2=0;i2<json_contenido_pedido_preparado.length();i2++){
                                    JSONObject jsonObject2 = json_contenido_pedido_preparado.getJSONObject(i2);
                                    Log.e("contenido", String.valueOf(jsonObject2));
                                    String strID=jsonObject2.getString("id");
                                    String strNombre = jsonObject2.getString("nombre");
                                    String strCantidad = jsonObject2.getString("cantidad");
                                    String strTotal = jsonObject2.getString("total");
                                    String strPrecio2 = jsonObject2.getString("precio");
                                    String strExtras = jsonObject2.getString("extras");
                                    String strNota_mesero=jsonObject2.getString("nota_mesero");
                                    String strEstatus=jsonObject2.getString("estatus");
                                    String strImagen=jsonObject2.getString("imagen");
                                    String strSeccion=jsonObject2.getString("seccion");

                                    Log.e("estatus",strEstatus);

                                    if (strEstatus.equals("preparado")){
                                        listaContenidosPreparado.add(new ListaContenidoPedidos(strId,strNombre,strCantidad,strTotal,strPrecio2,strExtras,strImagen,strSeccion,strNota_mesero,strEstatus));


                                    }


                                    Log.e("nombre",strNombre);
                                    Log.e("cantidad",strCantidad);
                                    Log.e("total",strTotal);
                                    Log.e("precio2",strPrecio2);
                                    Log.e("extras",strExtras);
                                    Log.e("notaMesero",strNota_mesero);
                                }
                                adapterListaPreparados=new AdapterListaPreparados(listaContenidosPreparado);
                                lista_preparados_recycler.setAdapter(adapterListaPreparados);

                                String strFecha_entrega = jsonObject.getString("fecha_entrega");
                                String strFecha_final = jsonObject.getString("fecha_final");

                                Log.e("idm", strIdPedido);


                            }


                            //recycler_movies.scrollToPosition(0);



                            //recycler_movies.getChildAt(0).findViewById(R.id.contenedor).requestFocus();
                            //bloquearMenu();


                        } catch (JSONException e) {
                            Log.e("errorRespuestaMovies", String.valueOf(e));
                        }
                        Log.e("jsonapedidos:",""+ json_pedidos_preparados);
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
  public void onBackPressed(){

        if (adonde_vas=="detallePedido"){
            caja_pedido_cliente.setVisibility(View.GONE);
            caja_lista_pedidos_recycler.setVisibility(View.VISIBLE);
            adonde_vas="principal";
            Log.e("adonde",adonde_vas);
        }
        if (adonde_vas=="detallePedidoEspera"){
            adonde_vas="pedidosEspera";
            Log.e("adonde",adonde_vas);
            caja_pedido_cliente_espera.setVisibility(View.GONE);
            caja_lista_pedidos_recycler.setVisibility(View.VISIBLE);
            caja_lista_espera_recycler.setVisibility(View.VISIBLE);
        }
     /*   if (adonde_vas=="pedidosEspera"){
            caja_lista_pedidos_recycler.setVisibility(View.VISIBLE);
            caja_recycler_pedidos.setVisibility(View.VISIBLE);
            caja_lista_espera_recycler.setVisibility(View.GONE);
        }*/

    }
}
