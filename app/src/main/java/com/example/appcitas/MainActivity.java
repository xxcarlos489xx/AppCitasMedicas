package com.example.appcitas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editUsuario,editPass;
    Button btnIngresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUsuario = findViewById(R.id.editDni);
        editPass = findViewById(R.id.editPass);
        btnIngresar = findViewById(R.id.btnIngreso);
    }

    public void Ingresar(View V){
        Toast.makeText(getApplication(), "Login con éxito", Toast.LENGTH_SHORT).show();
        //Intent ite = new Intent(this,AgendarFragment.class);
        //startActivity(ite);
    }
    public void FormRegistro(View V){
        Intent ite = new Intent(this,RegisterActivity.class);
        startActivity(ite);
    }
    public void menu(View V){
        Intent ite = new Intent(this,MenuActivity.class);
        startActivity(ite);
    }
}