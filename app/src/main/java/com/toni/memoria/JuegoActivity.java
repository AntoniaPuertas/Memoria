package com.toni.memoria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class JuegoActivity extends AppCompatActivity {

    private static final int NUM_CASILLAS = 16;

    private ImageButton btn00;
    private ImageButton btn01;
    private ImageButton btn02;
    private ImageButton btn03;
    private ImageButton btn04;
    private ImageButton btn05;
    private ImageButton btn06;
    private ImageButton btn07;
    private ImageButton btn08;
    private ImageButton btn09;
    private ImageButton btn10;
    private ImageButton btn11;
    private ImageButton btn12;
    private ImageButton btn13;
    private ImageButton btn14;
    private ImageButton btn15;

    private TextView txtPuntuacion;

    private ImageButton[] tablero;
    private int[] imagenes;
    private int imagenFondo;
    private int puntuacion;
    private int aciertos;

    private ArrayList<Integer> arrayBarajado;
    private ImageButton imagenVista;
    private int seleccion1;
    private int seleccion2;
    private boolean tableroBloqueado;
    private final Handler handler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        btn00 = findViewById(R.id.btn00);
        btn01 = findViewById(R.id.btn01);
        btn02 = findViewById(R.id.btn02);
        btn03 = findViewById(R.id.btn03);
        btn04 = findViewById(R.id.btn04);
        btn05 = findViewById(R.id.btn05);
        btn06 = findViewById(R.id.btn06);
        btn07 = findViewById(R.id.btn07);
        btn08 = findViewById(R.id.btn08);
        btn09 = findViewById(R.id.btn09);
        btn10 = findViewById(R.id.btn10);
        btn11 = findViewById(R.id.btn11);
        btn12 = findViewById(R.id.btn12);
        btn13 = findViewById(R.id.btn13);
        btn14 = findViewById(R.id.btn14);
        btn15 = findViewById(R.id.btn15);

        txtPuntuacion = findViewById(R.id.txtPuntuacion);

        tablero = new ImageButton[]{
                btn00,
                btn01,
                btn02,
                btn03,
                btn04,
                btn05,
                btn06,
                btn07,
                btn08,
                btn09,
                btn10,
                btn11,
                btn12,
                btn13,
                btn14,
                btn15,
        };

        imagenFondo = R.drawable.fondo;

        imagenes = new int[]{
                R.drawable.la0,
                R.drawable.la1,
                R.drawable.la2,
                R.drawable.la3,
                R.drawable.la4,
                R.drawable.la5,
                R.drawable.la6,
                R.drawable.la7
        };

        iniciandoJuego();
    }

    private void iniciandoJuego(){
        puntuacion = 0;
        aciertos = 0;
        txtPuntuacion.setText(getResources().getString(R.string.puntuacion) + Integer.toString(puntuacion));
        tableroBloqueado = false;
        imagenVista = null;
        seleccion1 = -1;
        seleccion2 = -1;
        arrayBarajado = barajarImagenes();
        for (int i = 0 ; i < NUM_CASILLAS ; i++){
            tablero[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
            tablero[i].setImageResource(imagenes[arrayBarajado.get(i)]);
            //tablero[i].setImageResource(imagenFondo);
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0 ; i < NUM_CASILLAS ; i++){
                    tablero[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
                    //tablero[i].setImageResource(imagenes[arrayBarajado.get(i)]);
                    tablero[i].setImageResource(imagenFondo);
                }
            }
        }, 100);

        for(int i = 0 ; i < NUM_CASILLAS ; i++){
            final int j = i;
            tablero[i].setEnabled(true);
            tablero[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(tableroBloqueado == false){
                        comprobar(j, tablero[j]);
                    }
                }
            });
        }
    }

    private void comprobar(int i, ImageButton imageButton){
        if(imagenVista == null){
            imagenVista = imageButton;
            imagenVista.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imagenVista.setImageResource(imagenes[arrayBarajado.get(i)]);
            imagenVista.setEnabled(false);
            seleccion1 = arrayBarajado.get(i);
        }else{
            tableroBloqueado = true;
            imageButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageButton.setImageResource(imagenes[arrayBarajado.get(i)]);
            imageButton.setEnabled(false);
            seleccion2 = arrayBarajado.get(i);
            if(seleccion1 == seleccion2){
                imagenVista = null;
                tableroBloqueado = false;
                aciertos++;
                puntuacion++;
                txtPuntuacion.setText(getResources().getString(R.string.puntuacion) + Integer.toString(puntuacion));
                if(aciertos == imagenes.length){
                    Toast.makeText(this, "Has ganado", Toast.LENGTH_LONG).show();
                }
            }else{
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imagenVista.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imagenVista.setImageResource(imagenFondo);
                        imagenVista.setEnabled(true);
                        imageButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imageButton.setImageResource(imagenFondo);
                        imageButton.setEnabled(true);
                        tableroBloqueado = false;
                        imagenVista = null;
                        puntuacion--;
                        txtPuntuacion.setText(getResources().getString(R.string.puntuacion) + Integer.toString(puntuacion));
                    }
                }, 1000);

            }
        }
    }

    private ArrayList<Integer>  barajarImagenes(){
        ArrayList<Integer> listaBarajada = new ArrayList<Integer>();
        for (int i = 0 ; i < NUM_CASILLAS ; i++){
            listaBarajada.add(i % (NUM_CASILLAS / 2));
        }
        Collections.shuffle(listaBarajada);
        Log.d("lista barajada", Arrays.toString(listaBarajada.toArray()));
        return listaBarajada;
    }

    public void iniciarJuego(View view){
        iniciandoJuego();
    }

    public void salirJuego(View view){
        finish();
    }
}