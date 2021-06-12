package com.proyecto.parcial3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.*;

public class Juego extends AppCompatActivity {

    TextView tvnom, tvpuntaje;
    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        Bundle bundle = this.getIntent().getExtras();
        String nombre = bundle.getString("usuario");
        String puntaje = bundle.getString("puntaje");

        tvnom = findViewById(R.id.nom_jugador);
        tvpuntaje = findViewById(R.id.puntaje);

        tvnom.setText(nombre);
        tvpuntaje.setText(puntaje);

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

    class GameView extends SurfaceView implements Runnable {

        Thread gt;
        SurfaceHolder sh;
        boolean playing; // jugando si/no
        Canvas canvas;
        Bitmap bitmapRunning;
        boolean isMoving; // en movimiento si/no
        float runSpeedPerSecond = 800; // velocidad en la pantalla
        float manXpos = 0, manYpos = 0;
        int frameWidth = 500, frameHeight = 437; // tamaño del muñeco
        int frameCount = 6; // cantidad de frames
        int currentFrame = 0; // frame inicial
        long fps;
        long timeThisFrame; // tiempo de cada frame
        long lastFrameChangeTime = 0; // tiempo de cambio de frame
        int frameLengthInMillisecond = 80; // tiempo entre un frame y otro


        private Rect frametoDraw = new Rect(0, 0, frameWidth, frameHeight); // dibujar frame con el tamaño especifico

        private RectF wheretoDraw = new RectF(manXpos,manYpos,manXpos + frameWidth, frameHeight); // colocar frame en posición de pantalla

        public GameView(Context context) {
            super(context);
            sh = getHolder();
            bitmapRunning = BitmapFactory.decodeResource(getResources(), R.drawable.correr); // Reconocer el script
            //escala del dibujo
            bitmapRunning = Bitmap.createScaledBitmap(bitmapRunning, frameWidth * frameCount, frameHeight, false);

        }

        @Override
        public void run() {
            while (playing) {
                long startFrameTime = System.currentTimeMillis(); // tiempo de inicio = tiempo actual en milisegundos
                update(); // actualizar posición
                draw(); // dibujar el personaje

                timeThisFrame = System.currentTimeMillis() - startFrameTime; // tiempo frame = tiempo actual del sistema - tiempo de inicio

                if (timeThisFrame >= 1) { // despues de 1 milisegundo
                    fps = 1000 /timeThisFrame; // frames por segundo
                }
            }
        }

        //Actualizar personaje en cada fila
        public void update() {

            if (isMoving) {
                manXpos = (manXpos + runSpeedPerSecond / fps);

                if (manXpos > getWidth()) {
                    manYpos += (int) frameHeight;
                    manXpos = 10;
                }

                if (manYpos + frameHeight > getHeight()) {
                    manYpos = 10;
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
                    if(currentFrame >= frameCount){
                        currentFrame = 0;
                    }

                }

            }
            frametoDraw.left = currentFrame * frameWidth; // pasar por el script
            frametoDraw.right = frametoDraw.left + frameWidth; // colocar el pantalla
        }

        // dibujar el personaje en cada frame
        public void draw(){

            if(sh.getSurface().isValid()){
                canvas = sh.lockCanvas();
                canvas.drawColor(Color.BLACK);
                wheretoDraw.set((int) manXpos, (int) manYpos , (int) manXpos + frameWidth, (int) manYpos +frameWidth);

                manageCurrentFrame();
                canvas.drawBitmap(bitmapRunning, frametoDraw,wheretoDraw,null); // dibuja el personaje, posición del personaje, posoción de la pantalla
                sh.unlockCanvasAndPost(canvas);
            }
        }

        public void pause(){
            playing = false;
            try {
                gt.join();
            }catch (InterruptedException e) {
                Log.e("ERR", "Fallo");
            }
        }

        public void resume (){
            playing  =true;
            gt= new Thread(this);
            gt.start();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            switch (event.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:
                    isMoving = !isMoving;
                    break;
            }
            return true;
        }
    }
}