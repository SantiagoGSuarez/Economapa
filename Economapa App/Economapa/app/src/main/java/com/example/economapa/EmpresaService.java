package com.example.economapa;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EmpresaService {
    @GET("/empresas")
    Call<List<Empresa>> getEmpresa();

    @GET("/empresas/{id}")
    Call<List<Empresa>> getEmpresa(@Path("id") int id);
}
