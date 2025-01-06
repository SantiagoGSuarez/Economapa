package com.example.Economapa.dtos;

import jakarta.validation.constraints.NotBlank;


public record EmpresaRecordDto(@NotBlank String nomeEmpresa,@NotBlank String endereco,@NotBlank String email ,@NotBlank String longitude,@NotBlank String latitude,@NotBlank String rutCnpj) {

}
