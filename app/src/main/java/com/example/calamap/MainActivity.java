package com.example.calamap;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private List<Marker> beachMarkers;  //Crea playas
    private Button btnNext;             //Boton para entrar a la info de la playa
    private Beach selectedBeach;        //Playa seleccionada

    //private String beachName;
    private List<Beach> beaches;        //Lista de playas


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNext = findViewById(R.id.btnNext);
        btnNext.setVisibility(View.GONE);

        //obtener una instancia del fragmento de mapa (SupportMapFragment), buscarlo en la actividad mediante su ID,
        // y solicitar asincrónicamente una instancia del mapa a través del método getMapAsync()
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        beaches = getBeaches(); // Asigna la lista de playas a la variable beaches

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Valora si se ha seleccionado una playa en el mapa y entonces no es null
                if (selectedBeach != null) {

                    // Verificar el nombre de la playa seleccionada y abrir la actividad correspondiente

                        String beachNamed = selectedBeach.getName();
                        String beachInfo = selectedBeach.getDescription();
                        String image = selectedBeach.getUrl();
                        Intent intent = new Intent(MainActivity.this, PlayaActivity.class);
                        intent.putExtra("beachNamed", beachNamed);  // Envía el nombre de la playa
                        intent.putExtra("beachInfo", beachInfo);  //Envía la descripción de la playa
                        intent.putExtra("image", image);
                        startActivity(intent);

                    // Agrega más condiciones según tus playas y actividades correspondientes
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        LatLng spainLatLng = new LatLng(40.4637, -3.7492);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(spainLatLng, 6f));

        addBeachMarkers();

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                selectedBeach = getSelectedBeach(marker);
                btnNext.setVisibility(View.VISIBLE);
                return false;
            }
        });
    }

    private void addBeachMarkers() {
        List<Beach> beaches = getBeaches();

        beachMarkers = new ArrayList<>();
        for (Beach beach : beaches) {
            LatLng beachLatLng = new LatLng(beach.getLatitude(), beach.getLongitude());
            Marker marker = googleMap.addMarker(new MarkerOptions().position(beachLatLng).title(beach.getName()));
            beachMarkers.add(marker);
        }
    }

    private List<Beach> getBeaches() {
        List<Beach> beaches = new ArrayList<>();
        //Definición de cada playa
        beaches.add(new Beach("Playa Malvarrosa", 39.476882, -0.323163, "https://firebasestorage.googleapis.com/v0/b/playamap-a2dc2.appspot.com/o/Fotos%2FMalvarrosa.jpg?alt=media&token=63ba7802-4905-4b86-a46a-7695720bd67e" ,"La malvarrosa es una de las playas más conocidas en Valencia. És muy frecuentada debido al paseo que tiene lleno de locales, bares, restaurantes, hoteles etc."));
        beaches.add(new Beach("Playa de la Barceloneta", 41.378530, 2.192359, "https://firebasestorage.googleapis.com/v0/b/playamap-a2dc2.appspot.com/o/Fotos%2Fplayabarceloneta.jpg?alt=media&token=9671caa7-af23-4578-ac98-5f4b21b91e12" , "La playa de la Barceloneta tiene una longitud de 422 metros y es una de las playas más antiguas y con más tradición de la ciudad. Es una de las preferidas por los usuarios extranjeros y los grupos de esparcimiento y las escuelas, que van a menudo a hacer actividades."));
        beaches.add(new Beach("Playa de la Patacona", 39.490215, -0.323917, "https://firebasestorage.googleapis.com/v0/b/playamap-a2dc2.appspot.com/o/Fotos%2FPatacona.jpg?alt=media&token=86560d5f-77e8-43ea-b57a-d3fd4abf3494" ,"La playa de la Patacona se encuentra en el extremo meridional de Alboraya, y está directamente unida con la playa de la Malvarrosa de València. Cuenta con una extensión de más de 1 km de longitud y es una playa amplia y abierta, con 110 metros de anchura de media."));
        beaches.add(new Beach("Playa de Pinedo", 39.421557, -0.333505, "https://firebasestorage.googleapis.com/v0/b/playamap-a2dc2.appspot.com/o/Fotos%2Fludovico-ceroseis-28jLnDe1f7I-unsplash.jpg?alt=media&token=fb5322d4-b7e2-4762-89b4-752eac4f3059", "Playa de arena, con vistas al puerto de Valencia, paseo marítimo y restaurantes sencillos de cocina española."));
        beaches.add(new Beach("Playa Perellonet", 39.306029, -0.290845, "https://firebasestorage.googleapis.com/v0/b/playamap-a2dc2.appspot.com/o/Fotos%2Fperellonet.jpg?alt=media&token=f737d436-be3f-4fa5-bddc-01d3edc367ef", "Se encuentra al sur de la gola de La Albufera. Es una playa tranquila y de arena fina delimitada por una pequeña urbanización tras la que se extiende una enorme extensión de cultivos de arroz."));
        beaches.add(new Beach("Cala la Mosca", 37.931873, -0.719713, "https://firebasestorage.googleapis.com/v0/b/playamap-a2dc2.appspot.com/o/Fotos%2FMalvarrosa.jpg?alt=media&token=63ba7802-4905-4b86-a46a-7695720bd67e", "Una cala con interesantes elementos naturales protegidos y de pequeñas dimensiones bordeada por dos áreas de roca que le reportan un aspecto de bahía bien acotada y con una gran profundidad de arena fina."));
        beaches.add(new Beach("Playa Torrevieja", 37.958797, -0.702664, "https://firebasestorage.googleapis.com/v0/b/playamap-a2dc2.appspot.com/o/Fotos%2FplayaTorrevieja.jpg?alt=media&token=05f5d0f8-46c1-4b90-b208-91bbad297823", "Su playa más larga tiene 2,3 kilómetros de arena fina y es conocida como la Playa de la Mata. Está en el entorno del Parque Natural de la Laguna de la Mata y cerca del Parque del Molino del agua, este último formado por las dunas de arena que se encuentran tras la playa."));
        beaches.add(new Beach("Playa de Tabarca", 38.165968, -0.479190, "https://firebasestorage.googleapis.com/v0/b/playamap-a2dc2.appspot.com/o/Fotos%2FTabarca.jpg?alt=media&token=3381cea1-7943-4eff-b460-6488b8ad5d0e", "La playa de Tabarca también conocida como Playa Central es la única playa de grava y arena que hay en la isla, un lugar de unos 200 metros de longitud y 20 metros de anchura donde recalan la mayoría de los turistas que visitan la isla ."));
        beaches.add(new Beach("Calas del Cabo", 38.197660, -0.513757, "https://firebasestorage.googleapis.com/v0/b/playamap-a2dc2.appspot.com/o/Fotos%2FplayadelCabo.jpg?alt=media&token=3154d972-40d1-4e0e-b65b-b5247a1ce6e9", "Este tramo de costa permite disfrutar de una experiencia de mar alejada de los más concurridos arenales. Su singularidad ha convertido este rincón meditarráneo en una zona naturista."));
        beaches.add(new Beach("Banys de la Reina Xàbia", 38.775891, 0.191162, "https://firebasestorage.googleapis.com/v0/b/playamap-a2dc2.appspot.com/o/Fotos%2FbanysDeLaReina.jpg?alt=media&token=2d146dca-b165-4b86-b426-334c345c06e2", "La Comunidad Valenciana esconde auténticos tesoros como la Capilla Sixtina de Valencia o el Parque Natural de La Albufera, entre muchos otros. En este caso, una de las joyas más desconocidas de esta comunidad se encuentra en la provincia de Alicante. Esta cala está hecha de restos de antiguas construcciones Romanas."));
        beaches.add(new Beach("Playa EL Xarco", 38.489622, -0.281969, "https://firebasestorage.googleapis.com/v0/b/playamap-a2dc2.appspot.com/o/Fotos%2FplaElXarco.jpg?alt=media&token=dfd86316-f12d-4d9e-b8f0-da667f126524", "La playa del Xarco es una cala de piedras con una torre de vigilancia del S. XVII muy característica. Es una cala de bastante extensión dividida en dos por un montículo central, pero que permite el acceso entre las dos partes."));
        // Agrega más playas aquí

        return beaches;
    }

    //Funcion que retorna la playa seleccionada
    private Beach getSelectedBeach(Marker marker) {
        LatLng selectedLatLng = marker.getPosition();
        for (Beach beach : beaches) {
            LatLng beachLatLng = new LatLng(beach.getLatitude(), beach.getLongitude());
            if (selectedLatLng.equals(beachLatLng)) {
                return beach;
            }
        }
        return null;
    }

}

