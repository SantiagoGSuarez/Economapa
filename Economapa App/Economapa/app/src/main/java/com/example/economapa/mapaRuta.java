package com.example.economapa;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.MenuItemCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class mapaRuta extends AppCompatActivity implements LocationListener{
    private WebView webViewOSMap;
    private String urlOSMap;
    private String stringDestino;
    private String stringOrigem;
    LocationManager locationManager;
    private String TAG = "MyCurrentLocation.MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_ruta);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Habilitar la flecha hacia atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(" ");

        // Manejar el clic en la flecha hacia atrás
        toolbar.setNavigationOnClickListener(view -> onBackPressed());


        int produtoId = getIntent().getIntExtra("PRODUTO_ID",0);
        obtenerInfoProduto(produtoId);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            setupLocationServices();

        }



        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            Location localAtual = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (localAtual != null){
                stringOrigem = localAtual.getLatitude() +","+localAtual.getLongitude();
            }
            else {
                Toast.makeText(this,"Nao foi possivel obter a localizacao atual",Toast.LENGTH_SHORT).show();
            }
        }
        catch (SecurityException e){
            e.printStackTrace();
        }



    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        stopForegroundService();
        super.onBackPressed();
    }
    private void obtenerInfoProduto(int produtoId) {
        // Crear una instancia de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Crear una instancia del servicio de la API
        ProdutoService produtoService = retrofit.create(ProdutoService.class);

        // Realizar una llamada a la API para obtener la información del producto
        Call<Produto> callProduto = produtoService.getProduto(produtoId);
        callProduto.enqueue(new Callback<Produto>() {
            @Override
            public void onResponse(Call<Produto> call, Response<Produto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Produto produto = response.body();

                    // Obtener la información de la empresa asociada al producto
                    Empresa empresa = produto.getIdEmpresa();

                    if (empresa != null) {
                        // Obtener la latitud y longitud de la empresa
                        String latitudDestino = empresa.getLatitude();
                        String longitudDestino = empresa.getLongitude();
                        stringDestino = latitudDestino+","+longitudDestino;

                        cargarMapaConUbicaciones(latitudDestino, longitudDestino);
                    } else {
                        Toast.makeText(mapaRuta.this, "No se pudo obtener la información de la empresa asociada al producto", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mapaRuta.this, "No se pudo obtener la información del producto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Produto> call, Throwable t) {
                Toast.makeText(mapaRuta.this, "Error al obtener la información del producto: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarMapaConUbicaciones(String latitudDestino, String longitudDestino) {
        webViewOSMap = findViewById(R.id.webViewOSMap);
        WebSettings webSettings = webViewOSMap.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Obtener la ubicación actual del usuario
        //String latitudOrigen = obtenerLatitudActual();
        //String longitudOrigen = obtenerLongitudActual();
        // Construir la URL del mapa con la ubicación del usuario como origen y la latitud y longitud de la empresa como destino
        String urlMapa = "https://www.openstreetmap.org/directions?engine=fossgis_osrm_car&route=" + stringOrigem + ";" + latitudDestino + "," + longitudDestino;

        // Cargar la URL del mapa en el WebView
        webViewOSMap.loadUrl(urlMapa);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupLocationServices();
            } else {
                Toast.makeText(this, "IMPOSSÍVEL DE CONTINUAR. O usuário não forneceu permissão de acesso a localização", Toast.LENGTH_LONG).show();
            }
        }
    }
    @SuppressLint("MissingPermission")
    private void setupLocationServices() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } catch (Exception exception){
            Log.e(TAG,"Ocorreu um erro ao executar a função setupLocationServices. O erro foi: " + exception.getMessage());
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        String posicaoAgora = location.getLatitude()+","+location.getLongitude();
        if (!stringOrigem.equals(posicaoAgora)){
            stringOrigem = location.getLatitude()+","+location.getLongitude();
            urlOSMap = "https://www.openstreetmap.org/directions?engine=fossgis_osrm_car&route=" + stringOrigem +";" + stringDestino;
            webViewOSMap.loadUrl(urlOSMap);
        }
        //location.distanceTo();

    }

    private void stopForegroundService() {
        Intent stopIntent = new Intent(this, ForegroundService.class);
        stopIntent.setAction(ForegroundService.ACTION_STOP_SERVICE);
        startService(stopIntent);
    }








}