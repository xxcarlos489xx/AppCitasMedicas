package com.example.appcitas;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appcitas.Help.DatePickerFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AgendarFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    EditText edfecha;
    TextView txtconsultorio;
    Spinner spRangoHora,spEspecialidad,spMedico;
    Button btnReserva;

    ArrayList<String> especialidades;
    ArrayList<String> nameMedico;
    ArrayList<String> consultorios;
    ArrayList<Integer> listaID,listaIdConsultorios,listaIdMedicos;
    int  IdEspecialidad,IdRangoHora,IdConsultorio,IdMedico;
    private SharedPreferences user;
    private SharedPreferences.Editor editor;
    JsonObjectRequest jobs;//para crear un objeto de peticion
    JSONArray vector;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = user.edit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_agendar, container, false);
        edfecha = v.findViewById(R.id.editFecha);
        spRangoHora = v.findViewById(R.id.spRangoHora);
        spEspecialidad = v.findViewById(R.id.spEspecialidad);
        spMedico = v.findViewById(R.id.spMedico);
        txtconsultorio = v.findViewById(R.id.txtViewConsultorio);
        btnReserva = v.findViewById(R.id.btnAgendar);
        traerEspecialidad();

        //crear un adaptador con el xml
        ArrayAdapter ad = ArrayAdapter.createFromResource(this.getActivity(),R.array.rango_horas, android.R.layout.simple_list_item_1);
        spRangoHora.setAdapter(ad);

        spEspecialidad.setOnItemSelectedListener(this);
        spRangoHora.setOnItemSelectedListener(this);
        spMedico.setOnItemSelectedListener(this);
        edfecha.setOnClickListener(this);
        btnReserva.setOnClickListener(this);
        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Spinner spinner = (Spinner) adapterView;
        if(spinner.getId() == R.id.spEspecialidad)
        {
            IdEspecialidad = listaID.get(position);
            traerMedicos(IdEspecialidad);
        }
        else if(spinner.getId() == R.id.spMedico)
        {
            txtconsultorio.setText(consultorios.get(position));
            IdConsultorio = listaIdConsultorios.get(position);
            IdMedico = listaIdMedicos.get(position);
        }
        else if(spinner.getId() == R.id.spRangoHora)
        {
            IdRangoHora = (position+1);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void traerEspecialidad(){
        String enlace ="https://citas.dmqvirucida.com.pe/api/especialidades";
        especialidades = new ArrayList<>();
        listaID = new ArrayList<>();
        jobs=new JsonObjectRequest(Request.Method.GET, enlace, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    vector=response.getJSONArray("dato");
                    for(int i=0;i<vector.length();i++){
                        JSONObject fila =(JSONObject) vector.get(i);
                        String nombreEsp=fila.getString("nombre");
                        int idEsp = fila.getInt("id");
                        especialidades.add(nombreEsp);
                        listaID.add(idEsp);
                    }

                    ArrayAdapter adaptaEspe = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, especialidades);
                    spEspecialidad.setAdapter(adaptaEspe);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });//fin del JsonRequest
        RequestQueue cola = Volley.newRequestQueue(getActivity());
        cola.add(jobs);
    }

    public void traerMedicos(int id){
        //Log.w("idmedico", String.valueOf(id));
        String enlace ="https://citas.dmqvirucida.com.pe/api/medicos/"+id;
        nameMedico = new ArrayList<>();
        consultorios = new ArrayList<>();
        listaIdConsultorios = new ArrayList<>();
        listaIdMedicos = new ArrayList<>();
        jobs=new JsonObjectRequest(Request.Method.GET, enlace, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                //Log.w("datos de medico",response.toString());
                try {
                    vector=response.getJSONArray("dato");
                    String namesLast = "";
                    for(int i=0;i<vector.length();i++){
                        JSONObject fila =(JSONObject) vector.get(i);
                        String nombres=fila.getString("nombres");
                        String apellidos=fila.getString("apellidos");
                        String listaConsul = fila.getString("consultorio");
                        int listaIdConsul = fila.getInt("id_consultorio");
                        int listaIdMedi = fila.getInt("id");
                        namesLast = nombres + " "+ apellidos;
                        nameMedico.add(namesLast);
                        consultorios.add(listaConsul);
                        listaIdConsultorios.add(listaIdConsul);
                        listaIdMedicos.add(listaIdMedi);
                    }
                    ArrayAdapter adaptaEspe = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, nameMedico);
                    spMedico.setAdapter(adaptaEspe);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });//fin del JsonRequest
        RequestQueue cola = Volley.newRequestQueue(getActivity());
        cola.add(jobs);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editFecha:
                showDatePickerDialog();
                break;
            case R.id.btnAgendar:
                int paciente = user.getInt("idUser",0);
                int medico = IdMedico;
                int consultorio = IdConsultorio;
                int especialidad = IdEspecialidad;
                String fecha = edfecha.getText().toString();
                int  rangoHora = IdRangoHora;
                String url = "https://citas.dmqvirucida.com.pe/api/reserva";
                StringRequest postRequestReg = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.w("datos de reserva",response.toString());
                        try {
                            JSONObject obj = new JSONObject(response);
                            int estado = obj.getInt("estado");

                            if (estado == 400){
                                Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                return;
                            }else if(estado == 200){
                                Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                /*Fragment nuevoFragmento = new ListarFragment();
                                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                                transaction.replace(getView().getId(), nuevoFragmento);
                                transaction.addToBackStack(null);
                                transaction.commit();*/
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
                        params.put("paciente", ""+paciente);
                        params.put("medico", ""+medico);
                        params.put("consultorio", ""+consultorio);
                        params.put("especialidad", ""+especialidad);
                        params.put("fecha", fecha);
                        params.put("hora", ""+rangoHora);
                        return params;
                    }
                };
                Volley.newRequestQueue(getActivity()).add(postRequestReg);

                break;
        }
    }
    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 porque enero es 0
                final String selectedDate = year + "-" + (month+1) + "-" + day;
                edfecha.setText(selectedDate);
            }
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
}