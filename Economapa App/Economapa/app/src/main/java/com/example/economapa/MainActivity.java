package com.example.economapa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListaFragment f1;
    private MapaFragment f2;
    private BottomNavigationView bnv;
    private static final int REQUEST_LOCATION_PERMISSION = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = new Intent(this, NotificationService.class);
        startService(intent);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" ");

        bnv = findViewById(R.id.nav);

        f1 = new ListaFragment();
        f2 = new MapaFragment();

        // Cargar inicialmente el fragmento ListaFragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container, f1).commit();



        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.lista) {
                    // Reemplazar el fragmento con ListaFragment y cargar productos al seleccionar "Lista"
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, f1).commit();
                    f1.cargarProdutos(); // Llamar de nuevo a cargarProdutos() si se desea recargar los productos al cambiar de pestaña
                    return true;
                } else if (item.getItemId() == R.id.mapa) {
                    // Reemplazar el fragmento con MapaFragment al seleccionar "Mapa"
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, f2).commit();
                    return true;
                }
                return false;
            }
        });
    }

}


