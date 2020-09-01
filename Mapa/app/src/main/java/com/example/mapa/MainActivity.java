package com.example.mapa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mapa.geolocalizacion.MapsActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        }

        Button direccion = (Button) findViewById(R.id.direccion);
        direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView longitudVista = (TextView)findViewById(R.id.longitud);
                TextView latitudVista = (TextView)findViewById(R.id.latitud);
                Double longitud = -76.5324943;
                Double latitud = 3.4517923;
                String textLo = " " + longitud;
                String textLa = " " + latitud;
                longitudVista.setText(textLo);
                latitudVista.setText(textLa);
                setLocation(latitud, longitud);
            }
        });

        Button btnRuta = (Button) findViewById(R.id.ruta);
        btnRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView longitudVista = (TextView)findViewById(R.id.longitud);
                TextView latitudVista = (TextView)findViewById(R.id.latitud);

                String longitudFinal = (String) longitudVista.getText();
                String latitudFinal = (String) latitudVista.getText();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q="+latitudFinal+","+longitudFinal+ "&mode=l"));
                startActivity(intent);
            }
        });
    }
    private void locationStart() {
        TextView mensaje2 = (TextView) findViewById(R.id.mensaje_id2);

        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
        mensaje2.setText("");
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    public void setLocation(Double latitud, Double longitud) {
        TextView mensaje2 = (TextView) findViewById(R.id.mensaje_id2);
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (latitud != 0.0 && longitud != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        latitud, longitud, 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    mensaje2.setText(DirCalle.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {
        MainActivity mainActivity;

        public void setMainActivity(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {


           // this.mainActivity.setLocation(latitud, longitud );
        }



    }



}