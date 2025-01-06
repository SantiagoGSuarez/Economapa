package com.example.Economapa.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.Economapa.dtos.EmpresaRecordDto;
import com.example.Economapa.dtos.ProdutoRecordDto;
import com.example.Economapa.models.EmpresaModel;
import com.example.Economapa.models.ProdutoModel;
import com.example.Economapa.respositories.EmpresaRepository;
import com.example.Economapa.respositories.ProdutoRepository;

import jakarta.validation.Valid;

@RestController
public class ProdutoController {
	@Autowired
	ProdutoRepository produtoRepository;
	
	@PostMapping("/produtos")
	public ResponseEntity<ProdutoModel> saveProduto(@RequestBody @Valid ProdutoRecordDto produtoRecordDto){
		var produtoModel = new ProdutoModel();
		BeanUtils.copyProperties(produtoRecordDto, produtoModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produtoModel));
	}
	
	@GetMapping("/produtos")
	public ResponseEntity<List<ProdutoModel>> getAllProdutos(){
		return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findAll());
		
	}
	
	
	@GetMapping("/produtos/{id}")
	public ResponseEntity<Object> getOneProduto(@PathVariable(value="id") int id){
		Optional<ProdutoModel> produto = produtoRepository.findById(id); 
		if(produto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto Não encontrado");
		}
		return ResponseEntity.status(HttpStatus.OK).body(produto.get());	

	}
	
	
	@PutMapping("/produtos/{id}")
	public ResponseEntity<Object> updateProduto(@PathVariable(value="id") int id, @RequestBody @Valid ProdutoRecordDto produtoRecordDto) {
		Optional<ProdutoModel> produto = produtoRepository.findById(id);
		if(produto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto Não encontrado");
		}
		var produtoModel = produto.get();
		BeanUtils.copyProperties(produtoRecordDto, produtoModel);
		return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produtoModel));
	}
	
	
	
	@DeleteMapping("/produtos/{id}")
	public ResponseEntity<Object> deleteProduto(@PathVariable(value="id") int id) {
		Optional<ProdutoModel> produto = produtoRepository.findById(id);
		if(produto.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto Não encontrado");
		}
		produtoRepository.delete(produto.get());
		return ResponseEntity.status(HttpStatus.OK).body("Produto excluído com sucesso");
	}
	
	
	
	@GetMapping("/buscarPorNombre")
	public ResponseEntity<List<ProdutoModel>> buscarPorNombre(@RequestParam String nomeProduto) {
	    List<ProdutoModel> productos = produtoRepository.findByNomeProdutoContainingIgnoreCase(nomeProduto);
	    if (productos.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok(productos);
	}
	
	
	
	
	
}
