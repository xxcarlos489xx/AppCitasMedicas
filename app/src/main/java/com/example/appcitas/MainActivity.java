package com.example.appcitas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appcitas.Entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText editUsuario,editPass;
    Button btnIngresar;
    private String dni,pass;
    private int tipo;
    Spinner spinner;
    private SharedPreferences shared;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editUsuario = findViewById(R.id.editDni);
        editPass = findViewById(R.id.editPass);
        btnIngresar = findViewById(R.id.btnIngreso);
        spinner = (Spinner) findViewById(R.id.tipo_user);
        //crear un adaptador con el xml
        ArrayAdapter ad = ArrayAdapter.createFromResource(getApplication(),R.array.tipo_user, android.R.layout.simple_list_item_1);

        spinner.setAdapter(ad);
        spinner.setOnItemSelectedListener(this);

        shared = getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = shared.edit();
        editor.clear().apply();
    }

    public void Ingresar(View V){
        dni = editUsuario.getText().toString();
        pass = editPass.getText().toString();
        RealizarPost();
    }

    public void RealizarPost() {
        String url = "https://citas.dmqvirucida.com.pe/api/login";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //tipo de dato
                        //response.getClass().getSimpleName();
                        try {
                            JSONObject obj = new JSONObject(response);
                            int estado = obj.getInt("estado");
                            //Log.w("message", obj.getString("message"));
                            if (estado == 400){
                                Toast.makeText(getApplication(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                return;
                            }else if(estado == 200){
                                String data = obj.getString("dato");
                                JSONObject user = new JSONObject(data);
                                //Log.w("message", obj.getString("dato"));
                                editor.putInt("idUser",user.getInt("id"));
                                editor.putString("nombres",user.getString("nombres"));
                                editor.putString("apellidos",user.getString("apellidos"));
                                editor.putString("dni",user.getString("dni"));
                                editor.putString("telefono",user.getString("telefono"));
                                //editor.putString("correo",user.getString("correo"));
                                //editor.putString("direccion",user.getString("direccion"));
                                editor.putInt("tipo",user.getInt("usuario_id"));
                                editor.commit();
                                Intent ite = new Intent(getApplication(),MenuActivity.class);
                                startActivity(ite);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        //Log.w("error", String.valueOf(response.statusCode));
                        /*if (response.equals("400")){
                            Log.w("dato",response.toString());
                        }*/
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("dni", ""+dni);
                params.put("pass", pass);
                params.put("tipo", ""+tipo);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);

    }

    public void FormRegistro(View V){
        Intent ite = new Intent(this,RegisterActivity.class);
        startActivity(ite);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (position == 0)
            tipo = 1;
        else if (position==1)
            tipo = 2;
        else if (position == 2)
            tipo = 3;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}