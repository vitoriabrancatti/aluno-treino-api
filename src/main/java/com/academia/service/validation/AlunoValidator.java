package com.academia.service.validation;

import com.academia.domain.Aluno;

public class AlunoValidator {
    public void validate(Aluno aluno) {
        if (aluno.getNome() == null || aluno.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome obrigatorio");
        }
        if (aluno.getCpf() == null || aluno.getCpf().isBlank()) {
            throw new IllegalArgumentException("CPF obrigatorio");
        }
        if (aluno.getEmail() == null || aluno.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email obrigatorio");
        }
        if (aluno.getIdade() <= 0) {
            throw new IllegalArgumentException("Idade invalida");
        }
    }
}
