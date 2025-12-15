package com.academia.controller.mapper;

import com.academia.domain.Treinador;
import com.academia.dto.TreinadorRequest;
import com.academia.dto.TreinadorResponse;

public class TreinadorMapper {

    public Treinador toEntity(TreinadorRequest request) {
        Treinador treinador = new Treinador();
        treinador.setNome(request.getNome());
        treinador.setCpf(request.getCpf());
        treinador.setEmail(request.getEmail());
        treinador.setIdade(request.getIdade());
        treinador.setEspecialidade(request.getEspecialidade());
        return treinador;
    }

    public TreinadorResponse toResponse(Treinador treinador) {
        TreinadorResponse response = new TreinadorResponse();
        response.setId(treinador.getId());
        response.setNome(treinador.getNome());
        response.setCpf(treinador.getCpf());
        response.setEmail(treinador.getEmail());
        response.setIdade(treinador.getIdade());
        response.setEspecialidade(treinador.getEspecialidade());
        return response;
    }
}
