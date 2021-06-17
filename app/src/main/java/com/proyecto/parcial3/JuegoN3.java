package com.proyecto.parcial3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import kotlin.jvm.internal.Intrinsics;

public class JuegoN3 extends AppCompatActivity {

    GameView gameView;
    FirebaseFirestore bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_n3);

        Bundle bundle = this.getIntent().getExtras();
        String nombre = bundle.getString("usuario");
        String puntaje = bundle.getString("puntaje");
        Log.d("Puntaje", puntaje);
        String contraseña = bundle.getString("contraseña");


        bd = FirebaseFirestore.getInstance();

        gameView = new GameView(this);
        setContentView(gameView);


    }
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    private void sumapuntaje(){

        Bundle bundle = this.getIntent().getExtras();
        String nombre = bundle.getString("usuario");

        bd.collection("usuarios").document(nombre).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String puntaje =documentSnapshot.getString("Puntaje");

                int pint = Integer.parseInt((puntaje));

                if(pint == 18){
                    pint = pint +1;
                    guardarpuntaje(String.valueOf(pint));
                    puntajebasededatos();
                }else if(pint == 19){
                    pint = pint +3;
                    guardarpuntaje(String.valueOf(pint));
                    puntajebasededatos();
                }else if(pint == 22){
                    pint = pint + 5;
                    guardarpuntaje(String.valueOf(pint));
                    puntajebasededatos();
                }

            }
        });

    }

    private void guardarpuntaje(String puntaje){

        Bundle bundle = this.getIntent().getExtras();
        String nombre = bundle.getString("usuario");
        String contraseña = bundle.getString("contraseña");

        Map<String, String> map = new HashMap<>();

        Log.d("Puntaje", puntaje);

        map.put("Nombre",  nombre);
        map.put("Puntaje", puntaje);
        map.put("Contraseña", contraseña);

        bd.collection("usuarios").document(nombre)
                .set(map).addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void aVoid) {

                Log.d("Añadido", "si se añadio");

            }
        });
    }

    private void puntajebasededatos() {

        Bundle bundle = this.getIntent().getExtras();
        String nombre = bundle.getString("usuario");

        bd.collection("usuarios").document(nombre)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String puntaje = documentSnapshot.getString("Puntaje");

                int pint = Integer.parseInt((puntaje));
                Log.d("Puntajeenpregunta", String.valueOf(pint));
                preguntas(pint);
            }
        });

    }

    public void preguntas(int pint){

        if (pint==18){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Pregunta");
            builder.setMessage("¿Cuántos géneros literario hay?");
            builder.setPositiveButton("5", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Felicitaciones, haz ganado un punto", Toast.LENGTH_SHORT);
                    toast1.show();
                    sumapuntaje();
                }
            });
            builder.setNegativeButton("2", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Haz fallado, pierdes tus puntos", Toast.LENGTH_SHORT);
                    toast1.show();
                    volver();

                }
            });

            AlertDialog var10000 = builder.create();
            Intrinsics.checkExpressionValueIsNotNull(var10000, "builder.create()");
            AlertDialog dialog = var10000;
            dialog.show();

        }else if (pint == 19){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Pregunta");
            builder.setMessage("¿Cual de estos es un tipo de arte?");
            builder.setPositiveButton("Arte auditivo", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Felicitaciones, ganas 3 puntos", Toast.LENGTH_SHORT);
                    toast1.show();
                    sumapuntaje();
                }
            });
            builder.setNegativeButton("Arte musical", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Haz fallado, pierdes tus puntos", Toast.LENGTH_SHORT);
                    toast1.show();
                    volver();
                }
            });

            AlertDialog var10000 = builder.create();
            Intrinsics.checkExpressionValueIsNotNull(var10000, "builder.create()");
            AlertDialog dialog = var10000;
            dialog.show();

        }else if(pint == 22){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Pregunta");
            builder.setMessage("¿Cual de estos es un ejemplo de liminalidad?");
            builder.setPositiveButton("Escultura y Pintura", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Felicitaciones, haz ganado cinco puntos", Toast.LENGTH_SHORT);
                    toast1.show();
                    sumapuntaje();
                }
            });
            builder.setNegativeButton("Danzas y Teatro", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "Haz fallado, no haz ganado puntos", Toast.LENGTH_SHORT);
                    toast1.show();
                    volverainicio();

                }
            });

            AlertDialog var10000 = builder.create();
            Intrinsics.checkExpressionValueIsNotNull(var10000, "builder.create()");
            AlertDialog dialog = var10000;
            dialog.show();

        }else if(pint == 27){
            Felicitaciones();
        }

    }

    private void basededatos() {

        Bundle bundle = this.getIntent().getExtras();
        String nombre = bundle.getString("usuario");

        bd.collection("usuarios").document(nombre)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String nom = documentSnapshot.getString("Nombre");
                String puntaje = documentSnapshot.getString("Puntaje");
                String contraseña = documentSnapshot.getString("Contraseña");

                Puntajes(nom,puntaje,contraseña);
            }
        });
    }

    private  void Felicitaciones(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pregunta");
        builder.setMessage("Felicitaciones haz terminado el juego");
        builder.setPositiveButton("Aceptar \n", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                basededatos();
            }
        });
        AlertDialog var10000 = builder.create();
        Intrinsics.checkExpressionValueIsNotNull(var10000, "builder.create()");
        AlertDialog dialog = var10000;
        dialog.show();

    }

    private void Puntajes(String nombre, String puntaje, String contraseña) {

        Intent intent = new Intent(this, Puntaje_jugadores.class);
        intent.putExtra("usuario", nombre);
        intent.putExtra("puntaje", puntaje);
        intent.putExtra("contraseña", contraseña);
        startActivity(intent);

    }

    private void volverainicio(){

        int pint;
        pint = 9;
        guardarpuntaje(String.valueOf(pint));
        Intent intent = new Intent(this, Juego.class);
        startActivity(intent);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        Bundle bundle = this.getIntent().getExtras();
        String nombre = bundle.getString("usuario");

        bd.collection("usuarios").document(nombre)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String puntaje = documentSnapshot.getString("Puntaje");

                int id = item.getItemId();
                if (id == R.id.perfil_menu) {
                    Act_Puntaje(puntaje);
                }

                if(id == R.id.salir_menu){
                    salir();
                }

                if(id == R.id.volver_menu){
                    volverainicio();
                }
            }
        });

        return super.onOptionsItemSelected(item);
    }

    public  void  volver (){
        int pint;
        pint =18;
        guardarpuntaje(String.valueOf(pint));
    }

    private void salir() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void Act_Puntaje(String puntaje) {

        Bundle bundle = this.getIntent().getExtras();
        String nombre = bundle.getString("usuario");

        Intent intent = new Intent(this, Puntaje_jugadores.class);
        intent.putExtra(nombre, "usuario");
        intent.putExtra(puntaje, "puntaje");
        startActivity(intent);
    }

    class GameView extends SurfaceView implements Runnable {

        Thread gt;
        SurfaceHolder sh;
        boolean playing; // jugando si/no
        Canvas canvas;
        Bitmap bitmapRunning;
        Bitmap bitmapFondo;
        boolean isMoving; // en movimiento si/no
        float runSpeedPerSecond = 700; // velocidad en la pantalla
        float manXpos =0, manYpos =1670;
        int frameWidth = 300, frameHeight = 10000; // tamaño del muñeco
        int frameCount = 6; // cantidad de frames
        int currentFrame = 0; // frame inicial
        long fps;
        long timeThisFrame; // tiempo de cada frame
        long lastFrameChangeTime = 0; // tiempo de cambio de frame
        int frameLengthInMillisecond = 80; // tiempo entre un frame y otro
        Rect screen;
        int ancho, alto;

        private Rect frametoDraw = new Rect(0, 0, frameWidth, frameHeight); // dibujar frame con el tamaño especifico

        private RectF wheretoDraw = new RectF (manXpos, manYpos, manXpos + frameWidth, frameHeight); // colocar frame en posición de pantalla

        public GameView(Context context) {
            super(context);
            sh = getHolder();
            bitmapRunning = BitmapFactory.decodeResource(getResources(), R.drawable.corriendo); // Reconocer el script
            bitmapFondo = BitmapFactory.decodeResource(getResources(), R.drawable.fondo); // Reconocer el fondo
            //escala del dibujo
            bitmapRunning = Bitmap.createScaledBitmap(bitmapRunning, frameWidth * frameCount, frameHeight, false);
            ancho = 1200;
            alto =  2650;
            screen = new Rect(0, 0, ancho, alto);


        }

        @Override
        public void run() {
            while (playing) {
                long startFrameTime = System.currentTimeMillis(); // tiempo de inicio = tiempo actual en milisegundos
                update();
                draw(); // dibujar el personaje

                timeThisFrame = System.currentTimeMillis() - startFrameTime; // tiempo frame = tiempo actual del sistema - tiempo de inicio

                if (timeThisFrame >= 1) { // despues de 1 milisegundo
                    fps = 1000 / timeThisFrame; // frames por segundo
                }
            }
        }

        //Actualizar personaje en cada fila

        public void update() {

            if (isMoving) {
                manXpos = (manXpos + runSpeedPerSecond / fps);

                if (manXpos > getWidth()) {
                    // manYpos += frameHeight;
                    manXpos = 0;
                }

                if (manYpos + frameHeight > getHeight()) {
                    manYpos = 1670;
                }
            }

        }

        public void manageCurrentFrame() {

            // que pasa actualmente

            long time = System.currentTimeMillis();

            if (isMoving) {
                if (time > lastFrameChangeTime + frameLengthInMillisecond) {
                    // avanzar en el script
                    lastFrameChangeTime = time;
                    currentFrame++;

                    //cuando termine el frame, vuelva a empezar el script
                    if (currentFrame >= frameCount) {
                        currentFrame = 0;
                    }

                }

            }
            frametoDraw.left = currentFrame * frameWidth; // pasar por el script
            frametoDraw.right = frametoDraw.left + frameWidth; // colocar el pantalla
        }

        // dibujar el personaje en cada frame
        public void draw() {

            if (sh.getSurface().isValid()) {
                canvas = sh.lockCanvas();
                canvas.drawColor(Color.BLACK);
                wheretoDraw.set((int) manXpos, (int) manYpos, (int) manXpos + frameWidth, (int) manYpos + frameWidth);

                manageCurrentFrame();
                canvas.drawBitmap(bitmapFondo, null, screen, null);
                canvas.drawBitmap(bitmapRunning, frametoDraw, wheretoDraw, null); // dibuja el personaje, posición del personaje, posoción de la pantalla
                sh.unlockCanvasAndPost(canvas);
            }
        }

        public void pause() {
            playing = false;
            try {
                gt.join();
            } catch (InterruptedException e) {
                Log.e("ERR", "Fallo");
            }
        }

        public void resume() {
            playing = true;
            gt = new Thread(this);
            gt.start();
        }


        public boolean onTouchEvent(MotionEvent event) {

            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    isMoving = !isMoving;
                    puntajebasededatos();
                    break;
            }
            return true;
        }

    }


}