package com.academia.dto;

import java.util.List;

public class AlunoResponse {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private int idade;
    private List<TreinoSummaryResponse> treinos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public List<TreinoSummaryResponse> getTreinos() {
        return treinos;
    }

    public void setTreinos(List<TreinoSummaryResponse> treinos) {
        this.treinos = treinos;
    }
}
