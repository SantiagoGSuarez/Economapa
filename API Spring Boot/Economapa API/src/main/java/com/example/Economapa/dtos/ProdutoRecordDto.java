package com.example.Economapa.dtos;





import java.util.Date;

import com.example.Economapa.models.EmpresaModel;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoRecordDto(@NotBlank String nomeProduto,@NotBlank String descricao,@NotNull int quantidade,@NotNull float preco,@NotNull float precoPromocao, String imagem, EmpresaModel empresa, Date dataVencimento) {

}
