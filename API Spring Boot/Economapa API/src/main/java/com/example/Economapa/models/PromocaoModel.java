package com.example.Economapa.models;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Promocao")
public class PromocaoModel implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long idPromocao;
	private String dataVenc;
	
	
	
	public long getIdPromocao() {
		return idPromocao;
	}
	public void setIdPromocao(long idPromocao) {
		this.idPromocao = idPromocao;
	}
	public String getDataVenc() {
		return dataVenc;
	}
	public void setDataVenc(String dataVenc) {
		this.dataVenc = dataVenc;
	}
	
	
	
	

}
