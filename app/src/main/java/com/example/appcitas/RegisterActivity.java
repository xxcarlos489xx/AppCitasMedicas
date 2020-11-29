package com.example.appcitas;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText nom,ape,nrodoc,nrcon,pass;
    Spinner sp1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sp1 = findViewById(R.id.spTipoDoc);
        //crear un adaptador con el xml
        ArrayAdapter ad = ArrayAdapter.createFromResource(this,R.array.tipoDoc, android.R.layout.simple_list_item_1);
        sp1.setAdapter(ad);//asociar con el spinner
        sp1.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        double v[] = {1,2,3};
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void formLogin (View V){
        Intent ite = new Intent(this,MainActivity.class);
        startActivity(ite);
    }
    public void Registrar(View V){
        Toast.makeText(getApplication(), "Registro con éxito", Toast.LENGTH_SHORT).show();
    }
}