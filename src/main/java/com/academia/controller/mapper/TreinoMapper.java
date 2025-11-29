package com.academia.controller.mapper;

import com.academia.domain.Aluno;
import com.academia.domain.Treino;
import com.academia.dto.TreinoRequest;
import com.academia.dto.TreinoResponse;
import com.academia.dto.TreinoSummaryResponse;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class TreinoMapper {
    public Treino toEntity(TreinoRequest request) {
        LocalDate data = parseDate(request.getData());
        Aluno aluno = new Aluno();
        aluno.setId(request.getAlunoId());
        return new Treino(null, request.getDescricao(), data, request.getDuracaoMinutos(), request.getNivel(), aluno);
    }

    public TreinoResponse toResponse(Treino treino) {
        TreinoResponse response = new TreinoResponse();
        response.setId(treino.getId());
        response.setDescricao(treino.getDescricao());
        response.setData(treino.getData());
        response.setDuracaoMinutos(treino.getDuracaoMinutos());
        response.setNivel(treino.getNivel());
        response.setAlunoId(treino.getAluno() != null ? treino.getAluno().getId() : null);
        return response;
    }

    public TreinoSummaryResponse toSummary(Treino treino) {
        TreinoSummaryResponse summary = new TreinoSummaryResponse();
        summary.setId(treino.getId());
        summary.setDescricao(treino.getDescricao());
        summary.setData(treino.getData());
        summary.setDuracaoMinutos(treino.getDuracaoMinutos());
        summary.setNivel(treino.getNivel());
        return summary;
    }

    private LocalDate parseDate(String rawDate) {
        try {
            return LocalDate.parse(rawDate);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data invalida");
        }
    }
}
