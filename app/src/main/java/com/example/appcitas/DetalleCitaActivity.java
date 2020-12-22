package com.example.appcitas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.appcitas.Entity.Cita;

public class DetalleCitaActivity extends AppCompatActivity {
    private TextView medico,espe,con,estado,fecha,hora;
    private Cita cita;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cita);
        setTitle(getClass().getSimpleName());

        initViews();
        iniValues();
    }

    private void initViews() {
        medico = findViewById(R.id.txtNombreMedico);
        espe = findViewById(R.id.txtEspecialidadMedico);
        con = findViewById(R.id.txtConsultorioMedico);
        estado = findViewById(R.id.txtEstadoCita);
        fecha = findViewById(R.id.txtFechaCita);
        hora = findViewById(R.id.txtHoraCita);
    }

    private void iniValues() {
        cita = (Cita) getIntent().getExtras().getSerializable("itemDetail");
        try {
            medico.setText(cita.getMedico());
            espe.setText(cita.getEspecialidad());
            con.setText(cita.getConsultorio());
            estado.setText(cita.getEstado());
            fecha.setText(cita.getFecha());
            hora.setText(cita.getHora());
        }catch (Exception ex){
            System.out.println(""+ex.getMessage());
        }

    }


}