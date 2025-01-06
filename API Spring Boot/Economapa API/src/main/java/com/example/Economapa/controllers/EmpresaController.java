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

import com.example.Economapa.dtos.EmpresaRecordDto;
import com.example.Economapa.models.EmpresaModel;
import com.example.Economapa.respositories.EmpresaRepository;

import jakarta.validation.Valid;

@RestController
public class EmpresaController {
	
	@Autowired
	EmpresaRepository empresaRepository;
	
	@PostMapping("/empresas")
	public ResponseEntity<EmpresaModel> saveEmpresa(@RequestBody @Valid EmpresaRecordDto empresaRecordDto){
		var empresaModel = new EmpresaModel();
		BeanUtils.copyProperties(empresaRecordDto, empresaModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(empresaRepository.save(empresaModel));
	}
	
	@GetMapping("/empresas")
	public ResponseEntity<List<EmpresaModel>> getAllEmpresas(){
		return ResponseEntity.status(HttpStatus.OK).body(empresaRepository.findAll());
		
	}
	
	
	@GetMapping("/empresas/{id}")
	public ResponseEntity<Object> getOneEmpresa(@PathVariable(value="id") int id){
		Optional<EmpresaModel> empresa = empresaRepository.findById(id); 
		if(empresa.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa Não encontrada");
		}
		return ResponseEntity.status(HttpStatus.OK).body(empresa.get());	

	}
	
	
	@PutMapping("/empresas/{id}")
	public ResponseEntity<Object> updateEmpresa(@PathVariable(value="id") int id, @RequestBody @Valid EmpresaRecordDto empresaRecordDto) {
		Optional<EmpresaModel> empresa = empresaRepository.findById(id);
		if(empresa.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa Não encontrada");
		}
		var empresaModel = empresa.get();
		BeanUtils.copyProperties(empresaRecordDto, empresaModel);
		return ResponseEntity.status(HttpStatus.OK).body(empresaRepository.save(empresaModel));
	}
	
	
	
	@DeleteMapping("/empresas/{id}")
	public ResponseEntity<Object> deleteEmpresa(@PathVariable(value="id") int id) {
		Optional<EmpresaModel> empresa = empresaRepository.findById(id);
		if(empresa.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empresa Não encontrada");
		}
		empresaRepository.delete(empresa.get());
		return ResponseEntity.status(HttpStatus.OK).body("Empresa excluído com sucesso");
	}
	
	

}
