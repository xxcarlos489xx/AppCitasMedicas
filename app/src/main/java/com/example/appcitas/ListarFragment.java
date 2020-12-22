package com.example.appcitas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appcitas.Entity.Cita;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ListarFragment extends Fragment{
    private int id_user;
    RecyclerView rec1;
    List<Cita> lista;
    ProgressBar pg1;
    //PETICION HTTP
    JsonObjectRequest jobs;
    JSONArray vector;
    String url = "https://citas.dmqvirucida.com.pe/api/citas";
    //DATOS DE SESION
    private SharedPreferences user;
    private SharedPreferences.Editor editor;
    String names,lastnames,paciente;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //HEADER DE NAV_MENU
        user = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = user.edit();
        id_user = user.getInt("idUser",0);
        names = user.getString("nombres","");
        lastnames = user.getString("apellidos","");
        paciente = names+" "+lastnames;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_listar, container, false);
        pg1 = v.findViewById(R.id.progressBar1);
        rec1 = v.findViewById(R.id.recy1);
        llenaCitas();
        return v;
    }
    private void llenaCitas() {
        String enlace = url+"/"+id_user;
        lista = new ArrayList<>();
        jobs = new JsonObjectRequest(Request.Method.GET, enlace, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //capturamos la respuesta
                try {
                    vector = response.getJSONArray("dato");
                    pg1.setVisibility(View.INVISIBLE);
                    //Log.w("dato",vector.toString());
                    for (int n=0;n<vector.length();n++){
                        Cita c = new Cita();
                        JSONObject fila =(JSONObject) vector.get(n);
                        c.setId(fila.getInt("id"));
                        c.setPaciente(paciente);
                        c.setMedico(fila.getString("nombreMedico")+ fila.getString("apellidoMedico"));
                        c.setEspecialidad(fila.getString("especialidad"));
                        c.setConsultorio(fila.getString("consultorio"));
                        c.setEstado(fila.getString("estado"));
                        c.setFecha(fila.getString("fecha"));
                        c.setHora(fila.getString("rangoHora"));
                        lista.add(c);
                    }
                    Adapta1 dp = new Adapta1(lista, getActivity());
                    rec1.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rec1.setAdapter(dp);
                }catch(Exception ex){
                    Toast.makeText(getActivity(),ex.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }//fin on response
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        //agregar a la cola de peticiones
        RequestQueue cola = Volley.newRequestQueue(getActivity());
        cola.add(jobs);
    }
}