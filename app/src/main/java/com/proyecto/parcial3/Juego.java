package com.proyecto.parcial3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.*;

public class Juego extends AppCompatActivity {

    TextView tvnom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        Bundle bundle = this.getIntent().getExtras();
        String nombre = bundle.getString("usuario");

        tvnom = findViewById(R.id.nom_jugador);

        tvnom.setText(nombre);

    }
}