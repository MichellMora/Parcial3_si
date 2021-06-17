package com.proyecto.parcial3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class Puntaje_Jugador extends AppCompatActivity {

    FirebaseFirestore bd;
    TextView tvJugador, tvPuntaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntaje__jugador);

        Bundle bundle = this.getIntent().getExtras();
        String nombre = bundle.getString("usuario");
        Log.d("Nombre4",nombre);

        bd = FirebaseFirestore.getInstance();

        tvJugador = findViewById(R.id.tvJugador);
        tvPuntaje = findViewById(R.id.tvPuntaje);

        basededatos(nombre,tvJugador,tvPuntaje);

    }

    private void basededatos(String nombre, TextView tvJugador, TextView tvPuntaje) {

        bd.collection("usuarios").document(nombre)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String nom = documentSnapshot.getString("Nombre");
                String puntaje = documentSnapshot.getString("Puntaje");
                String contraseña = documentSnapshot.getString("Contraseña");

                tvJugador.setText(nom);
                tvPuntaje.setText(puntaje);


            }
        });

    }
}