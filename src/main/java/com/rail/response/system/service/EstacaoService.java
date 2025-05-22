package com.rail.response.system.service;

import com.rail.response.system.DTOs.EstacaoDTO;
import com.rail.response.system.model.Estacao;
import com.rail.response.system.repository.EstacaoRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class EstacaoService {

    @Inject
    EstacaoRepository estacaoRepository;

    public Estacao buscarEstacaoPorId(long id) {
        return estacaoRepository.findById(id);
    }

    public List<Estacao> listarEstacoes() {
        return estacaoRepository.listAll(Sort.by("id"));
    }

    @Transactional
    public Estacao cadastrarEstacao(EstacaoDTO dto) {
        Estacao estacao = mapEstacao(dto);
        estacao.setDataCriacao(LocalDate.now());
        estacaoRepository.persist(estacao);
        return estacao;
    }

    @Transactional
    public Estacao atualizarEstacao(long id, EstacaoDTO dto) {
        Estacao estacao = estacaoRepository.findById(id);
        if (estacao == null) return null;

        estacao.setNome(dto.getNome());
        estacao.setEndereco(dto.getEndereco());
        estacao.setTelefone(dto.getTelefone());
        estacao.setCarro(dto.getCarro());

        return estacao;
    }

    @Transactional
    public void deletarEstacao(Long id) {
        estacaoRepository.deleteById(id);
    }

    private Estacao mapEstacao(EstacaoDTO dto) {
        return Estacao.builder()
                .nome(dto.getNome())
                .endereco(dto.getEndereco())
                .telefone(dto.getTelefone())
                .carro(dto.getCarro())
                .dataInicio(dto.getDataInicio() != null ?
                        new java.sql.Date(dto.getDataInicio().getTime()).toLocalDate() :
                        LocalDate.now())
                .build();
    }
}
