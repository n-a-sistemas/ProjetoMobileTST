package com.example.projetoapptst.modelos;

import java.util.UUID;

public class Epi {

    private String validadeCA;
    private String validade;
    private String nome;
    private String uuid;


    public Epi() {
    }

    public Epi(String validadeCA, String validade, String nome, String uuid) {
        this.validadeCA = validadeCA;
        this.validade = validade;
        this.nome = nome;
        this.uuid = uuid;
    }

    public String getValidadeCA() {
        return validadeCA;
    }

    public void setValidadeCA(String validadeCA) {
        this.validadeCA = validadeCA;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "Epi{" +
                "validadeCA='" + validadeCA + '\'' +
                ", validade='" + validade + '\'' +
                ", nome='" + nome + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
