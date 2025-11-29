package com.academia.domain;

import java.util.ArrayList;
import java.util.List;

public class Aluno {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private int idade;
    private final List<Treino> treinos = new ArrayList<>();

    public Aluno() {
    }

    public Aluno(Long id, String nome, String cpf, String email, int idade) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.idade = idade;
    }

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

    public List<Treino> getTreinos() {
        return treinos;
    }
}
