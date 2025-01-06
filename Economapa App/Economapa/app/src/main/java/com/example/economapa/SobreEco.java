package com.example.economapa;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SobreEco extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre_eco);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Habilitar la flecha hacia atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(" ");

        // Manejar el clic en la flecha hacia atrás
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        TextView txtSobreEconomapa = findViewById(R.id.txtSobreEconomapa);
        String sobreEconomapaTexto = "Economapa es una innovadora aplicación móvil diseñada para facilitar a los usuarios el acceso a productos en promoción.\nNuestra aplicación ofrece una experiencia integrada que combina la visualización de productos con un mapa interactivo, proporcionando una solución completa y fácil de usar\n\n";
        txtSobreEconomapa.setText(sobreEconomapaTexto);



    }
}