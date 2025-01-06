package com.example.Economapa.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Produto")
public class ProdutoModel implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long idProduto;
	private String nomeProduto;
	private String descricao;
	private int quantidade;
	private float preco;
	private float precoPromocao;
	private String imagem;
	private Date dataVencimento;
	
	@ManyToOne
	@JoinColumn(name = "idEmpresa")
	private EmpresaModel empresa;
	
	public EmpresaModel getIdEmpresa() {
		return empresa;
	}

	public void setIdEmpresa(EmpresaModel empresa) {
		this.empresa = empresa;
	}
	
	public String getImagem() {
		return imagem;
	}
	public void setImagem(String imagem) {
		this.imagem = imagem;
	}
	public long getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(long idProduto) {
		this.idProduto = idProduto;
	}
	public String getNomeProduto() {
		return nomeProduto;
	}
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public float getPreco() {
		return preco;
	}
	public void setPreco(float preco) {
		this.preco = preco;
	}
	public float getPrecoPromocao() {
		return precoPromocao;
	}
	public void setPrecoPromocao(float precoPromocao) {
		this.precoPromocao = precoPromocao;
	}
	public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
	
	
	
	

}
