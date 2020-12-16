package com.example.appcitas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appcitas.Entity.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText editUsuario,editPass;
    Button btnIngresar;
    List<User> u;
    private SharedPreferences shared;
    private SharedPreferences.Editor editor;
    JsonObjectRequest jobs;//para crear un objeto de peticion
    JSONArray vector;//capturar los datos enviados por el servidor
    String url = "http://10.0.2.2/SerTransito/Controla.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editUsuario = findViewById(R.id.editDni);
        editPass = findViewById(R.id.editPass);
        btnIngresar = findViewById(R.id.btnIngreso);
        shared = getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = shared.edit();
    }

    public void Ingresar(View V){
        String user = editUsuario.getText().toString();
        String pass = editPass.getText().toString();

        RealizarPost();


        /*u = new ArrayList<>();
        String enlace = url+"?tag=consulta3&pla="+placa.getText().toString();
        u = new ArrayList<>();
        jobs=new JsonObjectRequest(Request.Method.GET, enlace, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //captura de la respuesta
                try {
                    vector = response.getJSONArray("dato");
                    //Log.w("dato",vector.toString());
                    if ((vector.toString()).equals("[]")){
                        Toast.makeText(getApplication(),"usuario no registrado",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (int n=0;n<vector.length();n++){
                        User us = new Papeleta();
                        JSONObject fila =(JSONObject) vector.get(n);
                        us.setCod(fila.getString("pape"));
                        us.setFecha(fila.getString("fecha"));
                        us.setDesc(fila.getString("desp"));
                        us.setMonto(fila.getDouble("monto"));
                        u.add(us);
                    }

                }catch (Exception ex){
                    //Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });//fin del JsonRequest
        RequestQueue cola = Volley.newRequestQueue(getActivity());
        cola.add(jobs);*/



        editor.putString("dni",user);
        editor.putString("pass",pass);
        editor.commit();
        //Toast.makeText(getApplication(), user, Toast.LENGTH_SHORT).show();
        //Intent ite = new Intent(this,MenuActivity.class);
        //startActivity(ite);
    }

    public void RealizarPost() {

        String url = "http://127.0.0.1:8000/api/login";
        //String url = "http://10.0.2.2/api/login";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //((TextView)findViewById(R.id.TextResult)).setText(response);
                        Toast.makeText(getApplication(), response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("your-name", "Pepito Grillo");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }

    public void FormRegistro(View V){
        Intent ite = new Intent(this,RegisterActivity.class);
        startActivity(ite);
    }

}