package com.example.appcitas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText nom,ape,nrodoc,tel,pass;
    Spinner sp1;
    int tipo;
    private SharedPreferences shared;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sp1 = findViewById(R.id.tipo_user_reg);
        nom = findViewById(R.id.editNames);
        ape = findViewById(R.id.editLastnames);
        nrodoc = findViewById(R.id.editNroDoc);
        pass = findViewById(R.id.editPassRegister);
        tel = findViewById(R.id.editNumber);
        //crear un adaptador con el xml
        ArrayAdapter ad = ArrayAdapter.createFromResource(this,R.array.tipo_user_reg, android.R.layout.simple_list_item_1);
        //asociar con el spinner
        sp1.setAdapter(ad);
        //asociar evento select listener
        sp1.setOnItemSelectedListener(this);
        shared = getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = shared.edit();
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

    public void formLogin (View V){
        Intent ite = new Intent(this,MainActivity.class);
        startActivity(ite);
    }
    public void Registrar(View V){
        //Toast.makeText(getApplication(), "Registro con éxito", Toast.LENGTH_SHORT).show();
        String url = "https://citas.dmqvirucida.com.pe/api/register";
        StringRequest postRequestReg = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.w("dato",response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    int estado = obj.getInt("estado");

                    if (estado == 400){
                        Toast.makeText(getApplication(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        return;
                    }else if(estado == 200){
                        String data = obj.getString("dato");
                        JSONObject user = new JSONObject(data);
                        //Log.w("message", obj.getString("dato"));
                        editor.putInt("id",user.getInt("id"));
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
                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("names", nom.getText().toString());
                params.put("lastnames", ape.getText().toString());
                params.put("dni", ""+nrodoc.getText().toString());
                params.put("pass", pass.getText().toString());
                params.put("tipo", ""+tipo);
                params.put("tel", ""+tel.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequestReg);
    }
}