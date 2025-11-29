package com.academia.controller;

import com.academia.controller.mapper.AlunoMapper;
import com.academia.domain.Aluno;
import com.academia.dto.AlunoRequest;
import com.academia.dto.AlunoResponse;
import com.academia.service.AlunoService;

import java.util.List;
import java.util.stream.Collectors;

public class AlunoController {
    private final AlunoService alunoService;
    private final AlunoMapper mapper;

    public AlunoController(AlunoService alunoService, AlunoMapper mapper) {
        this.alunoService = alunoService;
        this.mapper = mapper;
    }

    public AlunoResponse create(AlunoRequest request) {
        Aluno aluno = mapper.toEntity(request);
        return mapper.toResponse(alunoService.create(aluno));
    }

    public List<AlunoResponse> listAll() {
        return alunoService.listAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public AlunoResponse find(Long id) {
        return mapper.toResponse(alunoService.find(id));
    }

    public AlunoResponse update(Long id, AlunoRequest request) {
        Aluno payload = mapper.toEntity(request);
        return mapper.toResponse(alunoService.update(id, payload));
    }

    public void delete(Long id) {
        alunoService.delete(id);
    }
}
