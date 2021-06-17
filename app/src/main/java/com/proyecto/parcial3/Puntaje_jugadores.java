package com.proyecto.parcial3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.LayoutInflaterFactory;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Puntaje_jugadores extends AppCompatActivity {

    private FirebaseFirestore bd;
    RecyclerView rv;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntaje_jugadores);

        bd = FirebaseFirestore.getInstance();

        Bundle bundle = this.getIntent().getExtras();
        String nombre = bundle.getString("usuario");

        rv = findViewById(R.id.rv);

        Query q = bd.collection("usuarios");

        FirestoreRecyclerOptions<Datos> opciones = new  FirestoreRecyclerOptions.Builder<Datos>()
                .setQuery(q, Datos.class)
                .build();

        adapter = new  FirestoreRecyclerAdapter<Datos, DatosViewHolder>(opciones) {
            @NonNull
            @Override
            public DatosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista, parent, false);
                return new DatosViewHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull DatosViewHolder datosViewHolder, int i, @NonNull Datos datos) {
                datosViewHolder.tvNombre.setText(datos.getNombre());
                datosViewHolder.tvPuntaje.setText(datos.getPuntaje());
            }
        };

    rv.setHasFixedSize(true);
    rv.setLayoutManager(new LinearLayoutManager(this));
    rv.setAdapter(adapter);

    }

    private class DatosViewHolder  extends RecyclerView.ViewHolder {

        private TextView tvNombre;
        private TextView tvPuntaje;

        public DatosViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNombre = itemView.findViewById(R.id.tvNom);
            tvPuntaje = itemView.findViewById(R.id.tvPunt);


        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}