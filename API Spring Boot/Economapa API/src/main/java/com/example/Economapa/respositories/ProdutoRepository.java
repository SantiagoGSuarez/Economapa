package com.example.Economapa.respositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Economapa.models.ProdutoModel;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Integer>{
	List<ProdutoModel> findByNomeProdutoContainingIgnoreCase(String nomeProduto);
	
	
}
