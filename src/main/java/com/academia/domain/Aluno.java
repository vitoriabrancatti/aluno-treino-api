package com.academia.domain;

import java.util.ArrayList;
import java.util.List;

public class Aluno extends Pessoa {
    private String objetivo;
    private final List<Treino> treinos = new ArrayList<>();

    public Aluno() {
    }

    public Aluno(Long id, String nome, String cpf, String email, int idade) {
        super(id, nome, cpf, email, idade, "ALUNO");
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public List<Treino> getTreinos() {
        return treinos;
    }

    public String getTipo() {
        return "ALUNO";
    }

}
