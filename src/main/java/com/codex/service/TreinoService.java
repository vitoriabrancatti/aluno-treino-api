package com.codex.service;

import com.codex.domain.Aluno;
import com.codex.domain.Treino;
import com.codex.dto.TreinoRequest;
import com.codex.dto.TreinoResponse;
import com.codex.persistence.AlunoRepository;
import com.codex.persistence.TreinoRepository;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class TreinoService {
    private final TreinoRepository treinoRepository;
    private final AlunoRepository alunoRepository;

    public TreinoService(TreinoRepository treinoRepository, AlunoRepository alunoRepository) {
        this.treinoRepository = treinoRepository;
        this.alunoRepository = alunoRepository;
    }

    public TreinoResponse create(TreinoRequest request) {
        Treino treino = toEntity(request);
        treinoRepository.save(treino);
        return toResponse(treino);
    }

    public List<TreinoResponse> listAll(Long alunoId) {
        List<Treino> treinos = alunoId == null ? treinoRepository.findAll() : treinoRepository.findByAlunoId(alunoId);
        return treinos.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public TreinoResponse find(Long id) {
        Treino treino = treinoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Treino nao encontrado"));
        return toResponse(treino);
    }

    public TreinoResponse update(Long id, TreinoRequest request) {
        Treino treino = treinoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Treino nao encontrado"));
        Treino payload = toEntity(request);
        treino.setDescricao(payload.getDescricao());
        treino.setData(payload.getData());
        treino.setDuracaoMinutos(payload.getDuracaoMinutos());
        treino.setNivel(payload.getNivel());
        treino.setAluno(payload.getAluno());
        treinoRepository.update(treino);
        return toResponse(treino);
    }

    public void delete(Long id) {
        boolean removed = treinoRepository.delete(id);
        if (!removed) {
            throw new IllegalArgumentException("Treino nao encontrado");
        }
    }

    private Treino toEntity(TreinoRequest request) {
        validateTreino(request);
        LocalDate data;
        try {
            data = LocalDate.parse(request.getData());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data invalida");
        }
        Aluno aluno = alunoRepository.findById(request.getAlunoId()).orElseThrow(() -> new IllegalArgumentException("Aluno nao encontrado"));
        return new Treino(null, request.getDescricao(), data, request.getDuracaoMinutos(), request.getNivel(), aluno);
    }

    private void validateTreino(TreinoRequest request) {
        if (request.getDescricao() == null || request.getDescricao().isBlank()) {
            throw new IllegalArgumentException("Descricao obrigatoria");
        }
        if (request.getData() == null || request.getData().isBlank()) {
            throw new IllegalArgumentException("Data obrigatoria");
        }
        if (request.getDuracaoMinutos() <= 0) {
            throw new IllegalArgumentException("Duracao invalida");
        }
        if (request.getNivel() == null || request.getNivel().isBlank()) {
            throw new IllegalArgumentException("Nivel obrigatorio");
        }
        if (request.getAlunoId() == null) {
            throw new IllegalArgumentException("Aluno obrigatorio");
        }
    }

    private TreinoResponse toResponse(Treino treino) {
        TreinoResponse response = new TreinoResponse();
        response.setId(treino.getId());
        response.setDescricao(treino.getDescricao());
        response.setData(treino.getData());
        response.setDuracaoMinutos(treino.getDuracaoMinutos());
        response.setNivel(treino.getNivel());
        response.setAlunoId(treino.getAluno() != null ? treino.getAluno().getId() : null);
        return response;
    }
}
