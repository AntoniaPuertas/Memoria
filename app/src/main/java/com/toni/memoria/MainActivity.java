package com.toni.memoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onJugar(View view){
        Intent intent = new Intent(this, JuegoActivity.class);
        startActivity(intent);
    }

    public void onSalir(View view){
        finish();
    }
}