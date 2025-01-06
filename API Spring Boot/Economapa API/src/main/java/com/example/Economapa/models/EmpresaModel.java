package com.example.Economapa.models;

import java.io.Serializable;
import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Empresa")
public class EmpresaModel implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long idEmpresa;
	private String nomeEmpresa;
	private String endereco;
	private String email;
	private String longitude;
	private String latitude;
	private String rutCnpj;
	
	@OneToMany(mappedBy = "empresa")
	private List<ProdutoModel> produtos;
	
	public long getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public String getNomeEmpresa() {
		return nomeEmpresa;
	}
	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getRutCnpj() {
		return rutCnpj;
	}
	public void setRutCnpj(String rutCnpj) {
		this.rutCnpj = rutCnpj;
	}
	
	
	
}
