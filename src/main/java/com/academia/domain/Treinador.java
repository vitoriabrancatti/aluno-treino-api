package com.academia.domain;

import java.util.ArrayList;
import java.util.List;

public class Treinador extends Pessoa {
    private String especialidade;
    private final List<Treino> treinos = new ArrayList<>();

    public Treinador() {
    }

    public Treinador(Long id, String nome, String cpf, String email, int idade) {
        super(id, nome, cpf, email, idade, "TREINADOR");
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public List<Treino> getTreinos() {
        return treinos;
    }

    public String getTipo() {
        return "TREINADOR";
    }
}
