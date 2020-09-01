package com.example.mapa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mapa.geolocalizacion.MapsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mostrarMapa(View view) {
        Intent mapa = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(mapa);
    }
}