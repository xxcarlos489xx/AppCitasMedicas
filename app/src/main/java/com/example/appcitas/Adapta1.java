package com.example.appcitas;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcitas.Entity.Cita;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapta1 extends RecyclerView.Adapter<Adapta1.MyHolder>{
    List<Cita> listaCita;
    Context context;

    public Adapta1(List<Cita> listaCita, Context context) {
        this.listaCita = listaCita;
        this.context = context;
    }
    @NonNull
    @Override
    public Adapta1.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater infla = LayoutInflater.from(context);
        View vis = infla.inflate(R.layout.vista1,parent,false);

        return new Adapta1.MyHolder(vis);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapta1.MyHolder holder, int position) {
        Cita c = listaCita.get(position);
        holder.especialidad.setText(c.getEspecialidad());
        holder.fecha.setText(c.getFecha());
        holder.hora.setText(c.getHora());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetalleCitaActivity.class);
                intent.putExtra("itemDetail",c);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaCita.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView especialidad,fecha,hora;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            especialidad = itemView.findViewById(R.id.txtEspecialidad);
            fecha =itemView.findViewById(R.id.txtFechaCita);
            hora = itemView.findViewById(R.id.txtHoraCita);
        }
    }

}
