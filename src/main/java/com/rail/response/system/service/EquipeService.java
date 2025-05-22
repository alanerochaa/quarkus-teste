package com.rail.response.system.service;

import com.rail.response.system.DTOs.EquipeDTO;
import com.rail.response.system.model.Equipe;
import com.rail.response.system.repository.EquipeRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class EquipeService {

    @Inject
    EquipeRepository equipeRepository;

    public Equipe buscarPorId(long id) {
        return equipeRepository.findById(id);
    }

    @Transactional
    public Equipe cadastrarEquipe(EquipeDTO dto) {
        var equipe = mapEquipe(dto);
        equipe.setDataCriacao(LocalDate.now());
        equipeRepository.persist(equipe);
        return equipe;
    }

    @Transactional
    public Equipe alterarEquipe(long id, EquipeDTO dto) {
        var equipe = equipeRepository.findById(id);
        if (equipe == null) return null;

        equipe.setNomeEquipe(dto.getNomeEquipe());
        equipe.setTipoExperiencia(dto.getTipoExperiencia());
        equipe.setContatoResponsavel(dto.getContatoResponsavel());

        return equipe;
    }

    @Transactional
    public void deletarEquipe(long id) {
        equipeRepository.deleteById(id);
    }

    public List<Equipe> listar() {
        return equipeRepository.listAll(Sort.by("id"));
    }

    private Equipe mapEquipe(EquipeDTO dto) {
        return Equipe.builder()
                .nomeEquipe(dto.getNomeEquipe())
                .tipoExperiencia(dto.getTipoExperiencia())
                .contatoResponsavel(dto.getContatoResponsavel())
                .build();
    }
}
