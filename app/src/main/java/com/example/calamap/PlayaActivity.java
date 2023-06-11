package com.example.calamap;

import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.ImageView;

public class PlayaActivity extends AppCompatActivity {

    //Declaración de variables
    private TextView txtPlayaName;
    private TextView txtPlayaDescription;

    private ImageView imgPlaya;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playa);

        // Obtener referencias de los elementos de la interfaz de usuario
        txtPlayaName = findViewById(R.id.txtPlayaNameTextView);
        txtPlayaDescription = findViewById(R.id.txtPlayaDescription);
        imgPlaya = findViewById(R.id.imgPlaya);

        // Obtener los datos de la playa seleccionada del Intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String beachNamed = extras.getString("beachNamed");
            double beachLatitude = extras.getDouble("beachLatitude");
            double beachLongitude = extras.getDouble("beachLongitude");
            String beachInfo = extras.getString("beachInfo");
            String imageUrl = extras.getString("image");
            // Mostrar la información de la playa en los TextViews
            txtPlayaName.setText(beachNamed);
            txtPlayaDescription.setText(beachInfo);
            Picasso.get().load(imageUrl).into(imgPlaya);
        }
    }
}