package com.academia.controller.mapper;

import com.academia.domain.Aluno;
import com.academia.domain.Treino;
import com.academia.dto.AlunoRequest;
import com.academia.dto.AlunoResponse;
import com.academia.dto.TreinoSummaryResponse;

import java.util.List;
import java.util.stream.Collectors;

public class AlunoMapper {
    private final TreinoMapper treinoMapper;

    public AlunoMapper(TreinoMapper treinoMapper) {
        this.treinoMapper = treinoMapper;
    }

    public Aluno toEntity(AlunoRequest request) {
        return new Aluno(null, request.getNome(), request.getCpf(), request.getEmail(), request.getIdade());
    }

    public AlunoResponse toResponse(Aluno aluno) {
        AlunoResponse response = new AlunoResponse();
        response.setId(aluno.getId());
        response.setNome(aluno.getNome());
        response.setCpf(aluno.getCpf());
        response.setEmail(aluno.getEmail());
        response.setIdade(aluno.getIdade());
        response.setTreinos(mapTreinos(aluno.getTreinos()));
        return response;
    }

    private List<TreinoSummaryResponse> mapTreinos(List<Treino> treinos) {
        return treinos.stream()
                .map(treinoMapper::toSummary)
                .collect(Collectors.toList());
    }
}
