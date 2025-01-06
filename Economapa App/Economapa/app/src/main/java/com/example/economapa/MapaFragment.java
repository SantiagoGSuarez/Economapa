package com.example.economapa;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.AndroidException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.library.BuildConfig;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MapaFragment extends Fragment {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private MapView mapView;
    private Marker userMarker;
    private EmpresaService empresaService;
    private GeoPoint userLocation; // Definir userLocation




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);

        // Initialize the map
        mapView = view.findViewById(R.id.map);
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        // Configure the map
        mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        // Create user marker
        userMarker = new Marker(mapView);
        userMarker.setTitle("Aqui está você");
        userMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

        // Add marker to the map
        mapView.getOverlays().add(userMarker);

        // Inicializar Retrofit
        initializeRetrofit();



        // Request location permission if not granted
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted, proceed to get user location
            getUserLocation();
        }

        return view;
    }


    private void initializeRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        empresaService = retrofit.create(EmpresaService.class);
    }

    private void cargarEmpresas() {
        Call<List<Empresa>> call = empresaService.getEmpresa();
        call.enqueue(new Callback<List<Empresa>>() {
            @Override
            public void onResponse(Call<List<Empresa>> call, Response<List<Empresa>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Empresa> empresas = response.body();
                    if (userLocation != null) {  // Verificar que userLocation no sea null
                        agregarEmpresasAlMapa(empresas, userLocation);
                    } else {
                        Toast.makeText(requireContext(), "Ubicación del usuario no disponible", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Empresa>> call, Throwable t) {
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void agregarEmpresasAlMapa(List<Empresa> empresas, GeoPoint userLocation) {
        for (Empresa empresa : empresas) {
            String latitudeStr = empresa.getLatitude();
            String longitudeStr = empresa.getLongitude();

            if (latitudeStr != null && longitudeStr != null) {
                try {
                    double latitude = Double.parseDouble(latitudeStr);
                    double longitude = Double.parseDouble(longitudeStr);
                    GeoPoint geoPoint = new GeoPoint(latitude, longitude);
                    double distancia = calcularDistancia(userLocation, geoPoint);

                    Marker marker = new Marker(mapView);
                    marker.setPosition(geoPoint);
                    marker.setTitle(empresa.getNomeEmpresa());
                    marker.setSnippet(String.format("Distancia: %.2f km", distancia));
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                    marker.setIcon(ContextCompat.getDrawable(requireContext(),R.drawable.baseline_location));

                    mapView.getOverlays().add(marker);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        mapView.invalidate(); // Refrescar el mapa
    }
    private double calcularDistancia(GeoPoint punto1, GeoPoint punto2) {
        float[] results = new float[1];
        Location.distanceBetween(punto1.getLatitude(), punto1.getLongitude(), punto2.getLatitude(), punto2.getLongitude(), results);
        return results[0] / 1000; // Convertir a kilómetros
    }

    private void getUserLocation() {
        // Check if location permission is granted
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Get user's current location
            LocationManager locationManager = (LocationManager) requireContext().getSystemService(requireContext().LOCATION_SERVICE);
            if (locationManager != null) {
                locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new android.location.LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        // Set user marker position
                        userLocation = new GeoPoint(location.getLatitude(), location.getLongitude());

                        userMarker.setPosition(userLocation);

                        // Center map on user location
                        IMapController mapController = mapView.getController();
                        mapController.setZoom(18.0);
                        mapController.setCenter(userLocation);

                        cargarEmpresas();
                    }

                    @Override
                    public void onProviderEnabled(@NonNull String provider) {}

                    @Override
                    public void onProviderDisabled(@NonNull String provider) {}

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {}
                }, null);
            } else {
                Toast.makeText(requireContext(), "Location manager not available", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permission granted, proceed to get user location
                getUserLocation();
            } else {
                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}