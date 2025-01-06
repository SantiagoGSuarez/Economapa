package com.example.economapa;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Soporte extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soporte);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Habilitar la flecha hacia atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(" ");

        // Manejar el clic en la flecha hacia atrás
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        TextView txtAyudaYSoporte = findViewById(R.id.txtAyudaySoporte);
        String ayudaYSoporteTexto = "Tutoriales\n\n" +
                "1. Tutorial: Cómo usar el mapa interactivo\n" +
                "\u2022 Abre la aplicación y permite el acceso a tu ubicación.\n\n" +
                "\u2022 Navega por el mapa para ver las empresas cercanas que tienen productos en promoción.\n\n" +
                "\u2022 Haz clic en los marcadores del mapa para ver más detalles sobre las Empresas.\n\n" +
                "2. Tutorial: Cómo buscar productos específicos\n" +
                "\u2022 Utiliza la barra de búsqueda en la pantalla principal.\n\n" +
                "\u2022 Introduce el nombre del producto que te interesa.\n\n" +
                "\u2022 Revisa los resultados y haz clic en el producto para ver más detalles.\n\n" +
                "Contacto con Soporte\n" +
                "Si necesitas más ayuda o tienes algún problema con la aplicación, no dudes en contactarnos. Nuestro equipo de soporte está disponible para asistirte.\n\n" +
                "Email: soporte@economapa.com\n\n" +
                "Teléfono: +123 456 7890\n\n" +
                "Horario de Atención: Lunes a Viernes, de 9:00 AM a 6:00 PM\n\n" +
                "Estamos aquí para ayudarte a disfrutar al máximo de Economapa. Gracias por usar nuestra aplicación y esperamos que encuentres todas las promociones que buscas.";
        txtAyudaYSoporte.setText(ayudaYSoporteTexto);


    }
}