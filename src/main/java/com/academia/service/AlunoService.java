package com.academia.service;

import com.academia.domain.Aluno;
import com.academia.domain.Treino;
import com.academia.persistence.AlunoRepository;
import com.academia.persistence.TreinoRepository;
import com.academia.service.validation.AlunoValidator;

import java.util.List;

public class AlunoService {
    private final AlunoRepository alunoRepository;
    private final TreinoRepository treinoRepository;
    private final AlunoValidator validator;

    public AlunoService(AlunoRepository alunoRepository, TreinoRepository treinoRepository, AlunoValidator validator) {
        this.alunoRepository = alunoRepository;
        this.treinoRepository = treinoRepository;
        this.validator = validator;
    }

    public Aluno create(Aluno aluno) {
        validator.validate(aluno);
        alunoRepository.save(aluno);
        return aluno;
    }

    public List<Aluno> listAll() {
        List<Aluno> alunos = alunoRepository.findAll();
        alunos.forEach(this::loadTreinos);
        return alunos;
    }

    public Aluno find(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno nao encontrado"));
        loadTreinos(aluno);
        return aluno;
    }

    public Aluno update(Long id, Aluno payload) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno nao encontrado"));
        aluno.setNome(payload.getNome());
        aluno.setCpf(payload.getCpf());
        aluno.setEmail(payload.getEmail());
        aluno.setIdade(payload.getIdade());
        validator.validate(aluno);
        alunoRepository.update(aluno);
        loadTreinos(aluno);
        return aluno;
    }

    public void delete(Long id) {
        boolean removed = alunoRepository.delete(id);
        if (!removed) {
            throw new IllegalArgumentException("Aluno nao encontrado");
        }
    }

    private void loadTreinos(Aluno aluno) {
        List<Treino> treinos = treinoRepository.findByAlunoId(aluno.getId());
        aluno.getTreinos().clear();
        aluno.getTreinos().addAll(treinos);
    }
}
