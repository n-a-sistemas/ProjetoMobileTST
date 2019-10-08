package com.example.projetoapptst.modelos;

import java.util.UUID;

public class Epi {

    private String ValidaCA;
    private String Validade;
    private String Nome;
    private String Uid;


    public Epi() {
    }


    public Epi(String uid,String validaCA, String validade, String nome) {
        Uid = uid;
        ValidaCA = validaCA;
        Validade = validade;
        Nome = nome;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getValidaCA() {
        return ValidaCA;
    }

    public void setValidaCA(String validaCA) {
        ValidaCA = validaCA;
    }

    public String getValidade() {
        return Validade;
    }

    public void setValidade(String validade) {
        Validade = validade;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    @Override
    public String toString() {
        return "Epi{" +
                "ValidaCA='" + ValidaCA + '\'' +
                ", Validade='" + Validade + '\'' +
                ", Nome='" + Nome + '\'' +
                ", Uid='" + Uid + '\'' +
                '}';
    }
}
