package com.academia.domain;

import java.time.LocalDate;

public class Treino {
    private Long id;
    private String descricao;
    private LocalDate data;
    private int duracaoMinutos;
    private String nivel;
    private Aluno aluno;

    public Treino() {
    }

    public Treino(Long id, String descricao, LocalDate data, int duracaoMinutos, String nivel, Aluno aluno) {
        this.id = id;
        this.descricao = descricao;
        this.data = data;
        this.duracaoMinutos = duracaoMinutos;
        this.nivel = nivel;
        this.aluno = aluno;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(int duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
}
