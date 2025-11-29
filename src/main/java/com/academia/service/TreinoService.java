package com.academia.service;

import com.academia.domain.Aluno;
import com.academia.domain.Treino;
import com.academia.persistence.AlunoRepository;
import com.academia.persistence.TreinoRepository;
import com.academia.service.validation.TreinoValidator;

import java.util.List;

public class TreinoService {
    private final TreinoRepository treinoRepository;
    private final AlunoRepository alunoRepository;
    private final TreinoValidator validator;

    public TreinoService(TreinoRepository treinoRepository, AlunoRepository alunoRepository, TreinoValidator validator) {
        this.treinoRepository = treinoRepository;
        this.alunoRepository = alunoRepository;
        this.validator = validator;
    }

    public Treino create(Treino treino) {
        validator.validate(treino);
        Aluno aluno = alunoRepository.findById(treino.getAluno().getId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno nao encontrado"));
        treino.setAluno(aluno);
        treinoRepository.save(treino);
        return treino;
    }

    public List<Treino> listAll(Long alunoId) {
        return alunoId == null ? treinoRepository.findAll() : treinoRepository.findByAlunoId(alunoId);
    }

    public Treino find(Long id) {
        return treinoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Treino nao encontrado"));
    }

    public Treino update(Long id, Treino payload) {
        Treino treino = find(id);
        validator.validate(payload);
        Aluno aluno = alunoRepository.findById(payload.getAluno().getId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno nao encontrado"));
        treino.setDescricao(payload.getDescricao());
        treino.setData(payload.getData());
        treino.setDuracaoMinutos(payload.getDuracaoMinutos());
        treino.setNivel(payload.getNivel());
        treino.setAluno(aluno);
        treinoRepository.update(treino);
        return treino;
    }

    public void delete(Long id) {
        boolean removed = treinoRepository.delete(id);
        if (!removed) {
            throw new IllegalArgumentException("Treino nao encontrado");
        }
    }
}
