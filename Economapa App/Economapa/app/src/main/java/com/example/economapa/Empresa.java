package com.example.economapa;

public class Empresa {
    private int idEmpresa;
    private String nomeEmpresa;
    private String endereco;
    private String email;
    private String longitude;
    private String latitude;
    private String rutCnpj;

    public Empresa(){

    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
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
