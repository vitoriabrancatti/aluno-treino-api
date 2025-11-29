package com.academia.service.validation;

import com.academia.domain.Treino;

public class TreinoValidator {
    public void validate(Treino treino) {
        if (treino.getDescricao() == null || treino.getDescricao().isBlank()) {
            throw new IllegalArgumentException("Descricao obrigatoria");
        }
        if (treino.getData() == null) {
            throw new IllegalArgumentException("Data obrigatoria");
        }
        if (treino.getDuracaoMinutos() <= 0) {
            throw new IllegalArgumentException("Duracao invalida");
        }
        if (treino.getNivel() == null || treino.getNivel().isBlank()) {
            throw new IllegalArgumentException("Nivel obrigatorio");
        }
        if (treino.getAluno() == null || treino.getAluno().getId() == null) {
            throw new IllegalArgumentException("Aluno obrigatorio");
        }
    }
}
