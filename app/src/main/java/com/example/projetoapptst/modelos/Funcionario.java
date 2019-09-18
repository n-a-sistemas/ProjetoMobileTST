package com.example.projetoapptst.modelos;

public class Funcionario {

    private String uuid;
    private String nome;
    private  String profissao;

    public Funcionario() {
    }

    public Funcionario(String uuid, String nome, String profissao) {
        this.uuid = uuid;
        this.nome = nome;
        this.profissao = profissao;
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

    @Override
    public String toString() {
        return "Funcionario{" +
                "uuid='" + uuid + '\'' +
                ", nome='" + nome + '\'' +
                ", profissao='" + profissao + '\'' +
                '}';
    }
}
