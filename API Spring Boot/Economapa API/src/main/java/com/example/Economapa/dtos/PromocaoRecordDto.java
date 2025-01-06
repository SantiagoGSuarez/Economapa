package com.example.Economapa.dtos;

import jakarta.validation.constraints.NotBlank;

public record PromocaoRecordDto(@NotBlank String dataVenc) {

}
