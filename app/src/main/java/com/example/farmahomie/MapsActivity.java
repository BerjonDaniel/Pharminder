package com.example.farmahomie;

/**
 * Asignatura Aplicaciones Moviles - UC3M
 * Update: 03/03/2022.
 */

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String saludo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // Recuperamos la informacion pasada en el intent
        Bundle bundle = this.getIntent().getExtras();

        // Construimos el saludo a mostrar
        saludo = "Hello " + bundle.getString("NAME");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng uc3m_leganes = new LatLng(40.332010, -3.765949);
        int zoomlevel = 15; // nivel de zoom (1: mundo, 5: continente,  10: ciudad,  15: calle,  20: edificios)

        CameraPosition camPos= new CameraPosition.Builder()
                .target(uc3m_leganes)//Centramos el mapa en UC3M
                .zoom(zoomlevel)//Establecemos el zoom en 15
                .tilt(70)//Bajamos el puntode vista de la cámara 70 grados
                .build();
        CameraUpdate camUpd  = CameraUpdateFactory.newCameraPosition(camPos);
        mMap.moveCamera(camUpd);

        mMap.addMarker(new MarkerOptions()
                .position(uc3m_leganes)
                .title(saludo))
                .showInfoWindow(); // si no se hace ésto sólo muestra mensaje cuando se pulsa marcador
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
