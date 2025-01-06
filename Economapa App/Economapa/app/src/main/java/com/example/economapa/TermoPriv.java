package com.example.economapa;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class TermoPriv extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termo_priv);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Habilitar la flecha hacia atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(" ");

        // Manejar el clic en la flecha hacia atrás
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        // Obtén referencias a los TextViews en tu layout
        TextView txtTerminosUso = findViewById(R.id.txtTerminosUso);
        TextView txtPoliticaPrivacidad = findViewById(R.id.txtPoliticaPrivacidad);

        // Definir los textos directamente en Java
        String terminosUso = "<b>Términos de Uso de Economapa</b><br/>" +
                "Bienvenido a Economapa, una aplicación diseñada para ayudarte a encontrar ofertas y empresas en la frontera entre Uruguay (Rivera) y Brasil (Santana do Livramento). Antes de utilizar nuestra aplicación, te pedimos que leas y aceptes nuestros términos de uso:<br/><br/>" +
                "<b>Uso Aceptable:</b><br/>" +
                "Economapa está destinada únicamente para uso personal y no comercial. Al utilizar la aplicación, aceptas no utilizarla de manera que pueda dañar, deshabilitar, sobrecargar o afectar de otra manera el funcionamiento de Economapa.<br/><br/>" +
                "<b>Acceso y Modificaciones:</b><br/>" +
                "Nos reservamos el derecho de modificar, suspender o interrumpir cualquier aspecto de Economapa en cualquier momento sin previo aviso. También podemos restringir el acceso a ciertas partes o a toda la aplicación según sea necesario.<br/><br/>" +
                "<b>Propiedad Intelectual:</b><br/>" +
                "Todos los derechos de propiedad intelectual relacionados con Economapa, incluidos derechos de autor y marcas registradas, son propiedad de Economapa o de terceros que nos han autorizado su uso. No tienes derecho a utilizar ninguna de estas marcas registradas sin nuestro consentimiento previo por escrito.<br/><br/>" +
                "<b>Limitación de Responsabilidad:</b><br/>" +
                "Economapa proporciona la información basada en los datos disponibles y actualizados. Sin embargo, no garantizamos la exactitud, la integridad o la disponibilidad continua de la información presentada en la aplicación. Utilizas Economapa bajo tu propio riesgo y eres responsable de cualquier acción que tomes basada en la información proporcionada por la aplicación.<br/><br/>" +
                "<b>Cambios en los Términos:</b><br/>" +
                "Nos reservamos el derecho de modificar estos términos en cualquier momento. Los cambios entrarán en vigencia tan pronto como se publiquen en la aplicación. Es tu responsabilidad revisar estos términos periódicamente para estar al tanto de cualquier actualización.<br /><br />";

        String politicaPrivacidad = "<b>Política de Privacidad de Economapa</b><br/>" +
                "En Economapa, nos comprometemos a proteger tu privacidad. Esta política explica cómo recopilamos, utilizamos y protegemos tu información personal:<br/><br/>" +
                "<b>Información Recopilada:</b><br/>" +
                "Economapa recopila la ubicación actual del usuario utilizando mapas de OpenStreetMap para proporcionar información relevante sobre empresas y productos cercanos. Además, recopilamos datos de empresas registradas, que incluyen nombres de productos, imágenes, precios brutos y precios en oferta.<br/><br/>" +
                "<b>Uso de la Información:</b><br/>" +
                "Utilizamos la información recopilada para personalizar la experiencia del usuario, mejorar nuestros servicios y proporcionar información relevante sobre empresas y productos cercanos a tu ubicación.<br/><br/>" +
                "<b>Compartición de Datos:</b><br/>" +
                "La información de las empresas y productos registrados se muestra a los usuarios para su conveniencia. No compartimos datos personales con las empresas a menos que el usuario decida hacer clic en un producto específico y opte por ser redirigido al sitio web correspondiente.<br/><br/>" +
                "<b>Seguridad de los Datos:</b><br/>" +
                "Implementamos medidas de seguridad adecuadas para proteger la información recopilada contra accesos no autorizados, alteraciones, divulgaciones o destrucciones no autorizadas.<br/><br/>" +
                "<b>Consentimiento del Usuario:</b><br/>" +
                "Al utilizar Economapa, aceptas el procesamiento de tu información según lo descrito en esta política de privacidad. Si no estás de acuerdo con estos términos, por favor, no utilices la aplicación.<br/><br/>" +
                "<b>Contacto:</b><br/>" +
                "Si tienes alguna pregunta sobre esta política de privacidad o deseas ejercer tus derechos de privacidad, contáctanos a través de <a href=\"mailto:economapa@gmail.com\">economapa@gmail.com</a>.<br/><br/>" +
                "<b>Última actualización:</b> 14/06/2024.";

        // Establece el texto formateado en los TextViews
        txtTerminosUso.setText(Html.fromHtml(terminosUso));
        txtPoliticaPrivacidad.setText(Html.fromHtml(politicaPrivacidad));
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
