package com.academia.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.academia.controller.mapper.TreinadorMapper;
import com.academia.domain.Treinador;
import com.academia.dto.TreinadorRequest;
import com.academia.dto.TreinadorResponse;
import com.academia.service.TreinadorService;

public class TreinadorController {
    private final TreinadorService treinadorService;
    private final TreinadorMapper mapper;

    public TreinadorController(TreinadorService treinadorService, TreinadorMapper mapper) {
        this.treinadorService = treinadorService;
        this.mapper = mapper;
    }

    public TreinadorResponse create(TreinadorRequest request) {
        Treinador treinador = mapper.toEntity(request);
        return mapper.toResponse(treinadorService.create(treinador));
    }

    public List<TreinadorResponse> listAll() {
        return treinadorService.listAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
    public TreinadorResponse find(Long id) {
        return mapper.toResponse(treinadorService.find(id));
    }
    public TreinadorResponse update(Long id, TreinadorRequest request) {
        Treinador payload = mapper.toEntity(request);
        return mapper.toResponse(treinadorService.update(id, payload));
    }
    public void delete(Long id) {
        treinadorService.delete(id);
    }

}
