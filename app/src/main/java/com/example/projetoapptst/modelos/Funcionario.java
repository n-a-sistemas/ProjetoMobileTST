package com.example.projetoapptst.modelos;

import android.provider.ContactsContract;

public class Funcionario {

    private String uuid;
    private String nome;
    private  String profissao;
    private String imgScr;
    private String pontos;
    private String email;
    private boolean valido;

    public String getImgScr() {
        return imgScr;
    }

    public String getPontos() {
        return pontos;
    }

    public void setPontos(String pontos) {
        this.pontos = pontos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        valido = valido;
    }

    public Funcionario() {
    }

    public Funcionario(String uuid, String nome, String profissao, String imgScr) {
        this.uuid = uuid;
        this.nome = nome;
        this.profissao = profissao;
        this.imgScr= imgScr;

    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "uuid='" + uuid + '\'' +
                ", nome='" + nome + '\'' +
                ", profissao='" + profissao + '\'' +
                ", imgScr='" + imgScr + '\'' +
                ", pontos='" + pontos + '\'' +
                ", email='" + email + '\'' +
                ", valido=" + valido +
                '}';
    }


    public void setImgScr(String imgScr) {
        this.imgScr = imgScr;
    }

    public Funcionario(String nome) {
        this.nome = nome;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

}
