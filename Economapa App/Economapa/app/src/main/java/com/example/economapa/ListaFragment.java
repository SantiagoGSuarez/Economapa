package com.example.economapa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ListaFragment extends Fragment {

    private ListView lview;
    private ProdutoAdapter adapter;
    private List<Produto> produtoList;
    private ProdutoService produtoService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // Inicializar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        produtoService = retrofit.create(ProdutoService.class);
        produtoList = new ArrayList<>();
        adapter = new ProdutoAdapter(getActivity(), produtoList);
    }
    public void cargarProdutos() {
        Call<List<Produto>> call = produtoService.getProduto();

        call.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Produto> produtos = response.body();

                    // Actualizar la lista de productos en el adaptador
                    produtoList.clear();
                    produtoList.addAll(produtos);
                    adapter.notifyDataSetChanged();
                } else {
                    // Log de error en caso de respuesta no exitosa al obtener productos
                    Log.e("ListaFragment", "Error en la respuesta del servidor al obtener productos: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                // Log de error en caso de fallo en la solicitud al obtener productos
                Log.e("ListaFragment", "Error en la solicitud al obtener productos: " + t.getMessage());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista, container, false);


        lview = view.findViewById(R.id.lview);
        lview.setAdapter(adapter);

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cargarProdutos();  // Llamar aquí para cargar productos al crear la vista
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission if needed

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                 // Call your search method here
                Log.d("SearchView", "Texto de búsqueda: " + newText);
                buscarProductoPorNombre(newText);
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_option1) {
            // Handle option 1
            Intent intent = new Intent(getActivity(), Soporte.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_option2) {
            // Handle option 2
            Intent intent = new Intent(getActivity(), SobreEco.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_option3) {
            // Handle option 3
            Intent intent = new Intent(getActivity(), TermoPriv.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void buscarProductoPorNombre(String nombreProducto) {
        // Perform API call to search products by name
        Call<List<Produto>> call = produtoService.buscarProductosPorNome(nombreProducto);

        call.enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Produto> productosFiltrados = response.body();

                    // Update list view with filtered products
                    produtoList.clear();
                    produtoList.addAll(productosFiltrados);
                    adapter.updateProductList(productosFiltrados);

                    Log.d("API Call", "Respuesta exitosa: " + response.body());
                } else {
                    // Handle unsuccessful response
                    Log.e("ListaFragment", "Error al filtrar productos por nombre: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {
                // Handle network or API call failure
                Log.e("ListaFragment", "Error en la solicitud al filtrar productos por nombre: " + t.getMessage());
            }
        });
    }


}

