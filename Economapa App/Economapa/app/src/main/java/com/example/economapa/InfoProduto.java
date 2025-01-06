package com.example.economapa;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoProduto extends AppCompatActivity {
    private ImageView imagemProduto;
    private TextView nomeProduto;
    private TextView descricaoProduto;
    private TextView precoProduto;
    private TextView precoPromocaoProduto;
    private TextView empresaProduto;
    private Button button1;
    private Produto produto;
    private static final int REQUEST_NOTIFICATION_PERMISSION = 1;



    private ProdutoService produtoService;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_produto);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Habilitar la flecha hacia atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(" ");

        // Manejar el clic en la flecha hacia atrás
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        imagemProduto = findViewById(R.id.imagemProduto);
        nomeProduto = findViewById(R.id.nomeProduto);
        descricaoProduto = findViewById(R.id.descricaoProduto);
        precoProduto = findViewById(R.id.precoProduto);
        precoPromocaoProduto = findViewById(R.id.precoPromocaoProduto);
        empresaProduto = findViewById(R.id.empresaProduto);
        button1 = findViewById(R.id.button1);


        // Inicializar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        produtoService = retrofit.create(ProdutoService.class);

        int produtoId = getIntent().getIntExtra("PRODUTO_ID", -1);
        if (produtoId != -1) {
            getProdutoDetails(produtoId);

        } else {
            Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_SHORT).show();
        }
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (produto != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (ContextCompat.checkSelfPermission(InfoProduto.this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(InfoProduto.this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, REQUEST_NOTIFICATION_PERMISSION);
                        } else {
                            proceedWithServiceAndMap();
                        }
                    } else {
                        proceedWithServiceAndMap();
                    }
                } else {
                    Toast.makeText(InfoProduto.this, "Producto no disponible", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




    private void getProdutoDetails(int produtoId) {
        Call<Produto> call = produtoService.getProduto(produtoId);
        call.enqueue(new Callback<Produto>() {
            @Override
            public void onResponse(Call<Produto> call, Response<Produto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    produto = response.body();
                    updateUI(produto);
                } else {
                    Toast.makeText(InfoProduto.this, "Error al cargar el producto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Produto> call, Throwable t) {
                Toast.makeText(InfoProduto.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(Produto produto) {
        Picasso.get().load(produto.getImagem()).into(imagemProduto);
        nomeProduto.setText(produto.getNomeProduto());
        descricaoProduto.setText(produto.getDescricao());
        empresaProduto.setText("Local: "+produto.getIdEmpresa().getNomeEmpresa());
        precoProduto.setText("Precio: $" + produto.getPreco());
        precoPromocaoProduto.setText("PRECIO EN OFERTA: $" + produto.getPrecoPromocao());


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "Permiso de notificaciones concedido", Toast.LENGTH_SHORT).show();
                proceedWithServiceAndMap();
            } else {
                //Toast.makeText(this, "Permiso de notificaciones denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void proceedWithServiceAndMap() {
        String empresaNome = produto.getIdEmpresa().getNomeEmpresa();
        Intent serviceIntent = new Intent(InfoProduto.this, ForegroundService.class);
        serviceIntent.putExtra(ForegroundService.EXTRA_EMPRESA_NOME, empresaNome);
        startForegroundService(serviceIntent);

        Intent intent = new Intent(InfoProduto.this, mapaRuta.class);
        intent.putExtra("PRODUTO_ID", produto.getIdProduto());
        startActivity(intent);
    }







}




