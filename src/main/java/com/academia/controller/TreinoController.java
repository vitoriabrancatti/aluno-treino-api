package com.academia.controller;

import com.academia.controller.mapper.TreinoMapper;
import com.academia.domain.Treino;
import com.academia.dto.TreinoRequest;
import com.academia.dto.TreinoResponse;
import com.academia.service.TreinoService;

import java.util.List;
import java.util.stream.Collectors;

public class TreinoController {
    private final TreinoService treinoService;
    private final TreinoMapper mapper;

    public TreinoController(TreinoService treinoService, TreinoMapper mapper) {
        this.treinoService = treinoService;
        this.mapper = mapper;
    }

    public TreinoResponse create(TreinoRequest request) {
        Treino treino = mapper.toEntity(request);
        return mapper.toResponse(treinoService.create(treino));
    }

    public List<TreinoResponse> listAll(Long alunoId) {
        return treinoService.listAll(alunoId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public TreinoResponse find(Long id) {
        return mapper.toResponse(treinoService.find(id));
    }

    public TreinoResponse update(Long id, TreinoRequest request) {
        Treino payload = mapper.toEntity(request);
        return mapper.toResponse(treinoService.update(id, payload));
    }

    public void delete(Long id) {
        treinoService.delete(id);
    }
}
