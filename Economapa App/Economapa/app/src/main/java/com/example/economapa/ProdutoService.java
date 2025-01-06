package com.example.economapa;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProdutoService {

    @GET("/produtos")
    Call<List<Produto>>getProduto();
    @GET("/produtos/{id}")
    Call<Produto> getProduto(@Path("id") int id);
    @GET("/buscarPorNombre")
    Call<List<Produto>> buscarProductosPorNome(@Query("nomeProduto") String nome);
    @DELETE("/produtos/{id}")
    Call<Void> deleteProduto(@Path("id") int id);








}
