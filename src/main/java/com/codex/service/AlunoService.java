package com.codex.service;

import com.codex.domain.Aluno;
import com.codex.domain.Treino;
import com.codex.dto.AlunoRequest;
import com.codex.dto.AlunoResponse;
import com.codex.dto.TreinoSummaryResponse;
import com.codex.persistence.AlunoRepository;
import com.codex.persistence.TreinoRepository;

import java.util.List;
import java.util.stream.Collectors;

public class AlunoService {
    private final AlunoRepository alunoRepository;
    private final TreinoRepository treinoRepository;

    public AlunoService(AlunoRepository alunoRepository, TreinoRepository treinoRepository) {
        this.alunoRepository = alunoRepository;
        this.treinoRepository = treinoRepository;
    }

    public AlunoResponse create(AlunoRequest request) {
        validateAluno(request);
        Aluno aluno = toEntity(request);
        alunoRepository.save(aluno);
        return toResponse(aluno, List.of());
    }

    public List<AlunoResponse> listAll() {
        return alunoRepository.findAll().stream()
                .map(aluno -> toResponse(aluno, treinoRepository.findByAlunoId(aluno.getId())))
                .collect(Collectors.toList());
    }

    public AlunoResponse find(Long id) {
        Aluno aluno = alunoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Aluno nao encontrado"));
        return toResponse(aluno, treinoRepository.findByAlunoId(id));
    }

    public AlunoResponse update(Long id, AlunoRequest request) {
        Aluno aluno = alunoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Aluno nao encontrado"));
        validateAluno(request);
        aluno.setNome(request.getNome());
        aluno.setCpf(request.getCpf());
        aluno.setEmail(request.getEmail());
        aluno.setIdade(request.getIdade());
        alunoRepository.update(aluno);
        return toResponse(aluno, treinoRepository.findByAlunoId(id));
    }

    public void delete(Long id) {
        boolean removed = alunoRepository.delete(id);
        if (!removed) {
            throw new IllegalArgumentException("Aluno nao encontrado");
        }
    }

    private void validateAluno(AlunoRequest request) {
        if (request.getNome() == null || request.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome obrigatorio");
        }
        if (request.getCpf() == null || request.getCpf().isBlank()) {
            throw new IllegalArgumentException("CPF obrigatorio");
        }
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email obrigatorio");
        }
        if (request.getIdade() <= 0) {
            throw new IllegalArgumentException("Idade invalida");
        }
    }

    private Aluno toEntity(AlunoRequest request) {
        return new Aluno(null, request.getNome(), request.getCpf(), request.getEmail(), request.getIdade());
    }

    private AlunoResponse toResponse(Aluno aluno, List<Treino> treinos) {
        AlunoResponse response = new AlunoResponse();
        response.setId(aluno.getId());
        response.setNome(aluno.getNome());
        response.setCpf(aluno.getCpf());
        response.setEmail(aluno.getEmail());
        response.setIdade(aluno.getIdade());
        response.setTreinos(treinos.stream().map(this::toSummary).collect(Collectors.toList()));
        return response;
    }

    private TreinoSummaryResponse toSummary(Treino treino) {
        TreinoSummaryResponse summary = new TreinoSummaryResponse();
        summary.setId(treino.getId());
        summary.setDescricao(treino.getDescricao());
        summary.setData(treino.getData());
        summary.setDuracaoMinutos(treino.getDuracaoMinutos());
        summary.setNivel(treino.getNivel());
        return summary;
    }
}
