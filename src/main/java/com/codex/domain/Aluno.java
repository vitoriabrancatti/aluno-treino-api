package com.codex.domain;

import java.util.ArrayList;
import java.util.List;

public class Aluno extends Pessoa {
    private int idade;
    private final List<Treino> treinos = new ArrayList<>();

    public Aluno() {
    }

    public Aluno(Long id, String nome, String cpf, String email, int idade) {
        super(id, nome, cpf, email);
        this.idade = idade;
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
