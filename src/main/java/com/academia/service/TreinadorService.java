package com.academia.service;

import java.util.List;

import com.academia.domain.Treinador;
import com.academia.persistence.TreinadorRepository;

public class TreinadorService {
    private final TreinadorRepository treinadorRepository;
    public TreinadorService(TreinadorRepository treinadorRepository) {
        this.treinadorRepository = treinadorRepository;
    }

    public Treinador create(Treinador treinador) {
        return treinadorRepository.save(treinador);
    }

    public List<Treinador> listAll() {
        return treinadorRepository.findAll();
    }

    public Treinador find(Long id) {
        return treinadorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Treinador nao encontrado"));
    }

    public Treinador update(Long id, Treinador payload) {
        Treinador treinador = treinadorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Treinador nao encontrado"));
        treinador.setNome(payload.getNome());
        treinador.setCpf(payload.getCpf());
        treinador.setEmail(payload.getEmail());
        treinador.setIdade(payload.getIdade());
        treinador.setEspecialidade(payload.getEspecialidade());
        treinadorRepository.update(treinador);
        return treinador;
    }

    public void delete(Long id) {
        boolean removed = treinadorRepository.delete(id);
        if (!removed) {
            throw new IllegalArgumentException("Treinador nao encontrado");
        }
    }

    


}
