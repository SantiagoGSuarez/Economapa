package com.example.Economapa.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.Economapa.dtos.ProdutoRecordDto;
import com.example.Economapa.dtos.PromocaoRecordDto;
import com.example.Economapa.models.ProdutoModel;
import com.example.Economapa.models.PromocaoModel;
import com.example.Economapa.respositories.ProdutoRepository;
import com.example.Economapa.respositories.PromocaoRepository;

import jakarta.validation.Valid;

@RestController
public class PromocaoController {
	@Autowired
	PromocaoRepository promocaoRepository;
	
	@PostMapping("/promocoes")
	public ResponseEntity<PromocaoModel> savePromocao(@RequestBody @Valid PromocaoRecordDto promocaoRecordDto){
		var promocaoModel = new PromocaoModel();
		BeanUtils.copyProperties(promocaoRecordDto, promocaoModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(promocaoRepository.save(promocaoModel));
	}
	
	@GetMapping("/promocoes")
	public ResponseEntity<List<PromocaoModel>> getAllPromocoes(){
		return ResponseEntity.status(HttpStatus.OK).body(promocaoRepository.findAll());
		
	}
	
	
	@GetMapping("/promocoes/{id}")
	public ResponseEntity<Object> getOnePromocao(@PathVariable(value="id") int id){
		Optional<PromocaoModel> promocao = promocaoRepository.findById(id); 
		if(promocao.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Promocao Não encontrado");
		}
		return ResponseEntity.status(HttpStatus.OK).body(promocao.get());	

	}
	
	
	@PutMapping("/promocoes/{id}")
	public ResponseEntity<Object> updatePromocao(@PathVariable(value="id") int id, @RequestBody @Valid PromocaoRecordDto promocaoRecordDto) {
		Optional<PromocaoModel> promocao = promocaoRepository.findById(id);
		if(promocao.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Promocao Não encontrado");
		}
		var promocaoModel = promocao.get();
		BeanUtils.copyProperties(promocaoRecordDto, promocaoModel);
		return ResponseEntity.status(HttpStatus.OK).body(promocaoRepository.save(promocaoModel));
	}
	
	
	
	@DeleteMapping("/promocoes/{id}")
	public ResponseEntity<Object> deletePromocao(@PathVariable(value="id") int id) {
		Optional<PromocaoModel> promocao = promocaoRepository.findById(id);
		if(promocao.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Promocao Não encontrado");
		}
		promocaoRepository.delete(promocao.get());
		return ResponseEntity.status(HttpStatus.OK).body("Promocao excluído com sucesso");
	}
}
