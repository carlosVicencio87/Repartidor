 package com.creathor.repartidores;


 import androidx.appcompat.app.AppCompatActivity;

 import android.os.Bundle;

 import androidx.appcompat.app.AppCompatActivity;

 import android.content.Intent;
 import android.content.SharedPreferences;
 import android.content.pm.ActivityInfo;
 import android.os.Bundle;
 import android.util.Log;
 import android.view.View;
 import android.view.Window;
 import android.view.WindowManager;
 import android.widget.EditText;
 import android.widget.TextView;
 import android.widget.Toast;

 import com.android.volley.AuthFailureError;
 import com.android.volley.Request;
 import com.android.volley.RequestQueue;
 import com.android.volley.Response;
 import com.android.volley.TimeoutError;
 import com.android.volley.VolleyError;
 import com.android.volley.toolbox.StringRequest;
 import com.android.volley.toolbox.Volley;

 import org.json.JSONArray;
 import org.json.JSONException;
 import org.json.JSONObject;

 import java.io.UnsupportedEncodingException;
 import java.security.MessageDigest;
 import java.security.NoSuchAlgorithmException;
 import java.util.HashMap;
 import java.util.Map;
 import java.util.concurrent.ExecutorService;
 import java.util.concurrent.Executors;


 public class Login extends AppCompatActivity {

  private ExecutorService executorService;
  private EditText usuario,contrasena;
  private TextView ingresar,recuperarContra,mensaje;
  private String valUsuario,valContra,correo_final;
  private static String SERVIDOR_CONTROLADOR;
  private int check=0;
  private SharedPreferences datosUsuario;
  private SharedPreferences.Editor editor;
  private boolean correo_exitoso,contrasena_exitoso;
  // private  JSONArray json_datos_usuario;
  private  JSONArray json_datos_usuario;
  private  String strInicio,strUsuario,strId,stridSesion;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   requestWindowFeature(Window.FEATURE_NO_TITLE);
   getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
   getSupportActionBar().hide();
   setContentView(R.layout.activity_login);

   SERVIDOR_CONTROLADOR = new Servidor().getIplocal();
   datosUsuario = getSharedPreferences("Usuario",this.MODE_PRIVATE);
   editor=datosUsuario.edit();
   executorService= Executors.newSingleThreadExecutor();

   usuario=findViewById(R.id.usuario);
   contrasena =findViewById(R.id.contrasena);
   ingresar= findViewById(R.id.ingresar);
   recuperarContra =findViewById(R.id.recuperarContra);
   mensaje =findViewById(R.id.mensaje);

   checkSesion();

   ingresar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
     valUsuario =usuario.getText().toString();
     valContra=contrasena.getText().toString();
     Log.e("datocorreo", valUsuario);
     Log.e("datocontra",valContra );
     if(!valUsuario.trim().equals("")){
      if(!valContra.trim().equals("")){



       recuperarContra.setVisibility(View.GONE);
       ingresar.setVisibility(View.GONE);
       mensaje.setText("Iniciando sesión ...");
       mensaje.setVisibility(View.VISIBLE);
       executorService.execute(new Runnable() {
        @Override
        public void run() {
         hacerPeticion();

         Log.e("entnedi_señor_calamardo","y esto igual");
        }
       });


      }
      else {
       Toast.makeText(getApplicationContext(), "La contrasena es necesario.", Toast.LENGTH_LONG).show();
      }
     }
     else {
      Toast.makeText(getApplicationContext(), "El correo es necesario.", Toast.LENGTH_LONG).show();
     }

    }

   });
   recuperarContra.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
     //Intent intento2= new Intent( Login.this,Recuperar_Contra.class);
     //startActivity(intento2);
    }
   });


  }


  public void hacerPeticion()
  {
   RequestQueue requestQueue= Volley.newRequestQueue(this);
   StringRequest request = new StringRequest(Request.Method.POST,  SERVIDOR_CONTROLADOR+"iniciarSesion.php",
           new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             Log.e("respuesta:",response);
             if (response.equals("no_existe")) {
              recuperarContra.setVisibility(View.VISIBLE);
              ingresar.setVisibility(View.VISIBLE);
              mensaje.setText("El teléfono o correo es incorrecto.");
             }
             else
             {
              try {

               json_datos_usuario=new JSONArray(response);
               Log.e("lala",""+json_datos_usuario);
               for (int i=0;i<json_datos_usuario.length();i++){
                JSONObject jsonObject = json_datos_usuario.getJSONObject(i);
                //Log.e("nombreMovies", String.valueOf(jsonObject));
                String strId = jsonObject.getString("id");
                String strNombre= jsonObject.getString("nombre");
                String strContrasena= jsonObject.getString("contrasena");
                String strFecha_registro=jsonObject.getString("fecha_registro");
                String strHistorial_venta=jsonObject.getString("historial_venta");
                String strCalificacion=jsonObject.getString("calificacion");
                String strActivo=jsonObject.getString("activo");
                String strReportes= jsonObject.getString("reportes");
                String stRol_usuario=jsonObject.getString("rol_usuario");
                String strFireCode= jsonObject.getString("fireCode");
                String strIdSesion=jsonObject.getString("idSesion");
                Log.e("idsesion",strIdSesion);


                editor.putString("id",strId);
                editor.putString("nombre",strNombre);
                editor.putString("contrasena",strContrasena);
                editor.putString("fecha_registro",strFecha_registro);
                editor.putString("historial_venta",strHistorial_venta);
                editor.putString("calificacion",strCalificacion);
                editor.putString("activo",strActivo);
                editor.putString("reportes",strReportes);
                editor.putString("rol_usuario",stRol_usuario);
                editor.putString("fireCode",strFireCode);
                editor.putString("idSesion",strIdSesion);

                editor.apply();
                Log.e("1",""+strId);
                Log.e("id_sesion",strNombre);
                Log.e("3",strContrasena);
                Log.e("4",strFecha_registro);
                Log.e("5",strHistorial_venta);
                Log.e("6",strCalificacion);
                Log.e("7",strActivo);
                Log.e("8",""+strReportes);
                Log.e("9",stRol_usuario);
                Log.e("10",strFireCode);
                Log.e("11",strIdSesion);


                Intent intent = new Intent(Login.this, Estacion.class);
                startActivity(intent);






               }
              }
              catch (JSONException e) {
               Log.e("error de json", String.valueOf(e));
              }

             }
            }}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError error) {
     if (error == null) {
      if (error.getClass().equals(TimeoutError.class)){
       Toast.makeText(getApplicationContext(),"Time Out Error",Toast.LENGTH_LONG).show();
      }
     }
     Log.e( "aquiMamo", "error: " +error.getMessage());
     Log.d("aquimamo2",""+ error.getStackTrace());
    }
   }){
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
     HashMap<String,String> map = new HashMap<>();
     map.put("nombre", valUsuario);
     map.put("contrasena",valContra);
     return map;
    }
   };
   requestQueue.add(request);
  }
  private void checkSesion() {
   strInicio = datosUsuario.getString("idSesion", "no");

   Log.e("inicio",""+strInicio);
   if (!strInicio.equals("no"))
   {

    Log.e("idSesion",strInicio);
    Intent agenda= new Intent(Login.this, Estacion.class);
    startActivity(agenda);
   }
  }
  public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
   MessageDigest md = MessageDigest.getInstance("SHA-1");
   byte[] textBytes = text.getBytes("iso-8859-1");
   md.update(textBytes, 0, textBytes.length);
   byte[] sha1hash = md.digest();
   return convertToHex(sha1hash);
  }
  private static String convertToHex(byte[] data) {
   StringBuilder buf = new StringBuilder();
   for (byte b : data) {
    int halfbyte = (b >>> 4) & 0x0F;
    int two_halfs = 0;
    do {
     buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
     halfbyte = b & 0x0F;
    } while (two_halfs++ < 1);
   }
   return buf.toString();
  }

 }