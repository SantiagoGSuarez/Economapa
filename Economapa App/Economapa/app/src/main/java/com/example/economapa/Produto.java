package com.example.economapa;

import java.time.LocalDate;
import java.util.Date;

public class Produto {
    private int idProduto;
    private String nomeProduto;
    private String descricao;
    private int quantidade;
    private float preco;
    private float precoPromocao;
    private String imagem;
    private Empresa idEmpresa;
    private Date dataVencimento;


    public Produto(){

    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int id) {
        this.idProduto = id;
    }

    public Empresa getIdEmpresa(){
        return idEmpresa;
    }
    public void setIdEmpresa(Empresa idEmpresa){
        this.idEmpresa = idEmpresa;
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

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }


}
